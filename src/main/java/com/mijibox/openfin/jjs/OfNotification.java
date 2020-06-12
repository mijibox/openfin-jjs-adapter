package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.JsonObject;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfNotification extends OfObject {

	OfNotification(ProxyObject obj) {
		super(obj);
	}

	public static CompletionStage<OfNotification> createAsync(OpenFinGateway gateway, JsonObject options) {
		return gateway.invoke(true, "fin.Notification.create", options).thenApply(r -> {
			return new OfNotification(r.getProxyObject());
		});
	}

	public static OfNotification create(OpenFinGateway gateway, JsonObject options) {
		return runSync(createAsync(gateway, options));
	}

	public CompletionStage<Void> closeAsync() {
		return this.ofInstance.invoke("close").thenAccept(r -> {
		});
	}

	public void close() {
		runSync(this.closeAsync());
	}

	public CompletionStage<Void> sendMessageAsync(JsonValue message) {
		return this.ofInstance.invoke("sendMessage", message).thenAccept(r -> {
		});
	}

	public void sendMessage(JsonValue message) {
		runSync(this.sendMessageAsync(message));
	}

	public CompletionStage<Void> showAsync() {
		return this.ofInstance.invoke("show").thenAccept(r -> {
		});
	}

	public void show() {
		runSync(this.showAsync());
	}

}
