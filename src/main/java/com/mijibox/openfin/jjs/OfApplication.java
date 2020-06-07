package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfApplication extends OfObject {

	protected OfApplication(ProxyObject obj) {
		super(obj);
	}

	public static CompletionStage<OfApplication> startAsync(OpenFinGateway gateway, JsonObject appOpts) {
		return gateway.invoke(true, "fin.Application.start", appOpts)
				.thenApply(r -> {
					return new OfApplication(r.getProxyObject());
				});
	}

	public static OfApplication start(OpenFinGateway gateway, JsonObject appOpts) {
		return runSync(startAsync(gateway, appOpts));
	}

	public static CompletionStage<OfApplication> startFromManifestAsync(OpenFinGateway gateway, String manifestUrl, JsonObject rvmOptions) {
		return gateway.invoke(true, "fin.Application.startFromManifest",
				Json.createValue(manifestUrl), rvmOptions).thenApply(r -> {
					return new OfApplication(r.getProxyObject());
				});
	}

	public static OfApplication startFromManifest(OpenFinGateway gateway, String manifestUrl, JsonObject rvmOptions) {
		return runSync(startFromManifestAsync(gateway, manifestUrl, rvmOptions));
	}

	public static CompletionStage<OfApplication> wrapAsync(OpenFinGateway gateway, JsonObject identity) {
		return gateway.invoke(true, "fin.Application.wrap", identity).thenApply(r -> {
			return new OfApplication(r.getProxyObject());
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
		return this.ofInstance.invoke("quit", force ? JsonValue.TRUE : JsonValue.FALSE).thenAccept(result -> {
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
			return new OfWindow(r.getProxyObject());
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
				windows.add(OfWindow.wrap(ofInstance.getGateway(), winJson.getJsonObject("identity")));
			});
			return windows;
		});
	}

	public List<OfWindow> getChildWindows() {
		return runSync(this.getChildWindowsAsync());
	}

	public CompletionStage<JsonObject> getInfoAsync() {
		return this.ofInstance.invoke("getInfo").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public JsonObject getInfo() {
		return runSync(this.getInfoAsync());
	}

	public CompletionStage<JsonObject> getManifestAsync() {
		return this.ofInstance.invoke("getManifest").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public JsonObject getManifest() {
		return runSync(this.getManifestAsync());
	}

	public CompletionStage<String> getParentUuidAsync() {
		return this.ofInstance.invoke("getParentUuid").thenApply(r -> {
			return r.getResultAsString();
		});
	}
	
	public String getParentUuid() {
		return runSync(this.getParentUuidAsync());
	}
	
	public CompletionStage<Void> setZoomLevelAsync(double zoomLevel) {
		return this.ofInstance.invoke("setZoomLevel", Json.createValue(zoomLevel)).thenAccept(r -> {
		});
	}
	
	public void setZoomLevel(double zoomLevel) {
		runSync(this.setZoomLevelAsync(zoomLevel));
	}

	public CompletionStage<Double> getZoomLevelAsync() {
		return this.ofInstance.invoke("getZoomLevel").thenApply(r -> {
			return r.getResultAsDouble();
		});
	}
	
	public double getZoomLevel() {
		return runSync(this.getZoomLevelAsync());
	}

	public CompletionStage<Void> registerUserAsync(String userName, String appName) {
		return this.ofInstance.invoke("registerUser", Json.createValue(userName), Json.createValue(appName)).thenAccept(r -> {
		});
	}
	
	public void registerUser(String userName, String appName) {
		runSync(this.registerUserAsync(userName, appName));
	}



}
