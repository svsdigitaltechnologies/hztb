package com.svs.hztb.common.logging;

/**
 * Logger Factory class
 * 
 * @author skairamkonda
 *
 */
public enum LoggerFactory {
	INSTANCE;

	/**
	 * returns a logger
	 * 
	 * @param clazz
	 * @return Logger
	 */
	public Logger getLogger(Class<?> clazz) {
		return new DefaultLoggerImpl(clazz);
	}
}
