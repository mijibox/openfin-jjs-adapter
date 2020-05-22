package com.mijibox.openfinjjs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class OpenFinAPIGateway {
	
	private OpenFinInterApplicationBus iab;
	private Identity execIdentity;
	private String execTopic;
	private AtomicInteger messageId;
	private ConcurrentHashMap<Integer, CompletableFuture<JsonObject>> execCorrelationMap;

	public OpenFinAPIGateway(OpenFinConnection connection) {
		this.messageId = new AtomicInteger(0);
		this.execCorrelationMap = new ConcurrentHashMap<>();
		this.execIdentity = new Identity(connection.getDefaultApplicationUuid(), connection.getDefaultApplicationUuid());
		this.execTopic = connection.getDefaultApplicationUuid() + "-exec";
		this.iab = new OpenFinInterApplicationBus(connection);
		this.iab.subscribe(this.execIdentity, this.execTopic, (srcIdentity, message) -> {
			JsonObject msg = ((JsonObject)message);
			System.out.println("got message: " + msg);
			this.execCorrelationMap.get(msg.getInt("messageId")).complete(msg);
		});
	}
	
	private CompletionStage<JsonObject> sendMessage(String action, JsonValue payload) {
		int msgId = this.messageId.getAndIncrement();
		CompletableFuture<JsonObject> responseFuture = new CompletableFuture<>();
		this.execCorrelationMap.put(msgId, responseFuture);
		JsonObject message = Json.createObjectBuilder().add("messageId", msgId)
				.add("action", action)
				.add("payload", payload).build();
		return this.iab.send(this.execIdentity, this.execTopic, message).thenCombineAsync(responseFuture, (r1, r2) ->{
			return r2;
		});
	}
	
	public CompletionStage<Void> ping() {
		long value = System.currentTimeMillis();
		return this.sendMessage("ping", Json.createValue(value)).thenAccept(pongMsg->{
		});
	}
	
	public CompletionStage<JsonObject> invokeMethod(String method) {
		return this.invokeMethod(method, (JsonValue[])null);
	}
	
	public CompletionStage<JsonObject> invokeMethod(String method, JsonValue... args) {
		return this.invokeMethod(method, false, args);
	}

	public CompletionStage<JsonObject> invokeMethod(String method, boolean cacheResultObject, JsonValue... args) {
		return this.invokeMethod(null, cacheResultObject, method, args);
	}
	
	public CompletionStage<JsonObject> invokeMethod(JsonValue cachedObjId, boolean cacheResultObject, String method, JsonValue... args) {
		JsonObjectBuilder builder = Json.createObjectBuilder()
				.add("cacheResultObj", cacheResultObject)
				.add("method", method);
		if (cachedObjId != null) {
			builder.add("cachedObjId", cachedObjId);
		}
		if (args != null) {
			JsonArrayBuilder argsBuilder = Json.createArrayBuilder();
			for (int i=0; i<args.length; i++) {
				argsBuilder.add(args[i]);
			}
			builder.add("args", argsBuilder.build());
		}
		
		return this.sendMessage("invoke", builder.build());
	}

}
