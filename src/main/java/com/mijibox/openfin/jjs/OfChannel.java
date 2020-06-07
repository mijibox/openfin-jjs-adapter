package com.mijibox.openfin.jjs;

import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonObject;

import com.mijibox.openfin.gateway.OpenFinEventListener;
import com.mijibox.openfin.gateway.OpenFinGateway;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

public class OfChannel {

	public static CompletionStage<OfChannelProvider> createAsync(OpenFinGateway gateway, String channelName) {
		return gateway.invoke(true, "fin.InterApplicationBus.Channel.create", Json.createValue(channelName))
				.thenApply(r -> {
					return new OfChannelProvider(r.getProxyObject());
				});
	}

	public static OfChannelProvider create(OpenFinGateway gateway,String channelName) {
		return runSync(createAsync(gateway, channelName));
	}

	public static CompletionStage<OfChannelClient> connectAsync(OpenFinGateway gateway, String channelName, JsonObject options) {
		return gateway.invoke(true, "fin.InterApplicationBus.Channel.connect", Json.createValue(channelName), options)
				.thenApply(r -> {
					return new OfChannelClient(r.getProxyObject());
				});
	}

	public static OfChannelClient connect(OpenFinGateway gateway, String channelName, JsonObject options) {
		return runSync(connectAsync(gateway, channelName, options));
	}

	public static CompletionStage<Void> onChannelConnectAsync(OpenFinGateway gateway, OpenFinEventListener listener) {
		return gateway.addListener("fin.InterApplicationBus.Channel.onChannelConnect", listener)
				.thenAccept(v -> {

				});
	}

	public static void onChannelConnect(OpenFinGateway gateway, OpenFinEventListener listener) {
		runSync(onChannelConnectAsync(gateway, listener));
	}

	public static CompletionStage<Void> onChannelDisconnectAsync(OpenFinGateway gateway, OpenFinEventListener listener) {
		return gateway.addListener("fin.InterApplicationBus.Channel.onChannelDisconnect", listener)
				.thenAccept(v -> {

				});
	}

	public static void onChannelDisconnect(OpenFinGateway gateway, OpenFinEventListener listener) {
		runSync(onChannelConnectAsync(gateway, listener));
	}
}
