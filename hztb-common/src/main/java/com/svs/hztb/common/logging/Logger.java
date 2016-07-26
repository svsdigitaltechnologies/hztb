package com.svs.hztb.common.logging;

/**
 * Logger Interface
 * 
 * @author skairamkonda
 *
 */
public interface Logger {

	/**
	 * return whether trace is enabled for underlying logger
	 * 
	 * @return boolean flag
	 */
	boolean isTraceEnabled();

	/**
	 * return whether debug is enabled for underlying logger
	 * 
	 * @return boolean flag
	 */
	boolean isDebugEnabled();

	/**
	 * return whether info is enabled for underlying logger
	 * 
	 * @return boolean flag
	 */
	boolean isInfoEnabled();

	/**
	 * return whether warn is enabled for underlying logger
	 * 
	 * @return boolean flag
	 */
	boolean isWarnEnabled();

	/**
	 * log a message to the performance log
	 * 
	 * @param method
	 * @param duration
	 */
	void logDuration(String method, long duration);

	/**
	 * log a message to the performance log
	 * 
	 * @param method
	 * @param args
	 */
	void logPerformance(String method, Object... args);

	/**
	 * log a message to the security log
	 * 
	 * @param message
	 * @param args
	 */
	void logSecurityEvent(String message, Object... args);

	/**
	 * log a failure message to the failure log
	 * 
	 * @param timestamp
	 * @param deliverChannel
	 * @param messageType
	 * @param reasonFailureType
	 * @param reasonFailureText
	 * @param payload
	 * 
	 */
	void logFailureEvent(String timestamp, String deliverChannel, String messageType, String reasonFailureType,
			String reasonFailureText, String payload);

	/**
	 * standard trace method with parameters to avoid string concatenation
	 * 
	 * @param message
	 * @param args
	 */
	void trace(String message, Object... args);

	/**
	 * standard debug method with parameters to avoid string concatenation
	 * 
	 * @param message
	 * @param args
	 */
	void debug(String message, Object... args);

	/**
	 * standard info method with parameters to avoid string concatenation
	 * 
	 * @param message
	 * @param args
	 */
	void info(String message, Object... args);

	/**
	 * standard warn method with parameters to avoid string concatenation
	 * 
	 * @param message
	 * @param args
	 */
	void warn(String message, Object... args);

	/**
	 * standard error method with parameters to avoid string concatenation
	 * 
	 * @param message
	 * @param args
	 */
	void error(String message, Object... args);

	/**
	 * Generic callout logger method
	 * 
	 * @param message
	 * @param args
	 */
	void callOut(String message, Object... args);

	/**
	 * Generic callout logger method specifically for downstream errors
	 * 
	 * @param message
	 * @param downstreamSystem
	 * @param downstreamMethod
	 * @param args
	 */
	void callOutDownStream(String message, String downstreamSystem, String downstreamMethod, Object... args);

	/**
	 * log an exception stack trace to the stacktraces log file.
	 * 
	 * @param message
	 * @param exception
	 */
	void logStackTrace(String message, Exception exception);

	/**
	 * log gcm activity to gcm log file.
	 * 
	 * @param message
	 * @param args
	 */
	void logGCMActivity(String message, Object... args);

}
