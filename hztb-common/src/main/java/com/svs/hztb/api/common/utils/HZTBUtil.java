package com.svs.hztb.api.common.utils;

import java.time.Duration;
import java.time.Instant;

/**
 * <p>
 * Util class fo generating OTP, OTP allowed.
 * </p>
 * 
 * @author skairamk
 *
 */
public class HZTBUtil {
	static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String getUTCDate() {
		Instant t1 = Instant.now();
		return t1.toString();
	}

	public static Long generateSixDigitNumber() {
		Long aNumber = 0l;
		aNumber = (long) ((Math.random() * 900000) + 100000);
		return aNumber;
	}

	public static boolean isOtpValidationAllowed(String otpCreationDateTime, String invalidOtpCount) {
		
		boolean isOtpValidationAllowed = false;

		Integer count = Integer.parseInt(invalidOtpCount);
		if( count > 4) {
			return isOtpValidationAllowed;
		}

		Instant otpInstantTime = Instant.parse(otpCreationDateTime);
		Instant currentInstantTime = Instant.now();

		long differenceInMinutes = Duration.between(otpInstantTime, currentInstantTime).toMinutes();

		if ((differenceInMinutes <= 10)) {
			isOtpValidationAllowed = true;
			return isOtpValidationAllowed;
		}
		
		return isOtpValidationAllowed;
	}
}
