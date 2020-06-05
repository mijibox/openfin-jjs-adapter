package com.mijibox.openfin.jjs;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

public class OfChannel {

	public static CompletionStage<OfChannelProvider> createAsync(String channelName, OpenFinGateway gateway) {
		return gateway.invoke(true, "fin.InterApplicationBus.Channel.create", Json.createValue(channelName))
				.thenApply(r -> {
					return new OfChannelProvider(r.getProxyObject(), gateway);
				});
	}

	public static OfChannelProvider create(String channelName, JsonObject options,
			OpenFinGateway gateway) {
		return runSync(createAsync(channelName, gateway));
	}

	public static CompletionStage<OfChannelClient> connectAsync(String channelName, JsonObject options,
			OpenFinGateway gateway) {
		return gateway.invoke(true, "fin.InterApplicationBus.Channel.connect", Json.createValue(channelName), options)
				.thenApply(r -> {
					return new OfChannelClient(r.getProxyObject(), gateway);
				});
	}

	public static OfChannelClient connect(String channelName, JsonObject options,
			OpenFinGateway gateway) {
		return runSync(connectAsync(channelName, options, gateway));
	}

	public static CompletionStage<Void> onChannelConnectAsync(OpenFinEventListener listener, OpenFinGateway gateway) {
		return gateway.addListener(false, "fin.InterApplicationBus.Channel.onChannelConnect", listener)
				.thenAccept(v -> {

				});
	}

	public static void onChannelConnect(OpenFinEventListener listener, OpenFinGateway gateway) {
		runSync(onChannelConnectAsync(listener, gateway));
	}

	public static CompletionStage<Void> onChannelDisconnectAsync(OpenFinEventListener listener,
			OpenFinGateway gateway) {
		return gateway.addListener(false, "fin.InterApplicationBus.Channel.onChannelDisconnect", listener)
				.thenAccept(v -> {

				});
	}

	public static void onChannelDisconnect(OpenFinEventListener listener, OpenFinGateway gateway) {
		runSync(onChannelConnectAsync(listener, gateway));
	}
}
