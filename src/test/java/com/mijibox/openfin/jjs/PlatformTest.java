package com.mijibox.openfin.jjs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.OpenFinGatewayLauncher;
import com.mijibox.openfin.gateway.OpenFinLauncher;

public class PlatformTest {

	private final static Logger logger = LoggerFactory.getLogger(PlatformTest.class);

	private final static String RUNTIME_VERSION = "16.83.50.9";

	private static OpenFinGateway gateway;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("com.mijibox.openfin.gateway.showConsole", "true");
		gateway = OpenFinGatewayLauncher.newOpenFinGatewayLauncher()
				.launcherBuilder(OpenFinLauncher.newOpenFinLauncherBuilder()
						.runtimeVersion("stable")
						.addRuntimeOption("--v=1")
						.addRuntimeOption("--no-sandbox"))
				.open()
				.exceptionally(e -> {
					e.printStackTrace();
					return null;
				})
				.toCompletableFuture().get(120, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		gateway.close().toCompletableFuture().get();
	}

	@Test
	public void startFromManifest() throws Exception {
		OfPlatform platform = OfPlatform.startFromManifest(gateway,
				"https://openfin.github.io/golden-prototype/public.json", null);
		try {
			platform.quit();
		}
		catch (Exception e) {
			// bug in OpenFin, ignore error.
		}
		assertNotNull(platform);
	}

	@Test
	public void start() throws Exception {
		OfPlatform platform = OfPlatform.start(gateway,
				Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString()).build());
		assertNotNull(platform);
	}

	@Test
	public void wrap() throws Exception {
		JsonObject platformIdentity = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString()).build();
		OfPlatform platform = OfPlatform.start(gateway, platformIdentity);
		OfPlatform wrappedPlatform = OfPlatform.wrap(gateway, platformIdentity);
		assertNotNull(wrappedPlatform);
	}

	@Test
	public void createCloseView() throws Exception {
		JsonObject platformIdentity = Json.createObjectBuilder().add("uuid", UUID.randomUUID().toString()).build();
		OfPlatform platform = OfPlatform.start(gateway, platformIdentity);
		OfView view = platform.createView(Json.createObjectBuilder().add("url", "https://www.google.com").build());
		assertNotNull(view);
		platform.closeView(view.getIdentity());
	}

	@Test
	public void getAndApplySnapshot() throws Exception {
		OfPlatform platform = OfPlatform.startFromManifest(gateway,
				"https://openfin.github.io/golden-prototype/public.json", null);
		JsonObject platformIdentity = platform.getIdentity();
		JsonObject snapshot = platform.getSnapshot();
		logger.debug("snapshot: {}", snapshot);
		assertNotNull(snapshot);
		try {
			platform.quit();
		}
		catch (Exception e) {
			// bug in OpenFin, ignore error.
		}
		OfPlatform newPlatform = OfPlatform.start(gateway, platformIdentity); 
		OfPlatform returnedPlatform = newPlatform.applySnapshot(snapshot, null);
		assertNotNull(returnedPlatform);
		returnedPlatform.quitAsync();
	}
}
