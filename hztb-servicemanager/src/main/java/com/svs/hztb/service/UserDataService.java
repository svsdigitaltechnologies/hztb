package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.ping.PingResponse;
import com.svs.hztb.api.sm.model.user.RegisteredProfilesRequest;
import com.svs.hztb.api.sm.model.user.RegisteredProfilesResponse;
import com.svs.hztb.api.sm.model.user.UpdateUserProfileRequest;
import com.svs.hztb.api.sm.model.user.UpdateUserProfileResponse;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPResponse;

/**
 * 
 * interface for user data service methods
 * 
 * @author skairamk
 *
 */
public interface UserDataService {

	PingResponse ping(PingRequest pingRequest);

	ValidateOTPResponse validateOTP(ValidateOTPRequest validateOTPRequest);

	UserProfileResponse getUserProfile(UserProfileRequest userProfileRequest);

	RegisteredProfilesResponse registeredUsers(RegisteredProfilesRequest registeredProfilesRequest);

	UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest updateUserProfileRequest);

}
