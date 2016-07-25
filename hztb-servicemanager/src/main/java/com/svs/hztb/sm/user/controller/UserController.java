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

import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileRequests;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;
import com.svs.hztb.api.sm.model.user.UserProfileResponses;
import com.svs.hztb.service.UserDataService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDataService userDataService;

	/**
	 * 
	 * @param userProfileRequest
	 * @return UserProfileResponse This method is used to ping the server to
	 *         check if the user is still using the same device or not.
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

	@RequestMapping(value = "/updateUserProfile", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserProfileResponse> updateUserProfile(
			@RequestBody @Valid UserProfileRequest userProfileRequest) {
		UserProfileResponse userProfileResponse = userDataService.updateUserProfile(userProfileRequest);
		return buildUpdateUserProfileResponse(userProfileResponse);
	}

	private ResponseEntity<UserProfileResponse> buildUpdateUserProfileResponse(
			UserProfileResponse userProfileResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(userProfileResponse);
	}

	@RequestMapping(value = "/registeredUsers", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserProfileResponses> registeredUsers(
			@RequestBody @Valid UserProfileRequests userProfileRequests) {
		UserProfileResponses userProfileResponses = userDataService.registeredUsers(userProfileRequests);
		return buildRegisteredUsersResponse(userProfileResponses);
	}

	private ResponseEntity<UserProfileResponses> buildRegisteredUsersResponse(
			UserProfileResponses userProfileResponses) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(userProfileResponses);
	}

}