package com.mijibox.openfin.jjs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.OpenFinGatewayLauncher;
import com.mijibox.openfin.gateway.OpenFinLauncher;

public class WindowTest {
	private final static Logger logger = LoggerFactory.getLogger(WindowTest.class);

	private final static String RUNTIME_VERSION = "16.83.50.9";

	private static OpenFinGateway gateway;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("com.mijibox.openfin.gateway.showConsole", "true");
		gateway = OpenFinGatewayLauncher.newOpenFinGatewayLauncher()
				.launcherBuilder(OpenFinLauncher.newOpenFinLauncherBuilder()
						.licenseKey("JavaAdapterJunitTests")
						.runtimeVersion(RUNTIME_VERSION)
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
	public void create() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		win.close(true);
		assertNotNull(win);
	}

	@Test
	public void createForOtherApp() throws Exception {
		CompletableFuture<?> errorFuture = new CompletableFuture<>();
		String appUuid = UUID.randomUUID().toString();
		JsonObject appOpts = Json.createObjectBuilder().add("uuid", appUuid).build();
		OfApplication app = OfApplication.start(gateway, appOpts);
		OfWindow.createAsync(gateway, Json.createObjectBuilder()
				.add("uuid", appUuid)
				.add("name", UUID.randomUUID().toString())
				.build()).exceptionally(e->{
					logger.debug("expected error", e);
					app.quit(true);
					errorFuture.complete(null);
					return null;
				});
		errorFuture.get(10,  TimeUnit.SECONDS);
	}

