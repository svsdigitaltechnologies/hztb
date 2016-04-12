package com.svs.hztb.common.logging;

public enum LoggerFactory {
	INSTANCE;
	
	public Logger getLogger(Class<?> clazz) {
		return new DefaultLoggerImpl(clazz);
	}
}
