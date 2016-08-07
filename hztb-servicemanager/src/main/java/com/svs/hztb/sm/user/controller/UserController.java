package com.svs.hztb.sm.user.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svs.hztb.api.sm.model.user.RegisteredProfilesRequest;
import com.svs.hztb.api.sm.model.user.RegisteredProfilesResponse;
import com.svs.hztb.api.sm.model.user.UpdateUserProfileRequest;
import com.svs.hztb.api.sm.model.user.UpdateUserProfileResponse;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;
import com.svs.hztb.service.UserDataService;

/**
 * Controller for User service api.
 * 
 * @author skairamk
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDataService userDataService;

	/**
	 * 
	 * @param userProfileRequest
	 * @return UserProfileResponse This method is used to get the user details
	 *         if the user is already registered
	 */
	@RequestMapping(value = "/getUserProfile", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserProfileResponse> getUserProfile(
			@RequestBody @Valid UserProfileRequest userProfileRequest) {
		UserProfileResponse userProfileResponse = userDataService.getUserProfile(userProfileRequest);
		return buildGetUserProfileResponse(userProfileResponse);
	}

	private ResponseEntity<UserProfileResponse> buildGetUserProfileResponse(UserProfileResponse userProfileResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(userProfileResponse);
	}

	/**
	 * 
	 * @param updateUserProfileRequest
	 * @return UpdateUserProfileResponse This method is used to get the update
	 *         user details if the user is already registered
	 */
	@RequestMapping(value = "/updateUserProfile", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(
			@RequestBody @Valid UpdateUserProfileRequest updateUserProfileRequest) {
		UpdateUserProfileResponse updateUserProfileResponse = userDataService
				.updateUserProfile(updateUserProfileRequest);
		return buildUpdateUserProfileResponse(updateUserProfileResponse);
	}

	private ResponseEntity<UpdateUserProfileResponse> buildUpdateUserProfileResponse(
			UpdateUserProfileResponse updateUserProfileResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(updateUserProfileResponse);
	}

	/**
	 * 
	 * @param registeredProfilesRequest
	 * @return RegisteredProfilesResponse This method is used to get all the
	 *         registered users base on the input list
	 */
	@RequestMapping(value = "/registeredUsers", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<RegisteredProfilesResponse> registeredUsers(
			@RequestBody @Valid RegisteredProfilesRequest registeredProfilesRequest) {
		RegisteredProfilesResponse registeredProfilesResponse = userDataService
				.registeredUsers(registeredProfilesRequest);
		return buildRegisteredUsersResponse(registeredProfilesResponse);
	}

	private ResponseEntity<RegisteredProfilesResponse> buildRegisteredUsersResponse(
			RegisteredProfilesResponse registeredProfilesResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(registeredProfilesResponse);
	}

}