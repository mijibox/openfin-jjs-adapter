package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfLayout extends OfObject {
	
	OfLayout(ProxyObject obj) {
		super(obj);
	}

	public static CompletionStage<OfLayout> wrapAsync(OpenFinGateway gateway, JsonObject windowIdentity) {
		return gateway.invoke(true, "fin.Platform.Layout.wrap", windowIdentity).thenApply(r -> {
			return new OfLayout(r.getProxyObject());
		});
	}

	public static OfLayout wrap(OpenFinGateway gateway, JsonObject windowIdentity) {
		return runSync(wrapAsync(gateway, windowIdentity));
	}

	public CompletionStage<Void> applyPresetAsync(JsonObject options) {
		return this.ofInstance.invoke("applyPreset", options).thenAccept(result -> {
		});
	}

	public void applyPreset(JsonObject options) {
		runSync(applyPresetAsync(options));
	}

	public CompletionStage<JsonObject> getConfigAsync() {
		return this.ofInstance.invoke("getConfig").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public JsonObject getConfig() {
		return runSync(this.getConfigAsync());
	}

	public CompletionStage<Void> replaceAsync(JsonObject layout) {
		return this.ofInstance.invoke("replace", layout).thenAccept(result -> {
		});
	}

	public void replace(JsonObject layout) {
		runSync(replaceAsync(layout));
	}


}
