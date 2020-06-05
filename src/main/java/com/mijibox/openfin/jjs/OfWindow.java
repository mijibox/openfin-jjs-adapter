package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;
import com.mijibox.openfin.jjs.json.Identity;

public class OfWindow extends OfObject {

	private Identity identity;

	OfWindow(Identity identity, ProxyObject obj, OpenFinGateway gateway) {
		super(obj, gateway);
		this.identity = identity;
	}

	public CompletionStage<Void> navigateAsync(String url) {
		return this.ofInstance.invoke("navigate", Json.createValue(url)).thenAccept(r -> {
		});
	}

	public void navigate(String url) {
		runSync(navigateAsync(url));
	}

	public static CompletionStage<OfWindow> wrapAsync(Identity identity, OpenFinGateway gateway) {
		return gateway.invoke(true, "fin.Window.wrap", identity.getJson()).thenApply(r -> {
			return new OfWindow(identity, r.getProxyObject(), gateway);
		});
	}

	public static OfWindow wrap(Identity identity, OpenFinGateway gateway) {
		return runSync(wrapAsync(identity, gateway));
	}

	public CompletionStage<Void> flashAsync() {
		return this.ofInstance.invoke("flash").thenAccept(r -> {
		});
	}

	public void flash() {
		runSync(this.flashAsync());
	}
	
	public Identity getIdentity() {
		return this.identity;
	}
}
