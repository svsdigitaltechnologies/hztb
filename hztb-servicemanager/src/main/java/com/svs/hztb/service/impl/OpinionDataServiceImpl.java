package com.svs.hztb.service.impl;

import static com.svs.hztb.sm.common.model.ServiceManagerConstants.DEFAULT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.hztb.api.sm.model.opinion.OpinionOutput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseOutput;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionInput;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionOutput;
import com.svs.hztb.api.sm.model.product.Product;
import com.svs.hztb.entity.GroupEntity;
import com.svs.hztb.entity.OpinionEntity;
import com.svs.hztb.entity.OpinionResponseEntity;
import com.svs.hztb.entity.ProductEntity;
import com.svs.hztb.entity.UserGroupEntity;
import com.svs.hztb.entity.UserGroupPK;
import com.svs.hztb.repository.GroupRepository;
import com.svs.hztb.repository.OpinionRepository;
import com.svs.hztb.repository.OpinionResponseRepository;
import com.svs.hztb.repository.UserGroupEntityRepository;
import com.svs.hztb.service.OpinionDataService;

@Service
public class OpinionDataServiceImpl implements OpinionDataService {
	@Autowired
	OpinionRepository opinionRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	UserGroupEntityRepository userGroupEntityRepository;
	
	@Autowired
	OpinionResponseRepository opinionResponseRepository;
	

	@Override
	public OpinionOutput requestOpinion(RequestOpinionInput requestOpinionRequest) {
		// validations

		OpinionEntity opinionEntity = createOpinionEntity(requestOpinionRequest);
	
		if(0 == requestOpinionRequest.getRequestedGroupId()) {
			//Create a group and Save requested userIds
			GroupEntity group= createGroup(requestOpinionRequest);
			groupRepository.saveAndFlush(group);
			opinionEntity.setGroupId(group.getGroupId());
			
			List<UserGroupEntity> userGroupList = createUserGroup(requestOpinionRequest, group);
			userGroupEntityRepository.save(userGroupList);
		} 
		
		opinionRepository.save(opinionEntity);
		
		return buildRequestOpinionOutput();
	}
	
	private OpinionOutput buildRequestOpinionOutput() {
		RequestOpinionOutput requestOpinionOutput = new RequestOpinionOutput();
		OpinionOutput opinionOutput = new OpinionOutput();
		opinionOutput.setError(false);
		opinionOutput.setRequestOpinionOutput(requestOpinionOutput);
		return opinionOutput;
		
	}

	@Override
	public OpinionOutput saveResponse(OpinionResponseInput opinionResponseInput) {
		OpinionResponseEntity opinionResponseEntity = createOpinionResponseEntity(opinionResponseInput);
		opinionResponseRepository.save(opinionResponseEntity);
		
		return buildOpinionOutputForSaveResponse();
		
		
	
	}
	
	private OpinionOutput buildOpinionOutputForSaveResponse() {
		OpinionResponseOutput opinionResponseOutput = new OpinionResponseOutput();
		OpinionOutput opinionOutput = new OpinionOutput();
		opinionOutput.setError(false);
		opinionOutput.setOpinionResponseOutput(opinionResponseOutput);
		return opinionOutput;
	}

	private OpinionResponseEntity createOpinionResponseEntity(OpinionResponseInput opinionResponseInput) {
		OpinionResponseEntity opinionResponseEntity = new OpinionResponseEntity();
		opinionResponseEntity.setOpinionId(opinionResponseInput.getOpinionReqId());
		opinionResponseEntity.setOpinionResponseType(opinionResponseInput.getResponseCode());
		opinionResponseEntity.setResponderUserId(opinionResponseInput.getUserId());
		opinionResponseEntity.setResponseTxt(opinionResponseInput.getResponseTxt());
		return opinionResponseEntity;
	}

	private List<UserGroupEntity> createUserGroup(RequestOpinionInput requestOpinionRequest, GroupEntity group) {
		List<UserGroupEntity> userGroupEntityList =  new ArrayList<UserGroupEntity>();
		for(int userId: requestOpinionRequest.getRequestedUserIds()){
			UserGroupEntity userGroupEntity = new UserGroupEntity();
			UserGroupPK userGroupPK = new UserGroupPK();
			userGroupPK.setGroupId(group.getGroupId());
			userGroupPK.setUserId(userId);
			userGroupEntity.setId(userGroupPK);
			userGroupEntityList.add(userGroupEntity);
			
		}
		return userGroupEntityList;
		
	}

	private GroupEntity createGroup(RequestOpinionInput requestOpinionRequest) {
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setGroupDesc(DEFAULT);
		groupEntity.setGroupName(DEFAULT);
		groupEntity.setGroupOwner(requestOpinionRequest.getRequesterUserId());
		return groupEntity;
	}

	private OpinionEntity createOpinionEntity(RequestOpinionInput requestOpinionRequest) {
		OpinionEntity opinionEntity = new OpinionEntity();
		opinionEntity.setUserId(requestOpinionRequest.getRequesterUserId());
		opinionEntity.setGroupId(requestOpinionRequest.getRequestedGroupId());
		opinionEntity.setProductUrl(requestOpinionRequest.getProductUrl());
		
		Product product = requestOpinionRequest.getProduct();
		
		if(null != product) {
			ProductEntity productEntity = new ProductEntity();
			productEntity.setName(product.getName());
			productEntity.setLongDesc(product.getLongDesc());
			productEntity.setShortDesc(product.getShortDesc());
			productEntity.setPrice(product.getPrice());
			
			
			opinionEntity.setProduct(productEntity);
			
		}
		
		
		return opinionEntity;
	}

	

}
