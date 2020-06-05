package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyListener;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfObject {
	protected ProxyObject ofInstance;
	protected OpenFinGateway gateway;

	OfObject(ProxyObject obj, OpenFinGateway gateway) {
		this.ofInstance = obj;
		this.gateway = gateway;
	}

	public CompletionStage<ProxyListener> addListenerAsync(String event, OpenFinEventListener listener) {
		return this.ofInstance.addListener(true, "on", event, listener);
	}
	
	public ProxyListener addListener(String event, OpenFinEventListener listener) {
		return runSync(this.addListenerAsync(event, listener));
	}

	public CompletionStage<Void> removeListenerAsync(String event, ProxyListener listener) {
		return this.ofInstance.removeListener("removeListener", event, listener);
	}
	
	public void removeListener(String event, ProxyListener listener) {
		runSync(this.removeListenerAsync(event, listener));
	}

	public CompletionStage<Void> disposeAsync() {
		return this.ofInstance.dispose();
	}
	
	public void dispose() {
		runSync(this.disposeAsync());
	}
}
