package com.mijibox.openfin.jjs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.OpenFinLauncher;

public class GlobalHotkeyTest {
	private final static Logger logger = LoggerFactory.getLogger(GlobalHotkeyTest.class);

	private static OpenFinGateway gateway;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gateway = OpenFinLauncher.newOpenFinLauncherBuilder().runtimeVersion("stable").open(null).toCompletableFuture()
				.get();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		gateway.close().toCompletableFuture().get();
	}

	@Test
	public void noRegister() throws Exception {
		String hotkey = "Shift+F6";
		Boolean b = OfGlobalHotkey.isRegistered(gateway, hotkey);
		assertFalse(b);
	}

	@Test
	public void registerAndCheck() throws Exception {
		String hotkey = "Shift+F7";
		OfGlobalHotkey.register(gateway, hotkey, e -> {
			return null;
		});
		Boolean b = OfGlobalHotkey.isRegistered(gateway, hotkey);
		assertTrue(b);
	}

	@Test
	public void unregisterAndCheck() throws Exception {
		String hotkey = "Shift+F8";
		OfGlobalHotkey.register(gateway, hotkey, e -> {
			return null;
		});
		OfGlobalHotkey.unregister(gateway, hotkey);
		Boolean b = OfGlobalHotkey.isRegistered(gateway, hotkey);
		assertFalse(b);
	}

	@Test
	public void unregisterAllAndCheck() throws Exception {
		String hotkey1 = "Shift+F9";
		String hotkey2 = "Shift+F10";
		String hotkey3 = "Shift+F11";
		OfGlobalHotkey.register(gateway, hotkey1, e -> {
			return null;
		});
		OfGlobalHotkey.register(gateway, hotkey2, e -> {
			return null;
		});
		OfGlobalHotkey.register(gateway, hotkey3, e -> {
			return null;
		});
		OfGlobalHotkey.unregisterAll(gateway);
		Boolean b = OfGlobalHotkey.isRegistered(gateway, hotkey1);
		assertFalse(b);
		b = OfGlobalHotkey.isRegistered(gateway, hotkey2);
		assertFalse(b);
		b = OfGlobalHotkey.isRegistered(gateway, hotkey3);
		assertFalse(b);
	}

	@Test
	public void registerShallFail() throws Exception {
		CompletableFuture<?> failFuture = new CompletableFuture<>();
		OfGlobalHotkey.registerAsync(gateway, "F5", e -> {
			return null;
		}).exceptionally(e -> {
			logger.debug("expected error", e);
			failFuture.complete(null);
			return null;
		});
		failFuture.get(5, TimeUnit.SECONDS);
	}
}
