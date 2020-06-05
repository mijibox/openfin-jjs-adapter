package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.Utils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;
import com.mijibox.openfin.jjs.json.Identity;

public class OfApplication extends OfObject {

	private Identity identity;

	protected OfApplication(ProxyObject obj, OpenFinGateway gateway, Identity identity) {
		super(obj, gateway);
		this.identity = identity;
	}

	public static CompletionStage<OfApplication> startAsync(JsonObject appOpts,
			OpenFinGateway gateway) {
		return gateway.invoke(true, "fin.Application.start", appOpts)
				.thenApply(r -> {
					JsonObject app = (JsonObject) r.getResult();
					return new OfApplication(r.getProxyObject(), gateway, new Identity(app.getJsonObject("identity")));
				});
	}

	public OfApplication start(JsonObject appOpts, OpenFinGateway gateway) {
		return runSync(startAsync(appOpts, gateway));
	}

	public static CompletionStage<OfApplication> startFromManifestAsync(String manifestUrl,
			OpenFinGateway gateway) {
		return gateway.invoke(true, "fin.Application.startFromManifest",
				Json.createValue(manifestUrl)).thenApply(r -> {
					// System.out.println("startFromManifest got result: " + r);
					JsonObject app = (JsonObject) r.getResult();
					return new OfApplication(r.getProxyObject(), gateway, new Identity(app.getJsonObject("identity")));
				});
	}

	public OfApplication startFromManifest(String manifestUrl, OpenFinGateway gateway) {
		return runSync(startFromManifestAsync(manifestUrl, gateway));
	}

	public static CompletionStage<OfApplication> wrapAsync(Identity identity, OpenFinGateway gateway) {
		return gateway.invoke(true, "fin.Application.wrap",
				identity.getJson()).thenApply(r -> {
					return new OfApplication(r.getProxyObject(), gateway, identity);
				});
	}

	public static OfApplication wrap(Identity identity, OpenFinGateway gateway) {
		return runSync(wrapAsync(identity, gateway));
	}
	
	public CompletionStage<Boolean> isRunningAsync() {
		return this.ofInstance.invoke("isRunning").thenApply(result -> {
			return result.getResultAsBoolean();
		});
	}
	
	public Boolean isRunning() {
		return runSync(isRunningAsync());
	}

	public CompletionStage<Void> quitAsync(boolean force) {
		return this.ofInstance.invoke("quit", Json.createValue(force ? 1 : 0)).thenAccept(result -> {
		});
	}
	
	public void quit(boolean force) {
		runSync(quitAsync(force));
	}

	public CompletionStage<Void> terminateAsync() {
		return this.ofInstance.invoke("terminate").thenAccept(result -> {
		});
	}
	
	public void terminate() {
		runSync(terminateAsync());
	}

	public CompletionStage<OfWindow> getWindowAsync() {
		return this.ofInstance.invoke(true, "getWindow").thenApply(r -> {
			return new OfWindow(r.getProxyObject(), this.gateway);
		});
	}
	
	public OfWindow getWindow() {
		return runSync(getWindowAsync());
	}

	public Identity getIdentity() {
		return this.identity;
	}
}
