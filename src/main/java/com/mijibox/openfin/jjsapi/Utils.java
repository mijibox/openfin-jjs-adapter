package com.mijibox.openfin.jjsapi;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public interface Utils {
	static <T> T runSync(CompletionStage<T> future) {
		try {
			return future.toCompletableFuture().exceptionally(e -> {
				e.printStackTrace();
				throw new RuntimeException(e);
			}).get();
		}
		catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
