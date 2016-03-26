package com.inf749.secureLogin.helpers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeoutForMethodsHelper {

	public static void runWithTimeout(final Runnable runnable, long timeout, TimeUnit timeUnit) throws Exception {
		runWithTimeout(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				runnable.run();
				return null;
			}
		}, timeout, timeUnit);
	}

	public static <T> T runWithTimeout(Callable<T> callable, long timeout, TimeUnit timeUnit) throws Exception {
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		final Future<T> future = executor.submit(callable);
		executor.shutdown();
		try {
			return future.get(timeout, timeUnit);
		} catch (TimeoutException e) {
			future.cancel(true);
			throw e;
		} catch (ExecutionException e) {
			Throwable t = e.getCause();
			if (t instanceof Error) {
				throw (Error) t;
			} else if (t instanceof Exception) {
				throw e;
			} else {
				throw new IllegalStateException(t);
			}
		}
	}
}
