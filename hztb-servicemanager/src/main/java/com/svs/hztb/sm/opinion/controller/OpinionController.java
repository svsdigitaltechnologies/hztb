package com.svs.hztb.sm.opinion.controller;

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

import com.svs.hztb.api.sm.model.opinion.RequestOpinionRequest;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionResponse;
import com.svs.hztb.api.sm.model.opinion.OpinionOutput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseOutput;
import com.svs.hztb.service.OpinionDataService;

@RestController
@RequestMapping("/opinion")
public class OpinionController {

	@Autowired
	private OpinionDataService opinionDataService;

	/**
	 * 
	 * @param requestOpinionRequest
	 * @return
	 */
	@RequestMapping(value = "/requestOpinion", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<RequestOpinionResponse> requestOpinion(
			@RequestBody @Valid RequestOpinionRequest requestOpinionRequest) {

		RequestOpinionResponse requestOpinionResponse = opinionDataService.requestOpinion(requestOpinionRequest);
		return buildUpdateRequestOpinionResponse(requestOpinionResponse);
	}
	
	
	
	@RequestMapping(value = "/opinionResponse", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<OpinionResponseOutput> requestOpinion(
			@RequestBody @Valid OpinionResponseInput OpinionResponseInput) {

		OpinionOutput opinionOutput = opinionDataService.saveResponse(OpinionResponseInput);
		
		return buildRequestOpinionResponse(opinionOutput.getOpinionResponseOutput());
	}

	

	private ResponseEntity<OpinionResponseOutput> buildRequestOpinionResponse(
			OpinionResponseOutput opinionResponseOutput) {
		// TODO Auto-generated method stub
		return null;
	}



	@RequestMapping(value = "/sample", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<RequestOpinionRequest> sample() {

		RequestOpinionRequest requestOpinionRequest = new RequestOpinionRequest();
		requestOpinionRequest.setRequesterUserId(12345);
		requestOpinionRequest.setRequestedGroupId(0001);
		List<Integer> requestedUserIds = new ArrayList<Integer>();
		requestedUserIds.add(00123);
		requestedUserIds.add(00234);
		requestOpinionRequest.setRequestedUserIds(requestedUserIds);
		// requestOpinionRequest.setProduct(product);
		return ResponseEntity.status(HttpStatus.SC_OK).body(requestOpinionRequest);

	}

	private ResponseEntity<RequestOpinionResponse> buildUpdateRequestOpinionResponse(
			RequestOpinionResponse requestOpinionResponse) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(requestOpinionResponse);
	}

}