package com.svs.hztb.sm.opinion.controller;

import static com.svs.hztb.sm.common.util.JsonUtil.toJson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.svs.hztb.api.sm.model.opinion.OpinionOutput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionInput;
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
	public @ResponseBody ResponseEntity<String> requestOpinion(
			@RequestBody @Valid RequestOpinionInput requestOpinionInput) {
		ResponseEntity<String> response = null;
		OpinionOutput opinionOutput = opinionDataService.requestOpinion(requestOpinionInput);
		if(opinionOutput.isError()) {
			response =  ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(toJson(opinionOutput.getErrorOutput()));
		} else {
			response =  ResponseEntity.status(HttpStatus.SC_OK).body(toJson(opinionOutput.getRequestOpinionOutput()));
		}
		
		return response;
	}
	
	
	
	@RequestMapping(value = "/opinionResponse", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> opinionResponse(
			@RequestBody @Valid OpinionResponseInput OpinionResponseInput) {

		OpinionOutput opinionOutput = opinionDataService.saveResponse(OpinionResponseInput);
		ResponseEntity<String> response = null;
		if(opinionOutput.isError()) {
			response =  ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(toJson(opinionOutput.getErrorOutput()));
		} else {
			response =  ResponseEntity.status(HttpStatus.SC_OK).body(toJson(opinionOutput.getOpinionResponseOutput()));
		}
		return response;
	}


	@RequestMapping(value = "/sample", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<RequestOpinionInput> sample() {

		RequestOpinionInput requestOpinionRequest = new RequestOpinionInput();
		requestOpinionRequest.setRequesterUserId(12345);
		//requestOpinionRequest.setRequestedGroupId(0001);
		List<Integer> requestedUserIds = new ArrayList<Integer>();
		requestedUserIds.add(00123);
		requestedUserIds.add(00234);
		requestOpinionRequest.setRequestedUserIds(requestedUserIds);
		// requestOpinionRequest.setProduct(product);
		requestOpinionRequest.setDate(new Date());
		return ResponseEntity.status(HttpStatus.SC_OK).body(requestOpinionRequest);

	}

//	private ResponseEntity<RequestOpinionOutput> buildUpdateRequestOpinionResponse(
//			RequestOpinionOutput requestOpinionResponse) {
//		return ResponseEntity.status(HttpStatus.SC_OK).body(requestOpinionResponse);
//	}

}