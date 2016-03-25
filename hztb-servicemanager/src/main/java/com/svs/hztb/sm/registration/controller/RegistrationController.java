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

	@RequestMapping(value = "/register", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
		RegistrationResponse registrationResponse = userDataService.register(registrationRequest);
		return buildRegisterResponse(registrationResponse);
	}

	private ResponseEntity<RegistrationResponse> buildRegisterResponse(RegistrationResponse registrationResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(registrationResponse);
	}

}