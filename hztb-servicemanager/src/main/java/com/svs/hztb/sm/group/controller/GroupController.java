package com.svs.hztb.sm.group.controller;

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

import com.svs.hztb.api.sm.model.group.GroupInput;
import com.svs.hztb.api.sm.model.group.GroupOutput;
import com.svs.hztb.api.sm.model.opinion.OpinionOutput;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionInput;
import com.svs.hztb.service.GroupDataService;
import com.svs.hztb.service.OpinionDataService;
@RestController
@RequestMapping("/group")
public class GroupController {
	//Method to list all groups as owner userId: groupId,name,desc{members}
	//List all groups as member: userId:groupId,name,desc
	//Edit the group: groupId,owner: Add/remove members, name change
	//remove the group:groupId,ownerId
	@Autowired
	private GroupDataService groupDataService;


	@RequestMapping(value = "/createGroup", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> createGroup(
			@RequestBody @Valid GroupInput groupInput) {
		ResponseEntity<String> response = null;
		GroupOutput groupOutput = groupDataService.createGroup(groupInput);
		if(groupOutput.isError()) {
			response =  ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(toJson(groupOutput.getErrorOutput()));
		} else {
			response =  ResponseEntity.status(HttpStatus.SC_OK).body(toJson(groupOutput.getStatus()));
		}
		
		return response;
	}
	@RequestMapping(value = "/removeGroup", consumes = { "application/json" }, produces = {
			"application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> removeGroup(
			@RequestBody @Valid GroupInput groupInput) {
		ResponseEntity<String> response = null;
		GroupOutput groupOutput = groupDataService.removeGroup(groupInput);
		if(groupOutput.isError()) {
			response =  ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(toJson(groupOutput.getErrorOutput()));
		} else {
			response =  ResponseEntity.status(HttpStatus.SC_OK).body(toJson(groupOutput.getStatus()));
		}
		
		return response;
	}
	
}
