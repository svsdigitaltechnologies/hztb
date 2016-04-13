package com.svs.hztb.common.util;

import java.util.concurrent.TimeUnit;

import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;

public class PerformanceTimer {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(PerformanceTimer.class);
	
	private final long startNanos;
	
	public PerformanceTimer() {
		this.startNanos = System.nanoTime();
	}
	
	public long logPerformance(String actionName) {
		long durationMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
		LOGGER.logDuration(actionName, durationMillis);
		return durationMillis;
	}
}
