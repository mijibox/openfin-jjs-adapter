package com.mijibox.openfin.jjs.json;

import javax.json.JsonObject;

public class Identity extends JsonBeanImpl {

	public Identity() {
	}

	public Identity(JsonObject json) {
		super(json);
	}
	
	public Identity(String uuid) {
		this.setUuid(uuid);
	}
	
	public Identity(String uuid, String name) {
		this.setUuid(uuid);
		this.setName(name);
	}

	public void setUuid(String uuid) {
		this.setString("uuid", uuid);
	}
	
	public String getUuid() {
		return this.json.getString("uuid");
	}
	
	public void setName(String name) {
		this.setString("name", name);
	}
	
	public String getName() {
		return this.json.getString("name");
	}
}
