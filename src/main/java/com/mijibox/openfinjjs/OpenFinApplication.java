package com.mijibox.openfinjjs;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class OpenFinApplication extends OpenFinProxyObject {

	protected OpenFinApplication(JsonValue cachedAppObjId, OpenFinAPIGateway apiGateway) {
		super(cachedAppObjId, apiGateway);
	}

	public static CompletionStage<OpenFinApplication> start(JsonObject appOpts,
			OpenFinAPIGateway apiGateway) {
		return apiGateway.invokeMethod("fin.Application.start", true, appOpts)
				.thenApply(r -> {
					JsonValue cachedAppObjId = r.get("resultObjectId");
					return new OpenFinApplication(cachedAppObjId, apiGateway);
				});
	}

	public static CompletionStage<OpenFinApplication> startFromManifest(String manifestUrl,
			OpenFinAPIGateway apiGateway) {
		return apiGateway.invokeMethod("fin.Application.startFromManifest", true,
				Json.createValue(manifestUrl)).thenApply(r -> {
					// System.out.println("startFromManifest got result: " + r);
					JsonValue cachedAppObjId = r.get("resultObjectId");
					return new OpenFinApplication(cachedAppObjId, apiGateway);
				});
	}
	
	public static CompletionStage<OpenFinApplication> wrap(Identity identity, OpenFinAPIGateway apiGateway) {
		return apiGateway.invokeMethod("fin.Application.wrap", true, 
				identity.getJson()).thenApply(r -> {
					JsonValue cachedAppObjId = r.get("resultObjectId");
					return new OpenFinApplication(cachedAppObjId, apiGateway);
				});
	}
	
	public CompletionStage<Boolean> isRunning() {
		return this.apiGateway.invokeMethod(this.cachedObjId, false, "isRunning", (JsonValue[])null).thenApply(result ->{
			return result.getBoolean("payload");
		});
	}

	public CompletionStage<Boolean> quit(boolean force) {
		return this.apiGateway.invokeMethod(this.cachedObjId, false, "quit", Json.createValue(force ? 1 : 0)).thenApply(result ->{
			return result.getBoolean("payload");
		});
	}
	
	public CompletionStage<Void> terminate() {
		return this.apiGateway.invokeMethod(this.cachedObjId, false, "terminate", (JsonValue[])null).thenAccept(result ->{
		});
	}
	
	public CompletionStage<OpenFinWindow> getWindow() {
		return this.apiGateway.invokeMethod(this.cachedObjId, true, "getWindow", (JsonValue[])null).thenApply(r ->{
			JsonValue resultObjectId = r.get("resultObjectId");
			return new OpenFinWindow(resultObjectId, this.apiGateway);
		});
	}

}
