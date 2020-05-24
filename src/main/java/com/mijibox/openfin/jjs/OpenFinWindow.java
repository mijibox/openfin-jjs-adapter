package com.mijibox.openfin.jjs;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;

public class OpenFinWindow extends OpenFinProxyObject {

	OpenFinWindow(JsonValue cachedObjId, OpenFinGateway apiGateway) {
		super(cachedObjId, apiGateway);
	}
	
	public CompletionStage<Void> navigate(String url) {
		return this.apiGateway.invoke(this.cachedObjId, false, "navigate", Json.createValue(url)).thenAccept(result ->{
		});
	}

}
