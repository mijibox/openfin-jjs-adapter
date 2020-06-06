package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfChannelBase extends OfObject {

	OfChannelBase(ProxyObject obj, OpenFinGateway gateway) {
		super(obj, gateway);
	}

	public CompletionStage<Void> onDisconnectionAsync(OpenFinEventListener listener) {
		return this.ofInstance.addListener("onDisconnection", listener).thenAccept(r -> {

		});
	}

	public void onDisconnection(OpenFinEventListener listener) {
		runSync(onDisconnectionAsync(listener));
	}

	public CompletionStage<Void> registerAsync(String action, OpenFinEventListener listener) {
		return this.ofInstance.addListener("register", action, listener).thenAccept(r -> {

		});
	}

	public void register(String action, OpenFinEventListener listener) {
		runSync(registerAsync(action, listener));
	}

	public CompletionStage<Void> removeAsync(String action) {
		return this.ofInstance.invoke("remove", Json.createValue(action)).thenAccept(r -> {

		});
	}

	public void remove(String action) {
		runSync(removeAsync(action));
	}

	public CompletionStage<Void> setDefaultActionAsync(OpenFinEventListener listener) {
		return this.ofInstance.addListener("setDefaultAction", listener).thenAccept(r -> {

		});
	}

	public void setDefaultAction(OpenFinEventListener listener) {
		runSync(setDefaultActionAsync(listener));
	}

	public CompletionStage<Void> onErrorAsync(OpenFinEventListener listener) {
		return this.ofInstance.addListener("onError", listener).thenAccept(r -> {

		});
	}

	public void onError(OpenFinEventListener listener) {
		runSync(onErrorAsync(listener));
	}

	public CompletionStage<Void> beforeActionAsync(OpenFinEventListener listener) {
		return this.ofInstance.addListener("beforeAction", listener).thenAccept(r -> {

		});
	}

	public void beforeAction(OpenFinEventListener listener) {
		runSync(beforeActionAsync(listener));
	}

	public CompletionStage<Void> afterActionAsync(OpenFinEventListener listener) {
		return this.ofInstance.addListener("afterAction", listener).thenAccept(r -> {

		});
	}

	public void afterAction(OpenFinEventListener listener) {
		runSync(afterActionAsync(listener));
	}

}
