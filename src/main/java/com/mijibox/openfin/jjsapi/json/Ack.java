package com.mijibox.openfin.jjsapi.json;

import javax.json.JsonObject;

public class Ack extends JsonBean {
	
	public Ack() {
		super();
	}
	
	public Ack(JsonObject json) {
		super(json);
	}
	
	public boolean isSuccess() {
		return this.getBooleanValue("success", false);
	}
	
	public String getReason() {
		return this.getStringValue("reason", "N/A");
	}
	
	@Override 
	public String toString() {
		return this.json.toString();
	}
}
