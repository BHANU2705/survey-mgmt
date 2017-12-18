package com.bps.service.core;

public final class ProcessContextPool {
	private static ThreadLocal<ProcessContext> processContext = new ThreadLocal<ProcessContext>();

	private ProcessContextPool() {
	}

	public static ProcessContext get() {
		return processContext.get();
	}

	public static void set(final ProcessContext context) {
		processContext.set(context);
	}

	public static void remove() {
		processContext.remove();
	}
}
