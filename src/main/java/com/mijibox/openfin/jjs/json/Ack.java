package com.mijibox.openfin.jjs.json;

import javax.json.JsonObject;

public class Ack extends JsonBeanImpl {
	
	public Ack() {
		super();
	}
	
	public Ack(JsonObject json) {
		super(json);
	}
	
	public boolean isSuccess() {
		return this.getBoolean("success");
	}
	
	public String getReason() {
		return this.getString("reason");
	}
	
	@Override 
	public String toString() {
		return this.json.toString();
	}
}
