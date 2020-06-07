package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;
import com.mijibox.openfin.jjs.json.Identity;

public class OfApplication extends OfObject {

	protected OfApplication(ProxyObject obj, OpenFinGateway gateway) {
		super(obj, gateway);
	}

	public static CompletionStage<OfApplication> startAsync(OpenFinGateway gateway, JsonObject appOpts			) {
		return gateway.invoke(true, "fin.Application.start", appOpts)
				.thenApply(r -> {
					JsonObject app = (JsonObject) r.getResult();
					return new OfApplication(r.getProxyObject(), gateway);
				});
	}

	public OfApplication start(OpenFinGateway gateway, JsonObject appOpts) {
		return runSync(startAsync(gateway, appOpts));
	}

	public static CompletionStage<OfApplication> startFromManifestAsync(OpenFinGateway gateway, String manifestUrl) {
		return gateway.invoke(true, "fin.Application.startFromManifest",
				Json.createValue(manifestUrl)).thenApply(r -> {
					// System.out.println("startFromManifest got result: " + r);
					JsonObject app = (JsonObject) r.getResult();
					return new OfApplication(r.getProxyObject(), gateway);
				});
	}

	public OfApplication startFromManifest(OpenFinGateway gateway, String manifestUrl) {
		return runSync(startFromManifestAsync(gateway, manifestUrl));
	}

	public static CompletionStage<OfApplication> wrapAsync(OpenFinGateway gateway, JsonObject identity) {
		return gateway.invoke(true, "fin.Application.wrap", identity).thenApply(r -> {
			return new OfApplication(r.getProxyObject(), gateway);
		});
	}

	public static OfApplication wrap(OpenFinGateway gateway, JsonObject identity) {
		return runSync(wrapAsync(gateway, identity));
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

	public CompletionStage<List<OfWindow>> getChildWindowsAsync() {
		return this.ofInstance.invoke("getChildWindows").thenApply(r -> {
			return r.getResultAsJsonArray();
		}).thenApply(wins -> {
			ArrayList<OfWindow> windows = new ArrayList<>();
			wins.forEach(w -> {
				JsonObject winJson = (JsonObject) w;
				windows.add(OfWindow.wrap(gateway, winJson.getJsonObject("identity")));
			});
			return windows;
		});
	}

	public List<OfWindow> getChildWindows() {
		return runSync(this.getChildWindowsAsync());
	}
	
	
}
