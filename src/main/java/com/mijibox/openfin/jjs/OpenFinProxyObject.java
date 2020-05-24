package com.mijibox.openfin.jjs;

import java.util.concurrent.CompletionStage;

import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;

public class OpenFinProxyObject {

	protected JsonValue cachedObjId;
	protected OpenFinGateway apiGateway;
	
	protected OpenFinProxyObject(JsonValue cachedObjId, OpenFinGateway apiGateway) {
		this.cachedObjId = cachedObjId;
		this.apiGateway = apiGateway;
	}
	
	public CompletionStage<Void> dispose() {
		return this.apiGateway.delete(this.cachedObjId);
	}
}
