package com.mijibox.openfinjjs;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonValue;

public class OpenFinWindow extends OpenFinProxyObject {

	OpenFinWindow(JsonValue cachedObjId, OpenFinAPIGateway apiGateway) {
		super(cachedObjId, apiGateway);
	}
	
	public CompletionStage<Void> navigate(String url) {
		return this.apiGateway.invokeMethod(this.cachedObjId, false, "navigate", Json.createValue(url)).thenAccept(result ->{
		});
	}

}
