package com.svs.hztb.api.common.utils;

import java.time.Duration;
import java.time.Instant;

/**
 * Util class for generating OTP, OTP allowed.
 * 
 * @author skairamk
 *
 */
public final class HZTBUtil {
	static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	private HZTBUtil() {
		// do nothing
	}

	public static String getUTCDate() {
		Instant t1 = Instant.now();
		return t1.toString();
	}

	/**
	 * This method is used to generate otp code.
	 * 
	 * @return Long
	 * 
	 */
	public static Long generateSixDigitNumber() {
		Long aNumber;
		aNumber = (long) ((Math.random() * 900000) + 100000);
		return aNumber;
	}

	/**
	 * This method is used to check if otp validation is allowed or not.
	 * 
	 * @param otpCreationDateTime
	 * @param invalidOtpCount
	 * @return Long
	 * 
	 */
	public static boolean isOtpValidationAllowed(String otpCreationDateTime, String invalidOtpCount) {

		boolean isOtpValidationAllowed = false;

		Integer count = Integer.parseInt(invalidOtpCount);
		if (count > 3) {
			return isOtpValidationAllowed;
		}

		Instant otpInstantTime = Instant.parse(otpCreationDateTime);
		Instant currentInstantTime = Instant.now();

		long differenceInMinutes = Duration.between(otpInstantTime, currentInstantTime).toMinutes();

		if (differenceInMinutes <= 10) {
			isOtpValidationAllowed = true;
			return isOtpValidationAllowed;
		}

		return isOtpValidationAllowed;
	}
}
