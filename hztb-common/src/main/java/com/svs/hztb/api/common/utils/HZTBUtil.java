package com.svs.hztb.api.common.utils;

import java.time.Duration;
import java.time.Instant;

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

	public static boolean isOtpValidationAllowed(String otpCreationDateTime) {

		boolean isOtpValidationAllowed = false;

		Instant otpInstantTime = Instant.parse(otpCreationDateTime);
		Instant currentInstantTime = Instant.now();

		long differenceInMinutes = Duration.between(otpInstantTime, currentInstantTime).toMinutes();

		if ((differenceInMinutes <= 10)) {
			isOtpValidationAllowed = true;
		}
		return isOtpValidationAllowed;
	}
}
