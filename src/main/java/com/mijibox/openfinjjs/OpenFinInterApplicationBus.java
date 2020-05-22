package com.mijibox.openfinjjs;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class OpenFinInterApplicationBus {

	private OpenFinConnection connection;

	public OpenFinInterApplicationBus(OpenFinConnection connection) {
		this.connection = connection;
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
}
