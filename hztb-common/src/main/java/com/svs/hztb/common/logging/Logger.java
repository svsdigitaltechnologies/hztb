package com.svs.hztb.common.logging;

public interface Logger {
	boolean isTraceEnabled();

	boolean isDebugEnabled();

	boolean isInfoEnabled();

	boolean isWarnEnabled();

	void logDuration(String method, long duration);

	void logPerformance(String message, Object... args);

	void logSecurityEvent(String message, Object... args);

	void logFailureEvent(String timestamp, String deliverChannel, String messageType, String reasonFailureType,
			String reasonFailureText, String payload);

	void trace(String message, Object... args);

	void debug(String message, Object... args);

	void info(String message, Object... args);

	void warn(String message, Object... args);

	void error(String message, Object... args);

	void callOut(String message, Object... args);

	void callOutDownStream(String message, String downstreamSystem, String downstreamMethod, Object... args);

	void logStackTrace(String message, Exception e);
	
	void logGCMActivity(String message, Object... args);

}
