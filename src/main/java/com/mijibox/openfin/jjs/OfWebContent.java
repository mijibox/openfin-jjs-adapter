package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.ProxyObject;

public class OfWebContent extends OfObject {

	protected OfWebContent(ProxyObject obj) {
		super(obj);
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

	public CompletionStage<Void> focusAsync() {
		return this.ofInstance.invoke("focus").thenAccept(r -> {
		});
	}

	public void focus() {
		runSync(this.focusAsync());
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

	public CompletionStage<JsonArray> getPrintersAsync() {
		return this.ofInstance.invoke("getPrinters").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}

	public JsonArray getPrinters() {
		return runSync(this.getPrintersAsync());
	}

	public CompletionStage<Void> showDeveloperToolsAsync() {
		return this.ofInstance.invoke("showDeveloperTools").thenAccept(r -> {
		});
	}

	public void showDeveloperTools() {
		runSync(this.showDeveloperToolsAsync());
	}

	public CompletionStage<Void> stopFindInPageAsync(String action) {
		return this.ofInstance.invoke("stopFindInPage", Json.createValue(action)).thenAccept(r -> {
		});
	}

	public void stopFindInPage(String action) {
		runSync(this.stopFindInPageAsync(action));
	}

	public CompletionStage<Void> printAsync(JsonObject options) {
		return this.ofInstance.invoke("print", options).thenAccept(r -> {
		});
	}

	public void print(JsonObject options) {
		runSync(this.printAsync(options));
	}
}
