package com.svs.hztb.sm.opinion.controller;

import static com.svs.hztb.sm.common.util.JsonUtil.toJson;

import java.util.ArrayList;
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
import com.svs.hztb.api.sm.model.opinion.OpinionRequest;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
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
	@ResponseBody
	public ResponseEntity<String> requestOpinion(@RequestBody @Valid OpinionRequest requestOpinionInput) {
		ResponseEntity<String> response;
		OpinionOutput opinionOutput = opinionDataService.requestOpinion(requestOpinionInput);
		if (opinionOutput.isError()) {
			response = ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body(toJson(opinionOutput.getErrorOutput()));
		} else {
			response = ResponseEntity.status(HttpStatus.SC_OK).body(toJson(opinionOutput.getRequestOpinionOutput()));
		}
		return response;
	}

	@RequestMapping(value = "/opinionResponse", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> opinionResponse(@RequestBody @Valid OpinionResponseInput opinionResponseInput) {
		OpinionOutput opinionOutput = opinionDataService.saveResponse(opinionResponseInput);
		ResponseEntity<String> response;
		if (opinionOutput.isError()) {
			response = ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body(toJson(opinionOutput.getErrorOutput()));
		} else {
			response = ResponseEntity.status(HttpStatus.SC_OK).body(toJson(opinionOutput.getOpinionResponseOutput()));
		}
		return response;
	}

	@RequestMapping(value = "/sample", produces = { "application/json" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<OpinionRequest> sample() {

		OpinionRequest requestOpinionRequest = new OpinionRequest();
		requestOpinionRequest.setRequesterUserId(12345L);
		List<Long> requestedUserIds = new ArrayList<>();
		requestedUserIds.add(00123L);
		requestedUserIds.add(00234L);
		requestOpinionRequest.setRequestedUserIds(requestedUserIds);
		requestOpinionRequest.setDate(new Date());
		return ResponseEntity.status(HttpStatus.SC_OK).body(requestOpinionRequest);

	}

}