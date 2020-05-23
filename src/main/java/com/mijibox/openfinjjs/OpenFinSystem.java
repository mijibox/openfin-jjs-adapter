package com.mijibox.openfinjjs;

import java.util.concurrent.CompletionStage;

public class OpenFinSystem {
	
	private OpenFinAPIGateway apiGateway;

	public OpenFinSystem(OpenFinAPIGateway apiGateway) {
		this.apiGateway = apiGateway;
	}

	public CompletionStage<String> getVersion() {
		return this.apiGateway.invokeMethod("fin.System.getVersion").thenApply(r->{
			return r.getString("payload");
		});
	}
	
	public void addListener(String event, OpenFinEventListener listener) {
		this.apiGateway.addListener(null, "fin.System.addListener", event, listener).thenAccept(r->{
			System.out.println("addListener got result: " + r);
		});
	}
}
