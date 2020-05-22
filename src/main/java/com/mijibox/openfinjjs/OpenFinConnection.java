package com.mijibox.openfinjjs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenFinConnection implements Listener {
	private final static Logger logger = LoggerFactory.getLogger(OpenFinConnection.class);

	StringBuilder receivedMessage = new StringBuilder();
	CompletableFuture<?> accumulatedMessage = new CompletableFuture<>();
	AtomicInteger messageId = new AtomicInteger(0);
	ConcurrentHashMap<Integer, CompletableFuture<Ack>> ackMap = new ConcurrentHashMap<>();

	private int port;

	private WebSocket webSocket;

	private String uuid;
	private CompletableFuture<OpenFinConnection> authFuture = new CompletableFuture<>();;

	public OpenFinConnection(String uuid, int port) {
		this.uuid = uuid;
		this.port = port;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public CompletionStage<OpenFinConnection> connect() {
		try {
			
			String endpointURI = "ws://localhost:" + this.port + "/";
			HttpClient httpClient = HttpClient.newHttpClient();
			httpClient.newWebSocketBuilder().buildAsync(new URI(endpointURI), this);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {

		}
		return this.authFuture;
	}
	
	@Override
	public void onOpen(WebSocket webSocket) {
		webSocket.request(1);
		this.webSocket = webSocket;
		logger.info("websocket connected");
		
		JsonObject authPayload = Json.createObjectBuilder().add("action", "request-external-authorization")
				.add("payload", Json.createObjectBuilder()
						.add("uuid", this.uuid)
						.add("type", "file-token")
						.add("client", Json.createObjectBuilder()
								.add("type", "java")
								.add("version", "1.0-SNAPSHOT").build()
						).build())
				.build();
		
		this.webSocket.sendText(authPayload.toString(), true);
	}

	@Override
	public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
		receivedMessage.append(data);
		webSocket.request(1);
		if (last) {
			processMessage(receivedMessage.toString());
			receivedMessage = new StringBuilder();
			accumulatedMessage.complete(null);
			CompletionStage<?> cf = accumulatedMessage;
			accumulatedMessage = new CompletableFuture<>();
			return cf;
		}
		return accumulatedMessage;
	}
	
	public CompletionStage<Ack> sendMessage(String action, JsonObject payload) {
		int msgId = this.messageId.getAndIncrement();
		JsonObjectBuilder json = Json.createObjectBuilder();
		JsonObject msgJson = json.add("action", action).add("messageId", msgId).add("payload", payload).build();

		CompletableFuture<Ack> ackFuture = new CompletableFuture<>();
		this.ackMap.put(msgId, ackFuture);

		String msg = msgJson.toString();
		logger.info("sending: {}", msg);
		return this.webSocket.sendText(msg, true).thenCombineAsync(ackFuture, (ws, ack) -> {
			logger.info("msg[{}] got ack: {}", msgId, ack);
			return ack;
		});

	}

	private void processMessage(String message) {
		logger.info("received: {}", message);
		JsonReader jsonReader = Json.createReader(new StringReader(message));
		JsonObject receivedJson = jsonReader.readObject();
		String action = receivedJson.getString("action");
		JsonObject payload = receivedJson.getJsonObject("payload");
		if ("external-authorization-response".equals(action)) {
			String file = payload.getString("file");
			String token = payload.getString("token");
			
			try {
				Files.write(Paths.get(file), token.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
				
				JsonObject requestAuthPayload = Json.createObjectBuilder()
						.add("uuid", this.uuid)
						.add("type", "file-token").build();
				
				this.sendMessage("request-authorization", requestAuthPayload);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if ("authorization-response".equals(action)) {
			try {
				URL defaultHtml = this.getClass().getClassLoader().getResource("default.html");
				//copy the content to temp directory
				ReadableByteChannel readableByteChannel = Channels.newChannel(defaultHtml.openStream());
				Path tempFile = Files.createTempFile(null, ".html");
				FileOutputStream fileOutputStream = new FileOutputStream(tempFile.toFile());
				long size = fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
				fileOutputStream.close();
				
				System.out.println("defaultHtml: " + tempFile.toString());

				this.startApplication("__default_" + this.uuid, tempFile.toUri().toString())
						.thenAcceptAsync(app -> {
							this.authFuture.complete(this);
						});
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				
			}
		}
		else if ("ack".equals(action)) {
			int correlationId = receivedJson.getInt("correlationId");
			CompletableFuture<Ack> ackFuture = this.ackMap.remove(correlationId);
			if (ackFuture == null) {
				logger.error("missing ackFuture, correlationId={}", correlationId);
			}
			else {
				ackFuture.complete(new Ack(payload));
			}
//			ackFuture.completeOnTimeout(new Ack(payload), 60, TimeUnit.SECONDS);
		}
		else {
			
		}
	}
	
	public CompletionStage<OpenFinApplication> startApplication(String appUuid, String url) {
		logger.info("startApplication, uuid={}, url={}", appUuid, url);
		var appOpts = Json.createObjectBuilder()
				.add("uuid", appUuid)
				.add("url", url)
				.add("autoShow", true).build();

		return this.sendMessage("create-application", appOpts).thenComposeAsync(ack -> {
			return this.sendMessage("run-application", Json.createObjectBuilder().add("uuid", appUuid).build());
		}).thenApplyAsync(ack->{
			if (ack.isSuccess()) {
				return new OpenFinApplication();
			}
			else {
				throw new RuntimeException("error startApplication, reason: " + ack.getReason());
			}
		});
	}

}
