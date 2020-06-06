package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.Json;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyListener;

public class OfGlobalHotkey {

	static ConcurrentHashMap<String, ProxyListener> listenerMap = new ConcurrentHashMap<>();

	static String getMapKey(OpenFinGateway gateway, String hotkey) {
		return gateway.getId() + ":" + hotkey;
	}

	public static CompletionStage<Boolean> isRegisteredAsync(OpenFinGateway gateway, String hotkey) {
		return gateway.invoke("fin.GlobalHotkey.isRegistered", Json.createValue(hotkey)).thenApply(r -> {
			return r.getResultAsBoolean();
		});
	}

	public static Boolean isRegistered(OpenFinGateway gateway, String hotkey) {
		return runSync(isRegisteredAsync(gateway, hotkey));
	}

	public static CompletionStage<Void> registerAsync(OpenFinGateway gateway, String hotkey,
			OpenFinEventListener listener) {
		return gateway.addListener(true, "fin.GlobalHotkey.register", hotkey, listener).thenAccept(proxyListener -> {
			listenerMap.put(getMapKey(gateway, hotkey), proxyListener);
		});
	}

	public static void register(OpenFinGateway gateway, String hotkey, OpenFinEventListener listener) {
		runSync(registerAsync(gateway, hotkey, listener));
	}

	public static CompletionStage<Void> unregisterAsync(OpenFinGateway gateway, String hotkey) {
		ProxyListener listener = listenerMap.remove(getMapKey(gateway, hotkey));
		if (listener != null) {
			return gateway.removeListener("fin.GlobalHotkey.unregister", hotkey, listener);
		}
		else {
			return CompletableFuture.completedStage(null);
		}
	}

	public static void unregister(OpenFinGateway gateway, String hotkey) {
		runSync(unregisterAsync(gateway, hotkey));
	}

	public static CompletionStage<Void> unregisterAllAsync(OpenFinGateway gateway) {
		return gateway.invoke("fin.GlobalHotkey.unregisterAll").thenCompose(r -> {
			ArrayList<CompletionStage<Void>> disposeFuture = new ArrayList<>();
			listenerMap.values().forEach(l -> {
				disposeFuture.add(l.dispose());
			});
			listenerMap.clear();
			return CompletableFuture.allOf(disposeFuture.toArray(new CompletableFuture[0]));
		});
	}

	public static void unregisterAll(OpenFinGateway gateway) {
		runSync(unregisterAllAsync(gateway));
	}
}
