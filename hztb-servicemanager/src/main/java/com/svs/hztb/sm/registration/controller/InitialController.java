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

import com.svs.hztb.api.sm.model.initial.InitialCheckRequest;
import com.svs.hztb.api.sm.model.initial.InitialCheckResponse;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.service.InitialService;

/**
 * 
 * Controller class for Registration Service API
 * 
 * @author skairamk
 *
 */
@RestController
@RequestMapping("/app")
public class InitialController {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(InitialController.class);

	@Autowired
	private InitialService initialService;

	/**
	 * 
	 * @param initialRequest
	 * @return initialResponse
	 * 
	 *         This method is used to check the version updates
	 */
	@RequestMapping(value = "/initialcheck", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<InitialCheckResponse> initialCheck(@RequestBody @Valid InitialCheckRequest initialRequest) {
		LOGGER.debug("In InitialController, requestCode method {}", initialRequest);
		InitialCheckResponse initialResponse = initialService.checkVersion(initialRequest);

		return buildInitialResponse(initialResponse);
	}

	private ResponseEntity<InitialCheckResponse> buildInitialResponse(InitialCheckResponse initialResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).contentType(MediaType.APPLICATION_JSON).body(initialResponse);
	}

}