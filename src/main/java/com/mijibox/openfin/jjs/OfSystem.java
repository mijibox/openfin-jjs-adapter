package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyListener;

public class OfSystem {

	public static CompletionStage<String> getVersionAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getVersion").thenApply(r -> {
			return r.getResultAsString();
		});
	}
	
	public static String getVersion(OpenFinGateway gateway) {
		return runSync(getVersionAsync(gateway));
	}

	public static CompletionStage<ProxyListener> addListenerAsync(OpenFinGateway gateway, String event, OpenFinEventListener listener) {
		return gateway.addListener(true, "fin.System.on", event, listener);
	}
	
	public static ProxyListener addListener(OpenFinGateway gateway, String event, OpenFinEventListener listener) {
		return runSync(addListenerAsync(gateway, event, listener));
	}

	public static CompletionStage<Void> removeListenerAsync(OpenFinGateway gateway, String event, ProxyListener listener) {
		return gateway.removeListener("removeListener", event, listener);
	}
	
	public static void removeListener(OpenFinGateway gateway, String event, ProxyListener listener) {
		runSync(removeListenerAsync(gateway, event, listener));
	}
}
