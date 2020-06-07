package com.mijibox.openfin.jjs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.OpenFinLauncher;

public class SystemTest {
	private final static Logger logger = LoggerFactory.getLogger(SystemTest.class);
	
	private final static String RUNTIME_VERSION = "16.83.50.9";

	private static OpenFinGateway gateway;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gateway = OpenFinLauncher.newOpenFinLauncherBuilder().runtimeVersion(RUNTIME_VERSION).open(null).toCompletableFuture()
				.get();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		gateway.close().toCompletableFuture().get();
	}

	@Test
	public void getVersion() throws Exception {
		String v = OfSystem.getVersion(gateway);
		logger.debug("getVersion: {}", v);
		assertEquals(RUNTIME_VERSION, v);
	}

	@Test
	public void getMachineId() throws Exception {
		String mId = OfSystem.getMachineId(gateway);
		logger.debug("getMachineId: {}", mId);
		assertNotNull(mId);
	}

	@Test
	public void getMonitorInfo() throws Exception {
		JsonObject monitorInfo = OfSystem.getMonitorInfo(gateway);
		logger.debug("getMonitorInfo: {}", monitorInfo);
		assertNotNull(monitorInfo);
	}

	@Test
	public void getMousePosition() throws Exception {
		JsonObject mousePosition = OfSystem.getMousePosition(gateway);
		logger.debug("getMousePosition: {}", mousePosition);
		assertNotNull(mousePosition);
	}

	@Test
	public void getProcessList() throws Exception {
		JsonArray processList = OfSystem.getProcessList(gateway);
		logger.debug("getProcessList: {}", processList);
		assertNotNull(processList);
	}

	@Test
	public void getAllApplications() throws Exception {
		JsonArray applications = OfSystem.getAllApplications(gateway);
		logger.debug("getAllApplications: {}", applications);
		assertNotNull(applications);
	}

	@Test
	public void getAllExternalApplications() throws Exception {
		JsonArray extApps = OfSystem.getAllExternalApplications(gateway);
		logger.debug("getAllExternalApplications: {}", extApps);
		assertNotNull(extApps);
	}

	@Test
	public void getAllWindows() throws Exception {
		JsonArray windows = OfSystem.getAllWindows(gateway);
		logger.debug("getAllWindows: {}", windows);
		assertNotNull(windows);
	}

	@Test
	public void getProxySettings() throws Exception {
		JsonObject proxySettings = OfSystem.getProxySettings(gateway);
		logger.debug("getProxySettings: {}", proxySettings);
		assertNotNull(proxySettings);
	}

	@Test
	public void getRuntimeInfo() throws Exception {
		JsonObject runtimeInfo = OfSystem.getRuntimeInfo(gateway);
		logger.debug("getRuntimeInfo: {}", runtimeInfo);
		assertNotNull(runtimeInfo);
	}

	@Test
	public void getRvmInfo() throws Exception {
		JsonObject rvmInfo = OfSystem.getRvmInfo(gateway);
		logger.debug("getRvmInfo: {}", rvmInfo);
		assertNotNull(rvmInfo);
	}
	
	@Test
	public void getUniqueUserId() throws Exception {
		String userId = OfSystem.getUniqueUserId(gateway);
		logger.debug("getUniqueUserId: {}", userId);
		assertNotNull(userId);
	}
	
	@Test
	public void getCommandLineArguments() throws Exception {
		String args = OfSystem.getCommandLineArguments(gateway);
		logger.debug("getCommandLineArguments: {}", args);
		assertNotNull(args);
	}
	
	@Test
	public void openUrlWithBrowser() throws Exception {
		OfSystem.openUrlWithBrowser(gateway, "https://www.google.com");
	}

	@Test
	public void clearCache() throws Exception {
		OfSystem.clearCache(gateway, null);
	}

}
