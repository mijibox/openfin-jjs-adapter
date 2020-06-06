package com.mijibox.openfin.jjs;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;
import static com.mijibox.openfin.jjs.OfUtils.runSync;

public class OfChannelProvider extends OfChannelBase {

	OfChannelProvider(ProxyObject obj, OpenFinGateway gateway) {
		super(obj, gateway);
	}
	
	public CompletionStage<Void> onConnectionAsync(OpenFinEventListener listener) {
		return this.ofInstance.addListener("onConnection", listener).thenAccept(r->{
			
		});
	}
	
	public void onConnection(OpenFinEventListener listener) {
		runSync(onConnectionAsync(listener));
	}

	public CompletionStage<Void> publishAsync(String action, JsonValue payload) {
		return this.ofInstance.invoke("publish", Json.createValue(action), payload).thenAccept(r -> {

		});
	}

	public void publish(String action, JsonValue payload) {
		runSync(publishAsync(action, payload));
	}
	
	public CompletionStage<Void> dispatchAsync(JsonObject to, String action, JsonValue payload) {
		return this.ofInstance.invoke("dispatch", to, Json.createValue(action), payload).thenAccept(r -> {

		});
	}

	public void dispatch(JsonObject to, String action, JsonValue payload) {
		runSync(dispatchAsync(to, action, payload));
	}

	public CompletionStage<Void> destroyAsync() {
		return this.ofInstance.invoke("destroy").thenAccept(r -> {

		});
	}

	public void destroy() {
		runSync(destroyAsync());
	}


}
