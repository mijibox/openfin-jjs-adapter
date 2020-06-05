package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.Utils.runSync;

import java.util.concurrent.CompletionStage;

import javax.json.Json;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.ProxyObject;

public class OfWindow extends OfObject {

	OfWindow(ProxyObject obj, OpenFinGateway gateway) {
		super(obj, gateway);
	}
	
	public CompletionStage<Void> navigateAsync(String url) {
		return this.ofInstance.invoke("navigate", Json.createValue(url)).thenAccept(r ->{
		});
	}

	public void navigate(String url) {
		runSync(navigateAsync(url));
	}
}