	@Test
	public void flash() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		win.flash();
		win.close(true);
	}

	@Test
	public void stopFlashing() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		win.flash();
		win.stopFlashing();
		win.stopFlashing();
		win.close(true);
	}

	@Test
	public void focus() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		win.focus();
		win.close(true);
	}

	@Test
	public void blur() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		win.blur();
		win.close(true);
	}

	@Test
	public void bringToFront() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		win.bringToFront();
		win.close(true);
	}

	@Test
	public void center() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		win.center();
		win.close(true);
	}

	@Test
	public void getNativeId() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		String nativeId = win.getNativeId();
		logger.debug("nativeId: {}", nativeId);
		win.close(true);
		assertNotNull(nativeId);
	}

	@Test
	public void getSnapshot() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		String encodedSnapshot = win.getSnapshot(null);
		logger.debug("encodedSnapshot: {}", encodedSnapshot);
		String encodedSnapshot2 = win.getSnapshot(Json.createObjectBuilder()
				.add("x", 10)
				.add("y", 10)
				.add("width", 50)
				.add("height", 50)
				.build());
		logger.debug("encodedSnapshot2: {}", encodedSnapshot2);
		win.close(true);
		assertNotNull(encodedSnapshot);
	}

	@Ignore
	@Test
	public void isMainWindow() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		boolean mainWindow = win.isMainWindow();
		logger.debug("mainWindow: {}", mainWindow);
		win.close(true);
		assertFalse(mainWindow); //openfin bug.
	}

	@Test
	public void isShowing() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		boolean showing = win.isShowing();
		logger.debug("isShowing: {}", showing);
		win.close(true);
		assertTrue(showing);
	}

	@Test
	public void getAllFrames() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		JsonArray frames = win.getAllFrames();
		logger.debug("getAllFrames: {}", frames);
		win.close(true);
		assertNotNull(frames);
	}

	@Test
	public void getBounds() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		JsonObject bounds = win.getBounds();
		logger.debug("getBounds: {}", bounds);
		win.close(true);
		assertNotNull(bounds);
	}

	@Test
	public void getInfo() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		JsonObject winInfo = win.getInfo();
		logger.debug("getInfo: {}", winInfo);
		win.close(true);
		assertNotNull(winInfo);
	}

	@Test
	public void getPrinters() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		JsonArray winInfo = win.getPrinters();
		logger.debug("getPrinters: {}", winInfo);
		win.close(true);
		assertNotNull(winInfo);
	}

	@Test
	public void getOptions() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		JsonObject winOpts = win.getOptions();
		logger.debug("getOptions: {}", winOpts);
		win.close(true);
		assertNotNull(winOpts);
	}

	@Test
	public void getParentApplication() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		OfApplication parentApp = win.getParentApplication();
		assertNotNull(parentApp);
	}

	@Test
	public void getParentWindow() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		OfWindow parentWin = win.getParentWindow();
		logger.debug("getParentWindow: {}", parentWin.getIdentity());
		parentWin.flash();
		assertNotNull(parentWin);
	}

	@Test
	public void navigation() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder().add("name", UUID.randomUUID().toString()).build());
		win.navigate("https://www.google.com");
		win.navigate("https://www.apple.com");
		win.navigateBack();
		win.reload();
		win.navigateForward();
		win.navigateAsync("https://www.cnn.com").exceptionally(e->{
			logger.debug("navigation stopped", e);
			return null;
		});
		Thread.sleep(1000);
		win.stopNavigation();
		win.close(true);
	}

	@Test
	public void windowState() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("defaultCentered", true)
				.add("defaultWidth", 800)
				.add("defaultHeight", 600)
				.build());
		win.minimize();
		String minimizedState = win.getState();
		win.maximize();
		String maximizedState = win.getState();
		win.restore();
		String restoredState = win.getState();
		win.close(true);
		assertEquals("minimized", minimizedState);
		assertEquals("maximized", maximizedState);
		assertEquals("normal", restoredState);
	}

	@Test
	public void showHide() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		win.hide();
		boolean isShowingAfterHide = win.isShowing();
		win.show(true);
		boolean isShowingAfterShow = win.isShowing();
		win.close(true);
		assertFalse(isShowingAfterHide);
		assertTrue(isShowingAfterShow);
	}

	@Test
	public void findInPage() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		JsonObject result = win.findInPage("google", null);
		win.close(true);
		assertNotNull(result);
	}

	@Test
	public void showDeveloperTools() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		win.showDeveloperTools();
		win.close(true);
	}

	@Test
	public void getGroup() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		JsonArray group = win.getGroup();
		logger.debug("getGroup(): ", group);
		win.close(true);
		assertNotNull(group);
	}

	@Test
	public void moveBy() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		win.moveBy(500, 250, null);
		win.close(true);
	}

	@Test
	public void moveTo() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		win.moveTo(800, 400, null);
		win.close(true);
	}

	@Test
	public void resizeBy() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		win.resizeBy(500, 250, null, null);
		win.close(true);
	}

	@Test
	public void resizeTo() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		win.resizeTo(800, 400, null, null);
		win.close(true);
	}

	@Test
	public void setAsForeground() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		win.setAsForeground();
		win.close(true);
	}

	@Test
	public void setBounds() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		win.setBounds(Json.createObjectBuilder()
				.add("top", 150)
				.add("left", 300)
				.add("width", 800)
				.add("height", 600)
				.build(), null);
		win.close(true);
	}

	@Test
	public void showAt() throws Exception {
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.add("autoShow", false)
				.build());
		
		win.showAt(500, 250, true, null);
		win.close(true);
	}

	@Test
	public void updateOptions() throws Exception {
		CompletableFuture<?> errorFuture = new CompletableFuture<>();
		OfWindow win = OfWindow.create(gateway, Json.createObjectBuilder()
				.add("name", UUID.randomUUID().toString())
				.add("url", "https://www.google.com")
				.build());
		
		win.updateOptionsAsync(Json.createObjectBuilder().add("maxWidth", 200).build())
		.thenAccept(v->{
			win.resizeTo(500, 300, null, null);
		})
		.exceptionally(e->{
			logger.debug("expected error", e);
			win.close(true);
			errorFuture.complete(null);
			return null;
		});
		
		errorFuture.get(5, TimeUnit.SECONDS);
	}

}
