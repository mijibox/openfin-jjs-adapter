package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfExternalApplication extends OfObject {

	OfExternalApplication(ProxyObject obj) {
		super(obj);
	}

	public static CompletionStage<OfExternalApplication> wrapAsync(OpenFinGateway gateway, String uuid) {
		return gateway.invoke(true, "fin.ExternalApplication.wrap", Json.createValue(uuid)).thenApply(r -> {
			return new OfExternalApplication(r.getProxyObject());
		});
	}

	public static OfExternalApplication wrap(OpenFinGateway gateway, String uuid) {
		return runSync(wrapAsync(gateway, uuid));
	}


	public CompletionStage<JsonObject> getInfoAsync() {
		return this.ofInstance.invoke("getInfo").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public JsonObject getInfo() {
		return runSync(this.getInfoAsync());
	}

}
