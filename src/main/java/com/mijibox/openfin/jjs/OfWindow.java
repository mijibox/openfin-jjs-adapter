package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfWindow extends OfWebContent {

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
		JsonValue[] args = (area == null ? (JsonValue[]) null : new JsonValue[] { area });
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
		return this.ofInstance.invoke("authenticate", Json.createValue(userName), Json.createValue(password))
				.thenAccept(r -> {
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

	public CompletionStage<JsonArray> getGroupAsync() {
		return this.ofInstance.invoke("getGroup").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}

	public JsonArray getGroup() {
		return runSync(this.getGroupAsync());
	}

	public CompletionStage<Void> leaveGroupAsync() {
		return this.ofInstance.invoke("leaveGroup").thenAccept(r -> {
		});
	}

	public void leaveGroup() {
		runSync(this.leaveGroupAsync());
	}

	public CompletionStage<Void> moveByAsync(int deltaLeft, int deltaTop, JsonObject options) {
		return this.ofInstance.invoke("moveBy", Json.createValue(deltaLeft), Json.createValue(deltaTop), options)
				.thenAccept(r -> {
				});
	}

	public void moveBy(int deltaLeft, int deltaTop, JsonObject options) {
		runSync(this.moveByAsync(deltaLeft, deltaTop, options));
	}

	public CompletionStage<Void> moveToAsync(int left, int top, JsonObject options) {
		return this.ofInstance.invoke("moveTo", Json.createValue(left), Json.createValue(top), options)
				.thenAccept(r -> {
				});
	}

	public void moveTo(int left, int top, JsonObject options) {
		runSync(this.moveToAsync(left, top, options));
	}

	public CompletionStage<Void> resizeByAsync(int deltaWidth, int deltaHeight, String anchor, JsonObject options) {
		return this.ofInstance.invoke("resizeBy", Json.createValue(deltaWidth), Json.createValue(deltaHeight),
				anchor == null ? null : Json.createValue(anchor), options).thenAccept(r -> {
				});
	}

	public void resizeBy(int deltaWidth, int deltaHeight, String anchor, JsonObject options) {
		runSync(this.resizeByAsync(deltaWidth, deltaHeight, anchor, options));
	}

	public CompletionStage<Void> resizeToAsync(int width, int height, String anchor, JsonObject options) {
		return this.ofInstance.invoke("resizeTo", Json.createValue(width), Json.createValue(height),
				anchor == null ? null : Json.createValue(anchor), options).thenAccept(r -> {
				});
	}

	public void resizeTo(int width, int height, String anchor, JsonObject options) {
		runSync(this.resizeToAsync(width, height, anchor, options));
	}

	public CompletionStage<Void> setAsForegroundAsync() {
		return this.ofInstance.invoke("setAsForeground").thenAccept(r -> {
		});
	}

	public void setAsForeground() {
		runSync(this.setAsForegroundAsync());
	}

	public CompletionStage<Void> setBoundsAsync(JsonObject bounds, JsonObject options) {
		return this.ofInstance.invoke("setBounds", bounds, options).thenAccept(r -> {
		});
	}

	public void setBounds(JsonObject bounds, JsonObject options) {
		runSync(this.setBoundsAsync(bounds, options));
	}

	public CompletionStage<Void> showAtAsync(int left, int top, boolean force, JsonObject options) {
		return this.ofInstance.invoke("showAt", Json.createValue(left), Json.createValue(top),
				force ? JsonValue.TRUE : JsonValue.FALSE, options).thenAccept(r -> {
				});
	}

	public void showAt(int left, int top, boolean force, JsonObject options) {
		runSync(this.showAtAsync(left, top, force, options));
	}

	public CompletionStage<Void> updateOptionsAsync(JsonObject options) {
		return this.ofInstance.invoke("updateOptions", options).thenAccept(r -> {
		});
	}

	public void updateOptions(JsonObject options) {
		runSync(this.updateOptionsAsync(options));
	}

}
