package com.svs.speakthrough.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svs.speakthrough.api.model.user.UserRequest;
import com.svs.speakthrough.entity.UserEntity;
import com.svs.speakthrough.repository.UserRepository;

@RestController
@RequestMapping("/user")

public class UserServicesController {

	@Autowired
	private UserRepository userRepository;


	@RequestMapping(value = "/getUserDetails", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> getUserDetails(@RequestBody UserRequest userRequest) {
		UserEntity userResponse = userRepository.findUserByUserId(userRequest.getUserName());
		return buildGetUserDetailsResponse(userResponse);
	}

	private ResponseEntity<?> buildGetUserDetailsResponse(UserEntity userResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(userResponse);
	}

}