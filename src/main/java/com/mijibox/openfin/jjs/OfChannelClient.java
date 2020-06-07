package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.ProxyObject;

public class OfChannelClient extends OfChannelBase {

	OfChannelClient(ProxyObject obj) {
		super(obj);
	}
	
	public CompletionStage<JsonValue> dispatchAsync(String action, JsonValue payload) {
		return this.ofInstance.invoke("dispatch", Json.createValue(action), payload).thenApply(r -> {
			return r.getResult();
		});
	}

	public JsonValue dispatch(String action, JsonValue payload) {
		return runSync(dispatchAsync(action, payload));
	}

	public CompletionStage<Void> disconnectAsync() {
		return this.ofInstance.invoke("disconnect").thenAccept(r -> {

		});
	}

	public void disconnect() {
		runSync(disconnectAsync());
	}
}
