package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfPlatform extends OfObject {

	protected OfPlatform(ProxyObject obj, OpenFinGateway gateway) {
		super(obj, gateway);
	}
	
	public static CompletionStage<OfPlatform> startFromManifestAsync(String manifestUrl, JsonObject rvmLaunchOptions, OpenFinGateway gateway) {
		return gateway.invoke(true, "fin.Platform.startFromManifest", Json.createValue(manifestUrl), rvmLaunchOptions)
				.thenApply(r -> {
					return new OfPlatform(r.getProxyObject(), gateway);
				});
	}
	
	public static OfPlatform startFromManifest(String manifestUrl, JsonObject rvmLaunchOptions, OpenFinGateway gateway) {
		return runSync(startFromManifestAsync(manifestUrl, rvmLaunchOptions, gateway));
	}
	
	public CompletionStage<Void> quitAsync() {
		return this.ofInstance.invoke("quit").thenAccept(r -> {
		});
	}
	
	public void quit() {
		runSync(quitAsync());
	}

}
