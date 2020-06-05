package com.mijibox.openfin.jjs.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class JsonBeanImpl {

	protected JsonObject json;

	public JsonBeanImpl() {
		this(Json.createObjectBuilder().build());
	}

	public JsonBeanImpl(JsonObject json) {
		this.json = json;
	}

	public JsonObject getJson() {
		return this.json;
	}

	public JsonObject getJsonCopy() {
		return Json.createObjectBuilder(this.json).build();
	}

	public BigDecimal getBigDecimal(String name) {
		return this.json.containsKey(name) ? this.json.getJsonNumber(name).bigDecimalValue() : null;
	}

	public void setBigDecimal(String name, BigDecimal value) {
		this.json.put(name, value == null ? JsonValue.NULL : Json.createValue(value));
	}

	public BigInteger getBigInteger(String name) {
		return this.json.containsKey(name) ? this.json.getJsonNumber(name).bigIntegerValue() : null;
	}

	public void setBigInteger(String name, BigInteger value) {
		this.json.put(name, value == null ? JsonValue.NULL : Json.createValue(value));
	}

	public Boolean getBoolean(String name) {
		return this.json.containsKey(name) ? this.json.getBoolean(name) : null;
	}

	public void setBoolean(String name, Boolean value) {
		this.json.put(name, value == null ? JsonValue.NULL : (value ? JsonValue.TRUE : JsonValue.FALSE));
	}

	public Double getDouble(String name) {
		return this.json.containsKey(name) ? this.json.getJsonNumber(name).doubleValue() : null;
	}

	public void setDouble(String name, Double value) {
		this.json.put(name, value == null ? JsonValue.NULL : Json.createValue(value));
	}

	public Integer getInteger(String name) {
		return this.json.containsKey(name) ? this.json.getJsonNumber(name).intValue() : null;
	}

	public void setInteger(String name, Integer value) {
		this.json.put(name, value == null ? JsonValue.NULL : Json.createValue(value));
	}

	public Long getLong(String name) {
		return this.json.containsKey(name) ? this.json.getJsonNumber(name).longValue() : null;
	}

	public void setLong(String name, Long value) {
		this.json.put(name, value == null ? JsonValue.NULL : Json.createValue(value));
	}

	public String getString(String name) {
		return this.json.containsKey(name) ? this.json.getString(name) : null;
	}

	public void setString(String name, String value) {
		this.json.put(name, value == null ? JsonValue.NULL : Json.createValue(value));
	}

	public JsonArray getJsonArray(String name) {
		return this.json.containsKey(name) ? this.json.getJsonArray(name) : null;
	}

	public void setJsonArray(String name, JsonArray value) {
		this.json.put(name, value == null ? JsonValue.NULL : value);
	}

	public JsonObject getJsonObject(String name) {
		return this.json.containsKey(name) ? this.json.getJsonObject(name) : null;
	}

	public void setJsonObject(String name, JsonObject value) {
		this.json.put(name, value == null ? JsonValue.NULL : value);
	}

}
