package com.mijibox.openfinjjs;

import javax.json.JsonValue;

public class OpenFinProxyObject {

	protected JsonValue cachedObjId;
	protected OpenFinAPIGateway apiGateway;
	
	protected OpenFinProxyObject(JsonValue cachedObjId, OpenFinAPIGateway apiGateway) {
		this.cachedObjId = cachedObjId;
		this.apiGateway = apiGateway;
	}
}
