package com.svs.hztb.common.logging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.spi.StandardLevel;
import org.slf4j.LoggerFactory;

import com.svs.hztb.common.logging.serialize.LogSerializationFactory;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.util.StringUtil;

/**
 * Implementation class for Loggers
 * 
 * @author skairamkonda
 *
 */
public class DefaultLoggerImpl implements Logger {

	private static final String PERFORMANCE_LOG_NAME = "performance";
	private static final String SECURITY_LOG_NAME = "security";
	private static final String CALL_OUT_LOG_NAME = "callout";
	private static final String STACK_TRACE_LOG_NAME = "stacktraces";
	private static final String FAILURE_LOG_NAME = "failures";
	private static final String GCM_LOG_NAME = "gcm";

	private final org.slf4j.Logger logger;
	private final org.slf4j.Logger performanceLogger;
	private final org.slf4j.Logger securityLogger;
	private final org.slf4j.Logger callOutLogger;
	private final org.slf4j.Logger stackTraceLogger;
	private final org.slf4j.Logger failureLogger;
	private final org.slf4j.Logger gcmLogger;

	private static final String MESSAGE_PREFIX = "[{}]"; // [REQUEST_ID]
	private static final String CALL_OUT_MESSAGE_PREFIX = "CALL OUT : {} " + MESSAGE_PREFIX;
	private static final String CALL_OUT_DOWNSTREAM_MESSAGE_PREFIX = "CALL OUT : {} " + MESSAGE_PREFIX + ": {} : {} ";
	private static final String PERFORMANCE_MESSAGE_PREFIX = MESSAGE_PREFIX
			+ ", METHOD NAME : {}, CALL DURATION : {}ms";
	private static final String FAILURE_MESSAGE_PREFIX = MESSAGE_PREFIX + ": {}";

