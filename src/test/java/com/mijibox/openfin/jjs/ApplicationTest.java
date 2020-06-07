package com.mijibox.openfin.jjs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.OpenFinLauncher;

public class ApplicationTest {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

	private final static String RUNTIME_VERSION = "16.83.50.9";

	private static OpenFinGateway gateway;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("com.mijibox.openfin.gateway.showConsole", "true");
		gateway = OpenFinLauncher.newOpenFinLauncherBuilder()
				.licenseKey("JavaAdapterJunitTests")
				.runtimeVersion(RUNTIME_VERSION).open(null)
				.toCompletableFuture()
				.get();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		gateway.close().toCompletableFuture().get();
	}

	@Test
	public void startFromManifest() throws Exception {
		OfApplication app = OfApplication.startFromManifest(gateway,
				"https://cdn.openfin.co/demos/hello/app.json", null);
		try {
			app.quit(true);
		}
		catch (Exception e) {
			// openfin bug
		}
		assertNotNull(app);
	}

	@Test
	public void start() throws Exception {
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		assertNotNull(app);
		app.quit(true);
	}

	@Test
	public void getWindow() throws Exception {
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		OfWindow win = app.getWindow();
		assertNotNull(win);
		app.quit(true);
	}

	@Test
	public void isRunning() throws Exception {
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		Boolean b = app.isRunning();
		assertTrue(b);
		app.quit(true);
	}

	@Test
	public void wrap() throws Exception {
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		
		OfApplication wrappedApp = OfApplication.wrap(gateway, app.getIdentity());
		assertNotNull(wrappedApp);
		wrappedApp.quit(true);
	}

	@Test
	public void terminate() throws Exception {
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		app.terminate();
	}

	@Test
	public void getInfo() throws Exception {
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		JsonObject info = app.getInfo();
		logger.debug("info: {}", info);
		assertNotNull(info);
		app.quit(true);
	}
	
	@Test
	public void getManifest() throws Exception {
		OfApplication app = OfApplication.startFromManifest(gateway,
				"https://cdn.openfin.co/demos/hello/app.json", null);
		JsonObject manifest = app.getManifest();
		logger.debug("manifest: {}", manifest);
		assertNotNull(manifest);
		try {
			app.quit(true);
		}
		catch (Exception e) {
			// openfin bug
		}
	}
	
	@Test
	public void getManifestExpectedError() throws Exception {
		CompletableFuture<?> errorFuture = new CompletableFuture<>();
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		app.getManifestAsync().exceptionally(e->{
			logger.debug("expected error", e);
			errorFuture.complete(null);
			return null;
		});
		app.quit(true);
		errorFuture.get(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void getParentUuid() throws Exception {
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		String parentUuid = app.getParentUuid();
		logger.debug("parentUuid: {}", parentUuid);
		assertNotNull(parentUuid);
		app.quit(true);
	}
	
	@Test
	public void setGetZoomLevel() throws Exception {
		double zoomLevel = 3.5;
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		app.setZoomLevel(zoomLevel);
		double gotZoomLevel = app.getZoomLevel();
		app.quit(true);
		logger.debug("gotZoomLevel: {}", gotZoomLevel);
		assertEquals(zoomLevel, gotZoomLevel, 0.0000001);
	}
	
	@Test
	public void registerUser() throws Exception {
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", true)
				.build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		app.registerUser("MyName", "MyAppName");
		app.quit(true);
	}

}
