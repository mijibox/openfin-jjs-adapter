package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyListener;

public class OfSystem {

	private OpenFinGateway gateway;

	public OfSystem(OpenFinGateway gateway) {
		this.gateway = gateway;
	}

	public CompletionStage<String> getVersionAsync() {
		return this.gateway.invoke("fin.System.getVersion").thenApply(r -> {
			return r.getResultAsString();
		});
	}
	
	public String getVersion() {
		return runSync(getVersionAsync());
	}

	public CompletionStage<ProxyListener> addListenerAsync(String event, OpenFinEventListener listener) {
		return this.gateway.addListener(true, "fin.System.on", event, listener);
	}
	
	public ProxyListener addListener(String event, OpenFinEventListener listener) {
		return runSync(this.addListenerAsync(event, listener));
	}

	public CompletionStage<Void> removeListenerAsync(String event, ProxyListener listener) {
		return this.gateway.removeListener("removeListener", event, listener);
	}
	
	public void removeListener(String event, ProxyListener listener) {
		runSync(this.removeListenerAsync(event, listener));
	}
}
