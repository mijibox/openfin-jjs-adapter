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

## License
Apache License 2.0

The code in this repository is covered by the included license.

However, if you run this code, it may call on the OpenFin RVM or OpenFin Runtime, which are covered by OpenFin’s Developer, Community, and Enterprise licenses. You can learn more about OpenFin licensing at the links listed below or just email OpenFin at support@openfin.co with questions.

https://openfin.co/developer-agreement/ <br/>
https://openfin.co/licensing/
