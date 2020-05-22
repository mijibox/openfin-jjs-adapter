package com.mijibox.openfinjjs;

import javax.json.JsonValue;

public interface OpenFinIABMessageListener {
	public void onMessage(Identity sourceIdentity, JsonValue jsonValue);
}
