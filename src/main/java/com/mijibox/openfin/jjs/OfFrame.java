package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfFrame extends OfObject {

	OfFrame(ProxyObject obj) {
		super(obj);
	}

	public static CompletionStage<OfFrame> wrapAsync(OpenFinGateway gateway, JsonObject identity) {
		return gateway.invoke(true, "fin.Frame.wrap", identity).thenApply(r -> {
			return new OfFrame(r.getProxyObject());
		});
	}

	public static OfFrame wrap(OpenFinGateway gateway, JsonObject identity) {
		return runSync(wrapAsync(gateway, identity));
	}


	public CompletionStage<JsonObject> getInfoAsync() {
		return this.ofInstance.invoke("getInfo").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public JsonObject getInfo() {
		return runSync(this.getInfoAsync());
	}

	public CompletionStage<JsonObject> getParentWindowAsync() {
		return this.ofInstance.invoke("getParentWindow").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public JsonObject getParentWindow() {
		return runSync(this.getParentWindowAsync());
	}

}
