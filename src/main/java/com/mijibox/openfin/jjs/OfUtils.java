package com.mijibox.openfin.jjs;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface OfUtils {
	static <T> T runSync(CompletionStage<T> future) {
		try {
			return future.toCompletableFuture().exceptionally(e -> {
				e.printStackTrace();
				throw new RuntimeException(e);
			}).get(30, TimeUnit.SECONDS);
		}
		catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}
}
