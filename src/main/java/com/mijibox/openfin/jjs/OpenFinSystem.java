package com.mijibox.openfin.jjs;

import java.util.concurrent.CompletionStage;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;

public class OpenFinSystem {
	
	private OpenFinGateway apiGateway;

	public OpenFinSystem(OpenFinGateway apiGateway) {
		this.apiGateway = apiGateway;
	}

	public CompletionStage<String> getVersion() {
		return this.apiGateway.invoke("fin.System.getVersion").thenApply(r->{
			return r.getString("payload");
		});
	}
	
	public void addListener(String event, OpenFinEventListener listener) {
		this.apiGateway.addListener("fin.System.addListener", event, listener).thenAccept(r->{
		});
	}
}
