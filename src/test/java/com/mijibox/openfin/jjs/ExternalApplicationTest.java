package com.mijibox.openfin.jjs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mijibox.openfin.gateway.OpenFinGateway;
import com.mijibox.openfin.gateway.OpenFinLauncher;

public class ExternalApplicationTest {

	private final static Logger logger = LoggerFactory.getLogger(ExternalApplicationTest.class);

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
	public void getInfo() throws Exception {
		JsonArray extApps = OfSystem.getAllExternalApplications(gateway);
		logger.debug("extApps: {}", extApps);
		String extAppUuid = extApps.getJsonObject(0).getString("uuid");
		OfExternalApplication ofExtApp = OfExternalApplication.wrap(gateway, extAppUuid);
		JsonObject extAppInfo = ofExtApp.getInfo();
		logger.debug("extAppInfo: {}", extAppInfo);
		assertNotNull(extAppInfo);
	}

}
