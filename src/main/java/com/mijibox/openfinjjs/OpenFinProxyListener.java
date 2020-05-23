package com.mijibox.openfinjjs;

import javax.json.JsonValue;

public class OpenFinProxyListener extends OpenFinProxyObject {

	private OpenFinEventListener listener;

	protected OpenFinProxyListener(JsonValue cachedObjId, OpenFinAPIGateway apiGateway, OpenFinEventListener listener) {
		super(cachedObjId, apiGateway);
		this.listener = listener;
		
	}

}
