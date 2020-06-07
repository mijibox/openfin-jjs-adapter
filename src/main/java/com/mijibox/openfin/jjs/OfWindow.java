package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfWindow extends OfObject {

	OfWindow(ProxyObject obj) {
		super(obj);
	}

	public CompletionStage<Void> navigateAsync(String url) {
		return this.ofInstance.invoke("navigate", Json.createValue(url)).thenAccept(r -> {
		});
	}

	public void navigate(String url) {
		runSync(navigateAsync(url));
	}

	public static CompletionStage<OfWindow> wrapAsync(OpenFinGateway gateway, JsonObject identity) {
		return gateway.invoke(true, "fin.Window.wrap", identity).thenApply(r -> {
			return new OfWindow(r.getProxyObject());
		});
	}

	public static OfWindow wrap(OpenFinGateway gateway, JsonObject identity) {
		return runSync(wrapAsync(gateway, identity));
	}

	public CompletionStage<Void> flashAsync() {
		return this.ofInstance.invoke("flash").thenAccept(r -> {
		});
	}

	public void flash() {
		runSync(this.flashAsync());
	}
	
}
