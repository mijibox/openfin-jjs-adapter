package com.mijibox.openfinjjs;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class OpenFinInterApplicationBus implements MessageProcessor {

	private OpenFinConnection connection;
	private ConcurrentHashMap<String, CopyOnWriteArrayList<OpenFinIABMessageListener>> listenerMap;

	public OpenFinInterApplicationBus(OpenFinConnection connection) {
		this.connection = connection;
		this.listenerMap = new ConcurrentHashMap<>();
		this.connection.addMessageProcessor(this);
	}

	public CompletionStage<Void> publish(String topic, JsonObject message) {
		JsonObject payload = Json.createObjectBuilder().add("topic", topic)
				.add("message", message).build();
		return this.connection.sendMessage("publish-message", payload).thenAcceptAsync(ack -> {
			if (!ack.isSuccess()) {
				throw new RuntimeException("error publish, reason: " + ack.getReason());
			}
		});
	}

	public CompletionStage<Void> send(Identity destionation, String topic, JsonValue message) {
		JsonObject payload = Json.createObjectBuilder()
				.add("destinationUuid", destionation.getUuid())
				.add("destinationWindowName", destionation.getName())
				.add("topic", topic)
				.add("message", message).build();
		return this.connection.sendMessage("send-message", payload).thenAcceptAsync(ack -> {
			if (!ack.isSuccess()) {
				throw new RuntimeException("error send, reason: " + ack.getReason());
			}
		});
	}

	public CompletionStage<Void> subscribe(Identity source, String topic, OpenFinIABMessageListener listener) {
		String key = this.getSubscriptionKey(source.getUuid(), source.getName(), topic);
		CopyOnWriteArrayList<OpenFinIABMessageListener> listeners = this.listenerMap.get(key);
		
		if (listeners == null) {
			CopyOnWriteArrayList<OpenFinIABMessageListener> existingListener = this.listenerMap.put(key, new CopyOnWriteArrayList<>(new OpenFinIABMessageListener[] {listener}));
			if (existingListener == null) {
				//first one, send out the subscription
				JsonObject payload = Json.createObjectBuilder()
						.add("sourceUuid", source.getUuid())
						.add("sourceWindowName", source.getName())
						.add("topic", topic).build();
				return this.connection.sendMessage("subscribe", payload).thenAcceptAsync(ack -> {
					if (!ack.isSuccess()) {
						throw new RuntimeException("error subscribe, reason: " + ack.getReason());
					}
				});
			}
			else {
				existingListener.add(listener);
				return CompletableFuture.completedStage(null);
			}
		}
		else {
			listeners.add(listener);
			return CompletableFuture.completedStage(null);
		}
	}

	public CompletionStage<Void> unsubscribe(Identity source, String topic, OpenFinIABMessageListener listener) {
		JsonObject payload = Json.createObjectBuilder()
				.add("sourceUuid", source.getUuid())
				.add("sourceWindowName", source.getName())
				.add("topic", topic).build();
		return this.connection.sendMessage("unsubscribe", payload).thenAcceptAsync(ack -> {
			if (!ack.isSuccess()) {
				throw new RuntimeException("error unsubscribe, reason: " + ack.getReason());
			}
		});
	}
	
	private String getSubscriptionKey(String uuid, String name, String topic) {
		return uuid + "::" + name + "::" + topic;
	}

	@Override
	public void processMessage(JsonObject payload) {
		//check if it has subsubscribed topic
		String sourceUuid = payload.getString("sourceUuid");
		String sourceWindowName = payload.getString("sourceWindowName");
		String topic = payload.getString("topic");
		
		String key = this.getSubscriptionKey(sourceUuid, sourceWindowName, topic);
		CopyOnWriteArrayList<OpenFinIABMessageListener> listeners = this.listenerMap.get(key);
		if (listeners != null) {
			listeners.forEach(l -> {
				l.onMessage(new Identity(sourceUuid, sourceWindowName), payload.get("message"));
			});
		}
		
	}
}
