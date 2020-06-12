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

	public static CompletionStage<Void> deleteCacheOnExitAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.deleteCacheOnExit").thenAccept(r -> {
		});
	}

	public static void deleteCacheOnExit(OpenFinGateway gateway) {
		runSync(deleteCacheOnExitAsync(gateway));
	}

	public static CompletionStage<ProxyListener> addListenerAsync(OpenFinGateway gateway, String event,
			OpenFinEventListener listener) {
		return gateway.addListener(true, "fin.System.on", event, listener);
	}

	public static ProxyListener addListener(OpenFinGateway gateway, String event, OpenFinEventListener listener) {
		return runSync(addListenerAsync(gateway, event, listener));
	}

	public static CompletionStage<Void> removeListenerAsync(OpenFinGateway gateway, String event,
			ProxyListener listener) {
		return gateway.removeListener("removeListener", event, listener);
	}

	public static void removeListener(OpenFinGateway gateway, String event, ProxyListener listener) {
		runSync(removeListenerAsync(gateway, event, listener));
	}

	public static CompletionStage<Void> downloadAssetAsync(OpenFinGateway gateway, JsonObject appAsset) {
		return gateway.invoke("fin.System.downloadAsset", appAsset).thenAccept(r -> {
		});
	}

	public static void downloadAsset(OpenFinGateway gateway, JsonObject appAsset) {
		runSync(downloadAssetAsync(gateway, appAsset));
	}

	public static CompletionStage<JsonArray> downloadPreloadScriptsAsync(OpenFinGateway gateway, JsonArray scripts) {
		return gateway.invoke("fin.System.downloadPreloadScripts", scripts).thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}

	public static JsonArray downloadPreloadScripts(OpenFinGateway gateway, JsonArray scripts) {
		return runSync(downloadPreloadScriptsAsync(gateway, scripts));
	}

	public static CompletionStage<Void> downloadRuntimeAsync(OpenFinGateway gateway, JsonObject options,
			OpenFinEventListener progressListener) {
		return gateway.addListener(false, "fin.System.downloadRuntime", progressListener, 1, options).thenAccept(r -> {

		});
	}

	public static void downloadRuntime(OpenFinGateway gateway, JsonObject options, OpenFinEventListener listener) {
		runSync(downloadRuntimeAsync(gateway, options, listener));
	}

	public static CompletionStage<Void> exitAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.exit").thenAccept(r -> {
		});
	}

	public static void exit(OpenFinGateway gateway) {
		runSync(exitAsync(gateway));
	}

	public static CompletionStage<Void> flushCookieStoreAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.flushCookieStore").thenAccept(r -> {
		});
	}

	public static void flushCookieStore(OpenFinGateway gateway) {
		runSync(flushCookieStoreAsync(gateway));
	}

	public static CompletionStage<JsonObject> getAppAssetInfoAsync(OpenFinGateway gateway, JsonObject options) {
		return gateway.invoke("fin.System.getAppAssetInfo", options).thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public static JsonObject getAppAssetInfo(OpenFinGateway gateway, JsonObject options) {
		return runSync(getAppAssetInfoAsync(gateway, options));
	}

	public static CompletionStage<JsonObject> getCookiesAsync(OpenFinGateway gateway, JsonObject options) {
		return gateway.invoke("fin.System.getCookies", options).thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public static JsonObject getCookies(OpenFinGateway gateway, JsonObject options) {
		return runSync(getCookiesAsync(gateway, options));
	}

	public static CompletionStage<JsonObject> getCrashReporterStateAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getCrashReporterState").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public static JsonObject getCrashReporterState(OpenFinGateway gateway) {
		return runSync(getCrashReporterStateAsync(gateway));
	}

	public static CompletionStage<String> getDeviceUserIdAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getDeviceUserId").thenApply(r -> {
			return r.getResultAsString();
		});
	}

	public static String getDeviceUserId(OpenFinGateway gateway) {
		return runSync(getDeviceUserIdAsync(gateway));
	}

	public static CompletionStage<JsonObject> getEntityInfoAsync(OpenFinGateway gateway, String uuid, String name) {
		return gateway.invoke("fin.System.getEntityInfo", Json.createValue(uuid), Json.createValue(name))
				.thenApply(r -> {
					return r.getResultAsJsonObject();
				});
	}

	public static JsonObject getEntityInfo(OpenFinGateway gateway, String uuid, String name) {
		return runSync(getEntityInfoAsync(gateway, uuid, name));
	}

	public static CompletionStage<String> getEnvironmentVariableAsync(OpenFinGateway gateway, String name) {
		return gateway.invoke("fin.System.getEnvironmentVariable", Json.createValue(name))
				.thenApply(r -> {
					return r.getResultAsString();
				});
	}

	public static String getEnvironmentVariable(OpenFinGateway gateway, String name) {
		return runSync(getEnvironmentVariableAsync(gateway, name));
	}

	public static CompletionStage<JsonObject> getFocusedWindowAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getFocusedWindow")
				.thenApply(r -> {
					return r.getResultAsJsonObject();
				});
	}

	public static JsonObject getFocusedWindow(OpenFinGateway gateway) {
		return runSync(getFocusedWindowAsync(gateway));
	}

	public static CompletionStage<JsonObject> getHostSpecsAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getHostSpecs")
				.thenApply(r -> {
					return r.getResultAsJsonObject();
				});
	}

	public static JsonObject getHostSpecs(OpenFinGateway gateway) {
		return runSync(getHostSpecsAsync(gateway));
	}

	public static CompletionStage<JsonArray> getInstalledRuntimesAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getInstalledRuntimes")
				.thenApply(r -> {
					return r.getResultAsJsonArray();
				});
	}

	public static JsonArray getInstalledRuntimes(OpenFinGateway gateway) {
		return runSync(getInstalledRuntimesAsync(gateway));
	}

	public static CompletionStage<String> getLogAsync(OpenFinGateway gateway, JsonObject options) {
		return gateway.invoke("fin.System.getLog", options)
				.thenApply(r -> {
					return r.getResultAsString();
				});
	}

	public static String getLog(OpenFinGateway gateway, JsonObject options) {
		return runSync(getLogAsync(gateway, options));
	}

	public static CompletionStage<JsonArray> getLogListAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getLogList").thenApply(r -> {
			return r.getResultAsJsonArray();
		});
	}

	public static JsonArray getLogList(OpenFinGateway gateway) {
		return runSync(getLogListAsync(gateway));
	}

	public static CompletionStage<JsonObject> getMinLogLevelAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.System.getMinLogLevel").thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public static JsonObject getMinLogLevel(OpenFinGateway gateway) {
		return runSync(getMinLogLevelAsync(gateway));
	}

	public static CompletionStage<JsonObject> getServiceConfigurationAsync(OpenFinGateway gateway, JsonObject serviceIdentifier) {
		return gateway.invoke("fin.System.getServiceConfiguration", serviceIdentifier).thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public static JsonObject getServiceConfiguration(OpenFinGateway gateway, JsonObject serviceIdentifier) {
		return runSync(getServiceConfigurationAsync(gateway, serviceIdentifier));
	}

	public static CompletionStage<JsonObject> launchExternalProcessAsync(OpenFinGateway gateway, JsonObject options) {
		return gateway.invoke("fin.System.launchExternalProcess", options).thenApply(r -> {
			return r.getResultAsJsonObject();
		});
	}

	public static JsonObject launchExternalProcess(OpenFinGateway gateway, JsonObject options) {
		return runSync(launchExternalProcessAsync(gateway, options));
	}
	
	public static CompletionStage<JsonObject> logAsync(OpenFinGateway gateway, String level, String message) {
		return gateway.invoke("fin.System.log", Json.createValue(level), Json.createValue(message))
				.thenApply(r -> {
					return r.getResultAsJsonObject();
				});
	}

	public static JsonObject log(OpenFinGateway gateway, String level, String message) {
		return runSync(logAsync(gateway, level, message));
	}



}
