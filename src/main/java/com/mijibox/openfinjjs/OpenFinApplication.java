package com.mijibox.openfinjjs;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonValue;

public class OpenFinApplication {

	private OpenFinAPIGateway apiExecutor;
	private JsonValue cachedAppObjId;

	private OpenFinApplication(JsonValue cachedAppObjId, OpenFinAPIGateway apiExecutor) {
		this.cachedAppObjId = cachedAppObjId;
		this.apiExecutor = apiExecutor;
	}

	public static CompletionStage<OpenFinApplication> startFromManifest(String manifestUrl,
			OpenFinAPIGateway apiExecutor) {
		return apiExecutor.invokeMethod("fin.Application.startFromManifest", true, 
				Json.createValue(manifestUrl)).thenApply(r -> {
					//System.out.println("startFromManifest got result: " + r);
					JsonValue cachedAppObjId = r.get("resultObjectId");
					System.out.println("got resultObjectId: " + cachedAppObjId);
					return new OpenFinApplication(cachedAppObjId, apiExecutor);
				});
	}
	
	public CompletionStage<Boolean> isRunning() {
		return this.apiExecutor.invokeMethod(this.cachedAppObjId, false, "isRunning", (JsonValue[])null).thenApply(result ->{
			return result.getBoolean("payload");
		});
	}

}
