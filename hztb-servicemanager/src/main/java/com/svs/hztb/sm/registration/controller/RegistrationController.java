package com.svs.hztb.sm.registration.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svs.hztb.api.sm.model.registration.RegistrationRequest;
import com.svs.hztb.api.sm.model.registration.RegistrationResponse;
import com.svs.hztb.service.UserDataService;

@RestController
@RequestMapping("/user")
public class RegistrationController {

	@Autowired
	private UserDataService userDataService;
	
	/** 
	 * 
	 * @param registrationRequest
	 * @return registrationResponse
	 * 
	 * This method is used to register a new user to the application
	 * 1. Generates a new OTP code and inserts/updates a record in user table.
	 * 2. sends the otp code to the mobile phone.
	 */
	@RequestMapping(value = "/register", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<RegistrationResponse> register(
			@RequestBody @Valid RegistrationRequest registrationRequest) {
		RegistrationResponse registrationResponse = userDataService.register(registrationRequest);
		return buildRegisterResponse(registrationResponse);
	}

	private ResponseEntity<RegistrationResponse> buildRegisterResponse(RegistrationResponse registrationResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(registrationResponse);
	}
/*
	@RequestMapping(value = "/updateUserProfile", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<UserProfileResponse> updateUserProfile(
			@RequestBody @Valid UserProfileRequest userProfileRequest) {
		UserProfileResponse userProfileResponse = new UserProfileResponse();
		userProfileResponse.setPhoneNumber(userProfileRequest.getPhoneNumber());

		byte[] buffer = userProfileRequest.getProfilePic();
		File f = new File("C:\\temp\\" + "ABC123.jpeg");
		try {
			f.createNewFile();
			FileOutputStream fos;
			fos = new FileOutputStream(f);
			fos.write(buffer);
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // This is where I write it to the C Drive

		return buildUserProfileResponse(userProfileResponse);
	}

	private ResponseEntity<UserProfileResponse> buildUserProfileResponse(UserProfileResponse userProfileResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(userProfileResponse);
	}
*/}