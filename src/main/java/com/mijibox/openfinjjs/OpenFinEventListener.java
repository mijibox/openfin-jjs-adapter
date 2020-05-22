package com.mijibox.openfinjjs;

import javax.json.JsonObject;

public interface OpenFinEventListener {
	default public void onEvent(JsonObject event) {
	}
}
