package com.mijibox.openfin.jjs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
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

public class ClipboardTest {

	private final static Logger logger = LoggerFactory.getLogger(ClipboardTest.class);

	private static OpenFinGateway gateway;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void getAvailableFormats() throws Exception {
		List<String> formats = OfClipboard.getAvailableFormats(gateway, null);
		logger.debug(formats.toString());
		assertNotNull(formats);
	}

	@Test
	public void readHtml() throws Exception {
		String html = OfClipboard.readHtml(gateway, null);
		logger.debug("readHtml: {}", html);
	}

	@Test
	public void readWriteText() throws Exception {
		String TEXT = "testWriteThenReadText";
		OfClipboard.writeText(gateway, TEXT, null);
		assertEquals(TEXT, OfClipboard.readText(gateway, null));
	}

	@Test
	public void readWriteHtml() throws Exception {
		String HTML = "<h4>readWriteHtml</h4>";
		OfClipboard.writeHtml(gateway, HTML, null);
		assertEquals(HTML, OfClipboard.readHtml(gateway, null));
	}

	@Test
	public void readWriteRtf() throws Exception {
		String RTF = " {\\rtf1\\ansi{\\fonttbl\\f0\\fswiss Helvetica;}\\f0\\pard\r\n" +
				" This is some {\\b bold} text.\\par\r\n" +
				" }";
		OfClipboard.writeRtf(gateway, RTF, null);
		assertEquals(RTF, OfClipboard.readRtf(gateway, null));
	}

	@Test
	public void writeRead() throws Exception {
		String TEXT = "writeRead";
		String HTML = "<h4>readWriteHtml</h4>";
		String RTF = " {\\rtf1\\ansi{\\fonttbl\\f0\\fswiss Helvetica;}\\f0\\pard\r\n" +
				" This is some {\\b bold} text.\\par\r\n" +
				" }";
		JsonObject data = Json.createObjectBuilder()
				.add("text", TEXT)
				.add("html", HTML)
				.add("rtf", RTF)
				.build();
		OfClipboard.write(gateway, data, null);
		assertEquals(TEXT, OfClipboard.readText(gateway, null));
		assertEquals(HTML, OfClipboard.readHtml(gateway, null));
		assertEquals(RTF, OfClipboard.readRtf(gateway, null));

	}
}
