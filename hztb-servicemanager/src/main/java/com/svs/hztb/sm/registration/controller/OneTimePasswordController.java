package com.svs.hztb.sm.registration.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svs.hztb.api.sm.model.registration.OneTimePasswordRequest;
import com.svs.hztb.api.sm.model.registration.OneTimePasswordResponse;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.service.OneTimePasswordDataService;

/**
 * 
 * Controller class for Registration Service API
 * 
 * @author skairamk
 *
 */
@RestController
@RequestMapping("/user")
public class OneTimePasswordController {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(OneTimePasswordController.class);

	@Autowired
	private OneTimePasswordDataService oneTimePasswordDataService;

	/**
	 * 
	 * @param oneTimePasswordRequest
	 * @return oneTimePasswordResponse
	 * 
	 *         This method is used to request code for a new user to the
	 *         application 1. Generates a new OTP code and inserts/updates a
	 *         record in code_register table. 2. sends the otp code to the
	 *         mobile phone.
	 */
	@RequestMapping(value = "/requestcode", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<OneTimePasswordResponse> requestCode(
			@RequestBody @Valid OneTimePasswordRequest oneTimePasswordRequest) {
		LOGGER.debug("In OneTimePasswordController, requestCode method {}", oneTimePasswordRequest);
		OneTimePasswordResponse oneTimePasswordResponse = oneTimePasswordDataService
				.requestOTPCode(oneTimePasswordRequest);
		return buildOneTimePasswordResponse(oneTimePasswordResponse);
	}

	private ResponseEntity<OneTimePasswordResponse> buildOneTimePasswordResponse(
			OneTimePasswordResponse oneTimePasswordResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).contentType(MediaType.APPLICATION_JSON)
				.body(oneTimePasswordResponse);
	}

}