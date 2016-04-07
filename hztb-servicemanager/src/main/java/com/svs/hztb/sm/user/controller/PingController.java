package com.svs.hztb.sm.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svs.hztb.api.sm.model.clickatell.ClickatellData;
import com.svs.hztb.api.sm.model.clickatell.ClickatellMessage;
import com.svs.hztb.api.sm.model.clickatell.ClickatellResponse;
import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.ping.PingResponse;
import com.svs.hztb.service.UserDataService;

@RestController
@RequestMapping("/user")
public class PingController {

	@Autowired
	private UserDataService userDataService;

	/**
	 * 
	 * @param pingRequet
	 * @return pingResponse This method is used to ping the server to check if
	 *         the user is still using the same device or not.
	 */
	@RequestMapping(value = "/ping", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<PingResponse> ping(@RequestBody @Valid PingRequest pingRequest) {
		PingResponse pingResponse = userDataService.ping(pingRequest);
		return buildPingResponse(pingResponse);
	}

	private ResponseEntity<PingResponse> buildPingResponse(PingResponse pingResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(pingResponse);
	}

	@RequestMapping(value = "/jsonGetResponse", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ClickatellResponse> jsonResponse() {
		ClickatellResponse clickatellResponse = new ClickatellResponse();
		ClickatellData clickatellData = new ClickatellData();
		ClickatellMessage clickatellMessage = new ClickatellMessage();
		clickatellMessage.setAccepted("true");
		clickatellMessage.setApiMessageId("4e14462eab36c639ed3a06b1a0d05ed6");
		clickatellMessage.setTo("18479874489");
		List<ClickatellMessage> list = new ArrayList<ClickatellMessage>();
		list.add(clickatellMessage);

		clickatellData.setMessage(list);
		clickatellResponse.setData(clickatellData);
		return ResponseEntity.status(HttpStatus.SC_OK).body(clickatellResponse);
	}

	@RequestMapping(value = "/jsonPostResponse", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ClickatellResponse> jsonPostResponse() {
		ClickatellResponse clickatellResponse = new ClickatellResponse();
		ClickatellData clickatellData = new ClickatellData();
		ClickatellMessage clickatellMessage = new ClickatellMessage();
		clickatellMessage.setAccepted("true");
		clickatellMessage.setApiMessageId("4e14462eab36c639ed3a06b1a0d05ed6");
		clickatellMessage.setTo("18479874489");
		List<ClickatellMessage> list = new ArrayList<ClickatellMessage>();
		list.add(clickatellMessage);
		clickatellData.setMessage(list);
		clickatellResponse.setData(clickatellData);
		return ResponseEntity.status(HttpStatus.SC_OK).body(clickatellResponse);
	}

}