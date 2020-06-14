# OpenFin Java-JavaScript Adapter
OpenFin Java-JavaScript Adapter using [OpenFin Java Gateway](https://github.com/mijibox/openfin-java-gateway)

Almost all OpenFin JavaScript v2 API are implemented. Whatever marked as "EXPERIMENTAL" were not implemented.

All API calls have both synchronized and asynchronized methods, so developers can choose they way they like to program. ;-)  

```java
OpenFinGatewayLauncher.newOpenFinGatewayLauncher().open().thenAccept(gateway ->{
	OfApplication app = OfApplication.startFromManifest(gateway,
			"https://cdn.openfin.co/demos/hello/app.json", null);
	app.setZoomLevel(3.5);
});

```

Or 

```java
OpenFinGatewayLauncher.newOpenFinGatewayLauncher().open().thenCompose(gateway ->{
		return OfApplication.startFromManifestAsync(gateway,
			"https://cdn.openfin.co/demos/hello/app.json", null)
	})
	.thenCompose(app ->{
		return app.setZoomLevelAsync(3.5);
	});
});

```
