package com.svs.hztb.api.common.utils;

/**
 * This class is a Constants class for regular expressions
 * 
 * @author skairamkonda
 */
public final class HZTBRegularExpressions {
	public static final String ALPHA_NUMERIC_REGEX = "^[a-zA-Z0-9-]*$";
	public static final String ONLY_DIGITS_REGEX = "^[0-9]*";
	public static final String ACCEPT = "application/json";
	public static final String CACHE_CONTROL = "no-store";
	public static final String ACCEPT_LANGUAGE = "en-us|en-US";
	public static final String REQUEST_ID = ALPHA_NUMERIC_REGEX;
	public static final String REQUESTOR_ID = ALPHA_NUMERIC_REGEX;
	public static final String TIMESTAMP = "^\\d{4}-[0-1][0-3]-[0-3]\\d{1}T[0-2]\\d{1}:[0-5]\\d{1}:[0-5]\\d{1}Z$";

	private HZTBRegularExpressions() {
		// do nothing
	}
}
