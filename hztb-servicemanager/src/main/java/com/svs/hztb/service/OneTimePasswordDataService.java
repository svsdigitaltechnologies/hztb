package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.registration.GetOTPResponse;
import com.svs.hztb.api.sm.model.registration.OneTimePasswordRequest;
import com.svs.hztb.api.sm.model.registration.OneTimePasswordResponse;

/**
 * 
 * interface for user data service methods
 * 
 * @author skairamk
 *
 */
public interface OneTimePasswordDataService {

	/**
	 * method for request OTP code
	 * 
	 * @param oneTimePasswordRequest
	 * @return
	 */
	OneTimePasswordResponse requestOTPCode(OneTimePasswordRequest oneTimePasswordRequest);

	/**
	 * method for get OTP code
	 * 
	 * @param oneTimePasswordRequest
	 * @return
	 */
	GetOTPResponse getOTPCode(OneTimePasswordRequest oneTimePasswordRequest);

}
