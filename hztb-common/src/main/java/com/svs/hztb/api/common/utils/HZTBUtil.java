package com.svs.hztb.api.common.utils;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

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
	public static boolean isOtpValidationAllowed(String otpCreationDateTime, Integer invalidOtpCount) {

		boolean isOtpValidationAllowed = false;

		if (invalidOtpCount > 3) {
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

	/**
	 * This method is used to give the difference between current time and
	 * request time.
	 * 
	 * @param requestTime
	 * @return Long
	 * 
	 */
	public static Long differenceInSeconds(String requestTime) {
		Instant otpInstantTime = Instant.parse(requestTime);
		Instant currentInstantTime = Instant.now();
		return Duration.between(otpInstantTime, currentInstantTime).getSeconds();
	}

	public static String generatePw() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String pw = RandomStringUtils.random(15, 0, 0, false, false, characters.toCharArray(), new SecureRandom());
		return Base64.encodeBase64String(pw.getBytes());
	}
}