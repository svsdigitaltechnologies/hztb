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

import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.ping.PingResponse;
import com.svs.hztb.service.UserDataService;

/**
 * 
 * Controller class for Ping Service API
 * 
 * @author skairamk
 *
 */
@RestController
@RequestMapping("/user")
public class PingController {

	@Autowired
	private UserDataService userDataService;

	/**
	 * 
	 * @param pingRequest
	 * @return pingResponse This method is used to ping the server to check if
	 *         the user is still using the same device or not.
	 */
	@RequestMapping(value = "/ping", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PingResponse> ping(@RequestBody @Valid PingRequest pingRequest) {
		PingResponse pingResponse = userDataService.ping(pingRequest);
		return buildPingResponse(pingResponse);
	}

	private ResponseEntity<PingResponse> buildPingResponse(PingResponse pingResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(pingResponse);
	}

}