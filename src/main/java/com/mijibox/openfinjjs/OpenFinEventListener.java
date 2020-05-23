package com.mijibox.openfinjjs;

import javax.json.JsonObject;

@FunctionalInterface
public interface OpenFinEventListener {
	public void onEvent(JsonObject event);
	
}
