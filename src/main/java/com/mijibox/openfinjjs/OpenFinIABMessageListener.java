package com.mijibox.openfinjjs;

import javax.json.JsonObject;

public interface OpenFinIABMessageListener {
	public void onMessage(JsonObject payload);
}
