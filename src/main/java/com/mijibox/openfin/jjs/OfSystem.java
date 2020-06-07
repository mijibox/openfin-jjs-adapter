package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

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

	public static CompletionStage<String> getMachineIdAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getMachineId").thenApply(r -> {
			return r.getResultAsString();
		});
	}
	
	public static String getMachineId(OpenFinGateway gateway) {
		return runSync(getMachineIdAsync(gateway));
	}

	public static CompletionStage<JsonObject> getMonitorInfoAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getMonitorInfo").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public static JsonObject getMonitorInfo(OpenFinGateway gateway) {
		return runSync(getMonitorInfoAsync(gateway));
	}

	public static CompletionStage<JsonObject> getMousePositionAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getMousePosition").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public static JsonObject getMousePosition(OpenFinGateway gateway) {
		return runSync(getMousePositionAsync(gateway));
	}

	public static CompletionStage<JsonArray> getProcessListAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getProcessList").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}
	
	public static JsonArray getProcessList(OpenFinGateway gateway) {
		return runSync(getProcessListAsync(gateway));
	}

	public static CompletionStage<JsonArray> getAllApplicationsAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getAllApplications").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}
	
	public static JsonArray getAllApplications(OpenFinGateway gateway) {
		return runSync(getAllApplicationsAsync(gateway));
	}

	public static CompletionStage<JsonArray> getAllExternalApplicationsAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getAllExternalApplications").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}
	
	public static JsonArray getAllExternalApplications(OpenFinGateway gateway) {
		return runSync(getAllExternalApplicationsAsync(gateway));
	}

	public static CompletionStage<JsonArray> getAllWindowsAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getAllWindows").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}
	
	public static JsonArray getAllWindows(OpenFinGateway gateway) {
		return runSync(getAllWindowsAsync(gateway));
	}

	public static CompletionStage<JsonObject> getProxySettingsAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getProxySettings").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public static JsonObject getProxySettings(OpenFinGateway gateway) {
		return runSync(getProxySettingsAsync(gateway));
	}

	public static CompletionStage<JsonObject> getRuntimeInfoAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getRuntimeInfo").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public static JsonObject getRuntimeInfo(OpenFinGateway gateway) {
		return runSync(getRuntimeInfoAsync(gateway));
	}

	public static CompletionStage<JsonObject> getRvmInfoAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getRvmInfo").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}
	
	public static JsonObject getRvmInfo(OpenFinGateway gateway) {
		return runSync(getRvmInfoAsync(gateway));
	}

	public static CompletionStage<String> getUniqueUserIdAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getUniqueUserId").thenApply(r -> {
			return r.getResultAsString();
		});
	}
	
	public static String getUniqueUserId(OpenFinGateway gateway) {
		return runSync(getUniqueUserIdAsync(gateway));
	}

	public static CompletionStage<String> getCommandLineArgumentsAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getCommandLineArguments").thenApply(r -> {
			return r.getResultAsString();
		});
	}
	
	public static String getCommandLineArguments(OpenFinGateway gateway) {
		return runSync(getCommandLineArgumentsAsync(gateway));
	}

	public static CompletionStage<Void> openUrlWithBrowserAsync(OpenFinGateway gateway, String url) {
		return gateway.invoke("fin.System.openUrlWithBrowser", Json.createValue(url)).thenAccept(r -> {
		});
	}
	
	public static void openUrlWithBrowser(OpenFinGateway gateway, String url) {
		runSync(openUrlWithBrowserAsync(gateway, url));
	}

	public static CompletionStage<Void> clearCacheAsync(OpenFinGateway gateway, JsonObject options) {
		return gateway.invoke("fin.System.clearCache", options).thenAccept(r -> {
		});
	}
	
	public static void clearCache(OpenFinGateway gateway, JsonObject options) {
		runSync(clearCacheAsync(gateway, options));
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
