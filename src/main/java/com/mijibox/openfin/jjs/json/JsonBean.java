package com.mijibox.openfin.jjs.json;

import javax.json.Json;
import javax.json.JsonObject;

public class JsonBean {
	
	protected JsonObject json;
	
	public JsonBean() {
		this(Json.createObjectBuilder().build());
	}
	
	public JsonBean(JsonObject json) {
		this.json = json;
	}
	
	public JsonObject getJson() {
		return this.json;
	}
	
	public JsonObject getJsonCopy() {
		return Json.createObjectBuilder(this.json).build();
	}
	
	public boolean getBooleanValue(String name, boolean defaultValue) {
		return this.json.getBoolean(name, defaultValue); 
	}
	
	public String getStringValue(String name, String defaultValue) {
		return this.json.getString(name, defaultValue);
	}
	
	public void setStringValue(String name, String value) {
		this.json = Json.createObjectBuilder(this.json).add(name, Json.createValue(value)).build();
	}
}
