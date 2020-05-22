package com.mijibox.openfinjjs;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class OpenFinAPIExecutor {
	
	private OpenFinInterApplicationBus iab;
	private Identity execIdentity;
	private String execTopic;
	private AtomicInteger msgId;

	public OpenFinAPIExecutor(OpenFinConnection connection) {
		this.msgId = new AtomicInteger(0);
		this.execIdentity = new Identity("__default_" + connection.getUuid(), "__default_" + connection.getUuid());
		this.execTopic = "__OpenFinAPIExecutor__";
		this.iab = new OpenFinInterApplicationBus(connection);
		this.iab.subscribe(this.execIdentity, this.execTopic, (srcIdentity, result) -> {
			System.out.println("got result: " + result);
		});
	}
	
	public CompletionStage<?> invokeMethod(String method) {
		return this.invokeMethod(method, (JsonValue[])null);
	}
	
	public CompletionStage<?> invokeMethod(String method, JsonValue ...args) {
		JsonObjectBuilder builder = Json.createObjectBuilder()
				.add("msgId", this.msgId.getAndIncrement())
				.add("cacheResultObj", true)
				.add("method", method);

		if (args != null) {
			JsonArrayBuilder argsBuilder = Json.createArrayBuilder();
			for (int i=0; i<args.length; i++) {
				argsBuilder.add(args[i]);
			}
			builder.add("args", argsBuilder.build());
		}
		
		return this.iab.send(this.execIdentity, this.execTopic, builder.build());
	}

}