	/**
	 * DefaultLoggerImpl constructor
	 * 
	 * @param clazz
	 * 
	 */
	public DefaultLoggerImpl(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
		this.performanceLogger = LoggerFactory.getLogger(PERFORMANCE_LOG_NAME);
		this.securityLogger = LoggerFactory.getLogger(SECURITY_LOG_NAME);
		this.callOutLogger = LoggerFactory.getLogger(CALL_OUT_LOG_NAME);
		this.stackTraceLogger = LoggerFactory.getLogger(STACK_TRACE_LOG_NAME);
		this.failureLogger = LoggerFactory.getLogger(FAILURE_LOG_NAME);
		this.gcmLogger = LoggerFactory.getLogger(GCM_LOG_NAME);
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public void logDuration(String method, long duration) {
		if (isInfoEnabled()) {
			logMessage(performanceLogger, StandardLevel.INFO, PERFORMANCE_MESSAGE_PREFIX,
					convertToArray(buildRequestLogData(), method, duration));
		}
	}

	@Override
	public void logPerformance(String message, Object... args) {
		if (isInfoEnabled()) {
			logMessage(performanceLogger, StandardLevel.INFO, MESSAGE_PREFIX,
					convertToArray(buildRequestLogData(), args));
		}
	}

	@Override
	public void logSecurityEvent(String message, Object... args) {
		if (isInfoEnabled()) {
			logMessage(securityLogger, StandardLevel.INFO, MESSAGE_PREFIX, convertToArray(buildRequestLogData(), args));
		}
	}

	@Override
	public void logFailureEvent(String timestamp, String deliverChannel, String messageType, String reasonFailureType,
			String reasonFailureText, String payload) {
		if (isInfoEnabled()) {
			FailureLogMessage failureLogMessage = new FailureLogMessage(timestamp, deliverChannel, messageType,
					reasonFailureType, reasonFailureText, StringUtil.base64Encode(payload));
			logMessage(failureLogger, StandardLevel.INFO, FAILURE_MESSAGE_PREFIX,
					convertToArray(buildRequestLogData(), failureLogMessage));
		}
	}

	@Override
	public void trace(String message, Object... args) {
		if (isTraceEnabled()) {
			logMessage(logger, StandardLevel.TRACE, MESSAGE_PREFIX + message,
					convertToArray(buildRequestLogData(), args));
		}
	}

	@Override
	public void debug(String message, Object... args) {
		if (isDebugEnabled()) {
			logMessage(logger, StandardLevel.DEBUG, MESSAGE_PREFIX + message,
					convertToArray(buildRequestLogData(), args));
		}

	}

	@Override
	public void info(String message, Object... args) {
		if (isInfoEnabled()) {
			logMessage(logger, StandardLevel.INFO, MESSAGE_PREFIX + message,
					convertToArray(buildRequestLogData(), args));
		}

	}

	@Override
	public void warn(String message, Object... args) {
		if (isWarnEnabled()) {
			logMessage(logger, StandardLevel.INFO, MESSAGE_PREFIX + message,
					convertToArray(buildRequestLogData(), args));
		}
	}

	@Override
	public void error(String message, Object... args) {
		logMessage(logger, StandardLevel.ERROR, MESSAGE_PREFIX + message, convertToArray(buildRequestLogData(), args));
	}

	@Override
	public void callOut(String message, Object... args) {
		logMessage(callOutLogger, StandardLevel.ERROR, CALL_OUT_MESSAGE_PREFIX + message,
				convertToArray("APPLICATION ERROR", buildRequestLogData(), args));
	}

	@Override
	public void callOutDownStream(String message, String downstreamSystem, String downstreamMethod, Object... args) {
		logMessage(callOutLogger, StandardLevel.ERROR, CALL_OUT_DOWNSTREAM_MESSAGE_PREFIX + message,
				convertToArray("DOWNSTREAM ERROR", buildRequestLogData(), downstreamSystem, downstreamMethod, args));
	}

	@Override
	public void logStackTrace(String message, Exception e) {
		if (isInfoEnabled()) {
			logMessage(stackTraceLogger, StandardLevel.ERROR, MESSAGE_PREFIX + message,
					convertToArray(buildRequestLogData(), e));
		}
	}

	private void logMessage(org.slf4j.Logger logger, StandardLevel level, String message, Object... args) {
		for (Object object : args) {
			if (object instanceof Exception) {
				printLogMessage(stackTraceLogger, level, "Exception occured: ", object);
			}
		}
		printLogMessage(logger, level, message, wrapValues(args));
	}

	private void printLogMessage(org.slf4j.Logger logger, StandardLevel level, String message, Object... args) {
		switch (level) {
		case TRACE:
			logger.trace(message, args);
			break;
		case DEBUG:
			logger.debug(message, args);
			break;
		case INFO:
			logger.info(message, args);
			break;
		case WARN:
			logger.warn(message, args);
			break;
		case ERROR:
			logger.error(message, args);
			break;
		default:
			break;
		}
	}

	private Object[] buildRequestLogData() {
		Object[] logData = new Object[1];
		logData[0] = PlatformThreadLocalDataFactory.getInstance().getRequestData().getRequestId();
		return logData;
	}

	private static Object[] wrapValues(Object... values) {
		for (int i = 0; i < values.length; i++) {
			Object object = values[i];
			if (object instanceof Exception) {
				values[i] = ((Exception) object).getMessage();
			} else if (!(object instanceof Number || object instanceof String)) {
				values[i] = LogSerializationFactory.getInstance().getJsonString(object);
			}
		}
		return values;
	}

	private Object[] convertToArray(Object... args) {
		List<Object> list = new ArrayList<>();
		for (Object object : args) {
			if (object != null && object.getClass().isArray()) {
				list.addAll(Arrays.asList((Object[]) object));
			} else {
				list.add(object);
			}
		}
		return list.toArray();
	}

	@Override
	public void logGCMActivity(String message, Object... args) {
		logMessage(gcmLogger, StandardLevel.DEBUG, MESSAGE_PREFIX + message,
				convertToArray(buildRequestLogData(), args));

	}
}
