package com.mijibox.openfin.jjs;

import static com.mijibox.openfin.jjs.OfUtils.runSync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import com.mijibox.openfin.gateway.OpenFinGateway;

public class OfClipboard {
	public static CompletionStage<List<String>> getAvailableFormatsAsync(OpenFinGateway gateway, String type) {
		JsonValue typeJson = type == null ? null : Json.createValue(type);
		return gateway.invoke("fin.Clipboard.getAvailableFormats", typeJson).thenApply(r -> {
			JsonArray f = r.getResultAsJsonArray();
			ArrayList<String> formats = new ArrayList<>();
			for (int i = 0; i < f.size(); i++) {
				formats.add(f.getString(i));
			}
			return formats;
		});
	}

	public static List<String> getAvailableFormats(OpenFinGateway gateway, String type) {
		return runSync(getAvailableFormatsAsync(gateway, type));
	}

	public static CompletionStage<String> readHtmlAsync(OpenFinGateway gateway, String type) {
		JsonValue typeJson = type == null ? null : Json.createValue(type);
		return gateway.invoke("fin.Clipboard.readHtml", typeJson).thenApply(r -> {
			return r.getResultAsString();
		});
	}

	public static String readHtml(OpenFinGateway gateway, String type) {
		return runSync(readHtmlAsync(gateway, type));
	}

	public static CompletionStage<Void> writeHtmlAsync(OpenFinGateway gateway, String text, String type) {
		JsonObjectBuilder builder = Json.createObjectBuilder().add("data", text);
		if (type != null) {
			builder.add("type", type);
		}
		return gateway.invoke("fin.Clipboard.writeHtml", builder.build()).thenAccept(r -> {
		});
	}

	public static void writeHtml(OpenFinGateway gateway, String text, String type) {
		runSync(writeHtmlAsync(gateway, text, type));
	}

	public static CompletionStage<Void> writeTextAsync(OpenFinGateway gateway, String text, String type) {
		JsonObjectBuilder builder = Json.createObjectBuilder().add("data", text);
		if (type != null) {
			builder.add("type", type);
		}
		return gateway.invoke("fin.Clipboard.writeText", builder.build()).thenAccept(r -> {
		});
	}

	public static void writeText(OpenFinGateway gateway, String text, String type) {
		runSync(writeTextAsync(gateway, text, type));
	}

	public static CompletionStage<String> readTextAsync(OpenFinGateway gateway, String type) {
		JsonValue typeJson = type == null ? null : Json.createValue(type);
		return gateway.invoke("fin.Clipboard.readText", typeJson).thenApply(r -> {
			return r.getResultAsString();
		});
	}

	public static String readText(OpenFinGateway gateway, String type) {
		return runSync(readTextAsync(gateway, type));
	}

	public static CompletionStage<Void> writeRtfAsync(OpenFinGateway gateway, String rtf, String type) {
		JsonObjectBuilder builder = Json.createObjectBuilder().add("data", rtf);
		if (type != null) {
			builder.add("type", type);
		}
		return gateway.invoke("fin.Clipboard.writeRtf", builder.build()).thenAccept(r -> {
		});
	}

	public static void writeRtf(OpenFinGateway gateway, String rtf, String type) {
		runSync(writeRtfAsync(gateway, rtf, type));
	}

	public static CompletionStage<String> readRtfAsync(OpenFinGateway gateway, String type) {
		JsonValue typeJson = type == null ? null : Json.createValue(type);
		return gateway.invoke("fin.Clipboard.readRtf", typeJson).thenApply(r -> {
			return r.getResultAsString();
		});
	}

	public static String readRtf(OpenFinGateway gateway, String type) {
		return runSync(readRtfAsync(gateway, type));
	}

	public static CompletionStage<Void> writeAsync(OpenFinGateway gateway, JsonObject data, String type) {
		JsonObjectBuilder builder = Json.createObjectBuilder().add("data", data);
		if (type != null) {
			builder.add("type", type);
		}
		return gateway.invoke("fin.Clipboard.write", builder.build()).thenAccept(r -> {
		});
	}

	public static void write(OpenFinGateway gateway, JsonObject data, String type) {
		runSync(writeAsync(gateway, data, type));
	}

}
