package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.ping.PingResponse;
import com.svs.hztb.api.sm.model.registration.RegistrationRequest;
import com.svs.hztb.api.sm.model.registration.RegistrationResponse;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPResponse;

public interface UserDataService {

	RegistrationResponse register(RegistrationRequest registrationRequest);
	PingResponse ping(PingRequest pingRequest);
	ValidateOTPResponse validateOTP(ValidateOTPRequest validateOTPRequest);
	UserProfileResponse getUserProfile(UserProfileRequest userProfileRequest);
	UserProfileResponse updateUserProfile(UserProfileRequest userProfileRequest);
}
