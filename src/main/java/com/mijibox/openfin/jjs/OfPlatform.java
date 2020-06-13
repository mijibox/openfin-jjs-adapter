package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfPlatform extends OfObject {

	protected OfPlatform(ProxyObject obj) {
		super(obj);
	}

	public static CompletionStage<OfPlatform> startFromManifestAsync(OpenFinGateway gateway, String manifestUrl,
			JsonObject rvmLaunchOptions) {
		return gateway.invoke(true, "fin.Platform.startFromManifest", Json.createValue(manifestUrl), rvmLaunchOptions)
				.thenApply(r -> {
					return new OfPlatform(r.getProxyObject());
				});
	}

	public static OfPlatform startFromManifest(OpenFinGateway gateway, String manifestUrl,
			JsonObject rvmLaunchOptions) {
		return runSync(startFromManifestAsync(gateway, manifestUrl, rvmLaunchOptions == null ? JsonValue.EMPTY_JSON_OBJECT : rvmLaunchOptions));
	}

	public static CompletionStage<OfPlatform> startAsync(OpenFinGateway gateway, JsonObject platformOptions) {
		return gateway.invoke(true, "fin.Platform.start", platformOptions)
				.thenApply(r -> {
					return new OfPlatform(r.getProxyObject());
				});
	}

	public static OfPlatform start(OpenFinGateway gateway, JsonObject platformOptions) {
		return runSync(startAsync(gateway, platformOptions));
	}

	public static CompletionStage<OfPlatform> wrapAsync(OpenFinGateway gateway, JsonObject identity) {
		return gateway.invoke(true, "fin.Platform.wrap", identity)
				.thenApply(r -> {
					return new OfPlatform(r.getProxyObject());
				});
	}

	public static OfPlatform wrap(OpenFinGateway gateway, JsonObject identity) {
		return runSync(wrapAsync(gateway, identity));
	}

	public CompletionStage<OfView> createViewAsync(JsonObject viewOpts) {
		return this.ofInstance.invoke(true, "createView", viewOpts)
				.thenApply(r -> {
					return new OfView(r.getProxyObject());
				});
	}

	public OfView createView(JsonObject viewOpts) {
		return runSync(createViewAsync(viewOpts));
	}

	public CompletionStage<OfView> reparentViewAsync(JsonObject viewIdentity, JsonObject target) {
		return this.ofInstance.invoke(true, "reparentView", viewIdentity, target)
				.thenApply(r -> {
					return new OfView(r.getProxyObject());
				});
	}

	public OfView reparentView(JsonObject viewIdentity, JsonObject target) {
		return runSync(reparentViewAsync(viewIdentity, target));
	}

	public CompletionStage<OfWindow> createWindowAsync(JsonObject winOpts) {
		return this.ofInstance.invoke(true, "createWindow", winOpts)
				.thenApply(r -> {
					return new OfWindow(r.getProxyObject());
				});
	}

	public OfWindow createWindow(JsonObject winOpts) {
		return runSync(createWindowAsync(winOpts));
	}

	public CompletionStage<Void> closeViewAsync(JsonObject viewIdentity) {
		return this.ofInstance.invoke(true, "closeView", viewIdentity)
				.thenAccept(r -> {
				});
	}

	public void closeView(JsonObject viewIdentity) {
		runSync(closeViewAsync(viewIdentity));
	}

	public CompletionStage<JsonObject> getSnapshotAsync() {
		return this.ofInstance.invoke("getSnapshot")
				.thenApply(r -> {
					return r.getResultAsJsonObject();
				});
	}

	public JsonObject getSnapshot() {
		return runSync(getSnapshotAsync());
	}
	
	public CompletionStage<Void> quitAsync() {
		return this.ofInstance.invoke("quit").thenAccept(r -> {
		});
	}

	public void quit() {
		runSync(quitAsync());
	}

	public CompletionStage<OfPlatform> applySnapshotAsync(JsonObject requestedSnapshot, JsonObject options) {
		return this.ofInstance.invoke(true, "applySnapshot", requestedSnapshot, options)
				.thenApply(r -> {
					return this;
				});
	}
	
	public OfPlatform applySnapshot(JsonObject requestedSnapshot, JsonObject options) {
		return runSync(applySnapshotAsync(requestedSnapshot, options));
	}

	public CompletionStage<OfPlatform> applySnapshotAsync(String snapshotUrl, JsonObject options) {
		return this.ofInstance.invoke(true, "applySnapshot", Json.createValue(snapshotUrl), options)
				.thenApply(r -> {
					return this;
				});
	}
	
	public OfPlatform applySnapshot(String snapshotUrl, JsonObject options) {
		return runSync(applySnapshotAsync(snapshotUrl, options));
	}


}
