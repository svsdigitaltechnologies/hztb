package com.svs.hztb.sm.validateotp.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPResponse;
import com.svs.hztb.common.enums.ServiceManagerStatusCode;
import com.svs.hztb.orchestration.exception.BusinessError;
import com.svs.hztb.service.UserDataService;

/**
 * Controller for Validate OTP Service API
 * 
 * @author skairamk
 *
 */
@RestController
@RequestMapping("/user")
public class ValidateOTPController {

	@Autowired
	private UserDataService userDataService;

	/**
	 * 
	 * @param validateOTPRequest
	 * @return ValidateOTPResponse
	 * 
	 *         This method is used to register a new user to the application 1.
	 *         Generates a new OTP code and inserts/updates a record in user
	 *         table. 2. sends the otp code to the mobile phone.
	 */
	@RequestMapping(value = "/validateOTP", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ValidateOTPResponse> validateOTP(@RequestBody @Valid ValidateOTPRequest validateOTPRequest) {
		ValidateOTPResponse validateOTPResponse = userDataService.validateOTP(validateOTPRequest);
		if (!validateOTPResponse.getIsValidateOTPSuccesful()) {
			throw new BusinessError(ServiceManagerStatusCode.INVALID_OTP.getMessage(),
					ServiceManagerStatusCode.INVALID_OTP);
		}
		return buildvalidateOTPResponse(validateOTPResponse);
	}

	private ResponseEntity<ValidateOTPResponse> buildvalidateOTPResponse(ValidateOTPResponse validateOTPResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(validateOTPResponse);
	}
}