package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfWindow extends OfObject {

	OfWindow(ProxyObject obj) {
		super(obj);
	}

	public static CompletionStage<OfWindow> createAsync(OpenFinGateway gateway, JsonObject options) {
		return gateway.invoke(true, "fin.Window.create", options).thenApply(r -> {
			return new OfWindow(r.getProxyObject());
		});
	}

	public static OfWindow create(OpenFinGateway gateway, JsonObject options) {
		return runSync(createAsync(gateway, options));
	}

	public static CompletionStage<OfWindow> wrapAsync(OpenFinGateway gateway, JsonObject identity) {
		return gateway.invoke(true, "fin.Window.wrap", identity).thenApply(r -> {
			return new OfWindow(r.getProxyObject());
		});
	}

	public static OfWindow wrap(OpenFinGateway gateway, JsonObject identity) {
		return runSync(wrapAsync(gateway, identity));
	}

	public CompletionStage<Void> navigateAsync(String url) {
		return this.ofInstance.invoke("navigate", Json.createValue(url)).thenAccept(r -> {
		});
	}

	public void navigate(String url) {
		runSync(navigateAsync(url));
	}

	public CompletionStage<Void> navigateBackAsync() {
		return this.ofInstance.invoke("navigateBack").thenAccept(r -> {
		});
	}

	public void navigateBack() {
		runSync(navigateBackAsync());
	}

	public CompletionStage<Void> navigateForwardAsync() {
		return this.ofInstance.invoke("navigateForward").thenAccept(r -> {
		});
	}

	public void navigateForward() {
		runSync(navigateForwardAsync());
	}

	public CompletionStage<Void> closeAsync(boolean force) {
		return this.ofInstance.invoke("close", force ? JsonValue.TRUE : JsonValue.FALSE).thenAccept(r -> {
		});
	}

	public void close(boolean force) {
		runSync(this.closeAsync(force));
	}
	
	public CompletionStage<Void> flashAsync() {
		return this.ofInstance.invoke("flash").thenAccept(r -> {
		});
	}

	public void flash() {
		runSync(this.flashAsync());
	}
	
	public CompletionStage<Void> stopFlashingAsync() {
		return this.ofInstance.invoke("stopFlashing").thenAccept(r -> {
		});
	}

	public void stopFlashing() {
		runSync(this.stopFlashingAsync());
	}
	
	public CompletionStage<Void> stopNavigationAsync() {
		return this.ofInstance.invoke("stopNavigation").thenAccept(r -> {
		});
	}

	public void stopNavigation() {
		runSync(this.stopNavigationAsync());
	}
	
	public CompletionStage<Void> reloadAsync() {
		return this.ofInstance.invoke("reload").thenAccept(r -> {
		});
	}

	public void reload() {
		runSync(this.reloadAsync());
	}
	
	public CompletionStage<Void> maximizeAsync() {
		return this.ofInstance.invoke("maximize").thenAccept(r -> {
		});
	}

	public void maximize() {
		runSync(this.maximizeAsync());
	}
	
	public CompletionStage<Void> minimizeAsync() {
		return this.ofInstance.invoke("minimize").thenAccept(r -> {
		});
	}

	public void minimize() {
		runSync(this.minimizeAsync());
	}
	
	public CompletionStage<Void> restoreAsync() {
		return this.ofInstance.invoke("restore").thenAccept(r -> {
		});
	}

	public void restore() {
		runSync(this.restoreAsync());
	}
	
	public CompletionStage<String> getStateAsync() {
		return this.ofInstance.invoke("getState").thenApply(r -> {
			return r.getResultAsString();
		});
	}

	public String getState() {
		return runSync(this.getStateAsync());
	}
	

	
	public CompletionStage<Void> focusAsync() {
		return this.ofInstance.invoke("focus").thenAccept(r -> {
		});
	}

	public void focus() {
		runSync(this.focusAsync());
	}
	
	public CompletionStage<Void> blurAsync() {
		return this.ofInstance.invoke("blur").thenAccept(r -> {
		});
	}

	public void blur() {
		runSync(this.blurAsync());
	}
	
	public CompletionStage<Void> bringToFrontAsync() {
		return this.ofInstance.invoke("bringToFront").thenAccept(r -> {
		});
	}

	public void bringToFront() {
		runSync(this.bringToFrontAsync());
	}
	
	public CompletionStage<Void> centerAsync() {
		return this.ofInstance.invoke("center").thenAccept(r -> {
		});
	}

	public void center() {
		runSync(this.centerAsync());
	}
	
	public CompletionStage<JsonArray> getAllFramesAsync() {
		return this.ofInstance.invoke("getAllFrames").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}

	public JsonArray getAllFrames() {
		return runSync(this.getAllFramesAsync());
	}
	
	public CompletionStage<JsonObject> getBoundsAsync() {
		return this.ofInstance.invoke("getBounds").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public JsonObject getBounds() {
		return runSync(this.getBoundsAsync());
	}
	
	public CompletionStage<JsonObject> getInfoAsync() {
		return this.ofInstance.invoke("getInfo").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public JsonObject getInfo() {
		return runSync(this.getInfoAsync());
	}
	
	public CompletionStage<JsonArray> getPrintersAsync() {
		return this.ofInstance.invoke("getPrinters").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}

	public JsonArray getPrinters() {
		return runSync(this.getPrintersAsync());
	}
	
	public CompletionStage<JsonObject> getOptionsAsync() {
		return this.ofInstance.invoke("getOptions").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public JsonObject getOptions() {
		return runSync(this.getOptionsAsync());
	}
	
	public CompletionStage<String> getNativeIdAsync() {
		return this.ofInstance.invoke("getNativeId").thenApply(r -> {
			return r.getResultAsString();
		});
	}

	public String getNativeId() {
		return runSync(this.getNativeIdAsync());
	}
	
	public CompletionStage<String> getSnapshotAsync(JsonObject area) {
		JsonValue[] args = (area == null ? (JsonValue[]) null : new JsonValue[] {area});
		return this.ofInstance.invoke("getSnapshot", args).thenApply(r -> {
			return r.getResultAsString();
		});
	}

	public String getSnapshot(JsonObject area) {
		return runSync(this.getSnapshotAsync(area));
	}
	
	public CompletionStage<OfApplication> getParentApplicationAsync() {
		return this.ofInstance.invoke(true, "getParentApplication").thenApply(r -> {
			return new OfApplication(r.getProxyObject());
		});
	}

	public OfApplication getParentApplication() {
		return runSync(this.getParentApplicationAsync());
	}
	
	public CompletionStage<OfWindow> getParentWindowAsync() {
		return this.ofInstance.invoke(true, "getParentWindow").thenApply(r -> {
			return new OfWindow(r.getProxyObject());
		});
	}

	public OfWindow getParentWindow() {
		return runSync(this.getParentWindowAsync());
	}
	
	public CompletionStage<Boolean> isMainWindowAsync() {
		return this.ofInstance.invoke("isMainWindow").thenApply(r -> {
			return r.getResultAsBoolean();
		});
	}

	public Boolean isMainWindow() {
		return runSync(this.isMainWindowAsync());
	}
	
	public CompletionStage<Boolean> isShowingAsync() {
		return this.ofInstance.invoke("isShowing").thenApply(r -> {
			return r.getResultAsBoolean();
		});
	}

	public Boolean isShowing() {
		return runSync(this.isShowingAsync());
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

	public CompletionStage<ProxyObject> getWebWindowAsync() {
		return this.ofInstance.invoke(true, "getWebWindow").thenApply(r -> {
			return r.getProxyObject();
		});
	}
	
	public CompletionStage<Void> showAsync(boolean force) {
		return this.ofInstance.invoke("show", force ? JsonValue.TRUE : JsonValue.FALSE).thenAccept(r -> {
		});
	}

	public void show(boolean force) {
		runSync(this.showAsync(force));
	}
	
	public CompletionStage<Void> hideAsync() {
		return this.ofInstance.invoke("hide").thenAccept(r -> {
		});
	}

	public void hide() {
		runSync(this.hideAsync());
	}
	
	public CompletionStage<Void> animateAsync(JsonObject transitions, JsonObject options) {
		return this.ofInstance.invoke("animate", transitions, options).thenAccept(r -> {
		});
	}

	public void animate(JsonObject transitions, JsonObject options) {
		runSync(this.animateAsync(transitions, options));
	}
	
	public CompletionStage<Void> authenticateAsync(String userName, String password) {
		return this.ofInstance.invoke("authenticate", Json.createValue(userName), Json.createValue(password)).thenAccept(r -> {
		});
	}

	public void authenticate(String userName, String password) {
		runSync(this.authenticateAsync(userName, password));
	}
	
	public CompletionStage<Void> disableUserMovementAsync() {
		return this.ofInstance.invoke("disableUserMovement").thenAccept(r -> {
		});
	}

	public void disableUserMovement() {
		runSync(this.disableUserMovementAsync());
	}
	
	public CompletionStage<Void> enableUserMovementAsync() {
		return this.ofInstance.invoke("enableUserMovement").thenAccept(r -> {
		});
	}

	public void enableUserMovement() {
		runSync(this.enableUserMovementAsync());
	}
	
	public CompletionStage<Void> executeJavaScriptAsync(String code) {
		return this.ofInstance.invoke("executeJavaScript", Json.createValue(code)).thenAccept(r -> {
		});
	}

	public void executeJavaScript(String code) {
		runSync(this.executeJavaScriptAsync(code));
	}
	
	public CompletionStage<JsonObject> findInPageAsync(String searchTerm, JsonObject options) {
		return this.ofInstance.invoke("findInPage", Json.createValue(searchTerm), options).thenApply(r ->{
			return r.getResultAsJsonObject();
		});
	}

	public JsonObject findInPage(String searchTerm, JsonObject options) {
		return runSync(this.findInPageAsync(searchTerm, options));
	}
	
}
