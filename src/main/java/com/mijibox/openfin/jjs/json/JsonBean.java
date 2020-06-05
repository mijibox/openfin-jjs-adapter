package com.mijibox.openfin.jjs.json;

import javax.json.JsonObject;

public interface JsonBean<T> {
	JsonObject toJson();
	T fromJson(JsonObject json);
}
