package com.mijibox.openfinjjs;

import javax.json.JsonObject;

public interface MessageProcessor {
	public void processMessage(JsonObject payload);
}
