package com.mijibox.openfinjjs;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

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
			this.authFuture.complete(this);
		}
		else if ("ack".equals(action)) {
			int correlationId = receivedJson.getInt("correlationId");
			CompletableFuture<Ack> ackFuture = this.ackMap.get(correlationId);
			ackFuture.complete(new Ack(payload));
//			ackFuture.completeOnTimeout(new Ack(payload), 60, TimeUnit.SECONDS);
		}
		else {
			
		}
	}

}
