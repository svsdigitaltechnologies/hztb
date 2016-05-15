package com.svs.hztb.sm.refresh.controller;

import static com.svs.hztb.sm.common.util.JsonUtil.toJson;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svs.hztb.api.sm.model.refresh.RefreshInput;
import com.svs.hztb.api.sm.model.refresh.RefreshOutput;
import com.svs.hztb.service.RefreshDataService;

@RestController
@RequestMapping("/refresh")
public class RefreshController {
	@Autowired
	private RefreshDataService refreshDataService;

	/**
	 * 
	 * @param RefreshInput
	 * @return
	 */
	@RequestMapping(value = "/opinions", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> getAllOpinions(
			@RequestBody @Valid RefreshInput refreshInput) {
		ResponseEntity<String> response = null;
		RefreshOutput refreshOutput = refreshDataService.getOpinions(refreshInput);
		if(refreshOutput.isError()) {
			response =  ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(toJson(refreshOutput.getErrorOutput()));
		} else {
			response =  ResponseEntity.status(HttpStatus.SC_OK).body(toJson(refreshOutput.getOpinionDataList()));
		}
		
		return response;
	}
	
	/**
	 * 
	 * @param RefreshInput
	 * @return
	 */
	@RequestMapping(value = "/responses", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> getResponses(
			@RequestBody @Valid RefreshInput refreshInput) {
		ResponseEntity<String> response = null;
		RefreshOutput refreshOutput = refreshDataService.getResponses(refreshInput);
		if(refreshOutput.isError()) {
			response =  ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(toJson(refreshOutput.getErrorOutput()));
		} else {
			response =  ResponseEntity.status(HttpStatus.SC_OK).body(toJson(refreshOutput.getOpinionResponseDataList()));
		}
		
		return response;
	}
	}
