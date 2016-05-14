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
import com.svs.hztb.repository.ProductRepository;
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
	
	@Autowired
	ProductRepository productRepository;
	

	@Override
	public OpinionOutput requestOpinion(RequestOpinionInput requestOpinionRequest) {
		// validations
		
		
		OpinionEntity opinionEntity = createOpinionEntity(requestOpinionRequest);
	
		if(requestOpinionRequest.getRequestedGroupIds() == null ||
				requestOpinionRequest.getRequestedGroupIds().size() <= 0) {
			//Create a group and Save requested userIds
			GroupEntity group= createGroup(requestOpinionRequest);
			groupRepository.saveAndFlush(group);
			opinionEntity.setGroupId(group.getGroupId());
			
			List<UserGroupEntity> userGroupList = createUserGroup(requestOpinionRequest, group);
			userGroupEntityRepository.save(userGroupList);
		} else if (requestOpinionRequest.getRequestedGroupIds().size() == 1){
			
			opinionEntity.setGroupId(requestOpinionRequest.getRequestedGroupIds().get(0));
		} else{
			//Create a group and Save requested userIds
			GroupEntity group= createGroup(requestOpinionRequest);
			groupRepository.saveAndFlush(group);
			opinionEntity.setGroupId(group.getGroupId());
			
			List<UserGroupEntity> userGroupList = combineGroups(requestOpinionRequest, group);
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
		for(int userId: requestOpinionRequest.getRequestedUserIds()) {
			UserGroupEntity userGroupEntity = new UserGroupEntity();
			UserGroupPK userGroupPK = new UserGroupPK();
			userGroupPK.setGroupId(group.getGroupId());
			userGroupPK.setUserId(userId);
			userGroupEntity.setId(userGroupPK);
			userGroupEntityList.add(userGroupEntity);
			
		}
		return userGroupEntityList;
		
	}
	/**
	 * This method combines two or three groups into a single group
	 * @param requestOpinionRequest
	 * @param group
	 * @return List<UserGroupEntity>
	 */
	private List<UserGroupEntity> combineGroups(RequestOpinionInput requestOpinionRequest, GroupEntity group) {
		List<UserGroupEntity> userGroupEntityList =  new ArrayList<UserGroupEntity>();
		for(int groupId: requestOpinionRequest.getRequestedGroupIds()) {
			List<UserGroupEntity> userGroupEntities =  userGroupEntityRepository.findByGroupId(groupId);
			for(UserGroupEntity userGroup: userGroupEntities) {
				UserGroupEntity userGroupEntity = new UserGroupEntity();
				UserGroupPK userGroupPK = new UserGroupPK();
				userGroupPK.setGroupId(group.getGroupId());
				userGroupPK.setUserId(userGroup.getId().getUserId());
				userGroupEntity.setId(userGroupPK);
				userGroupEntityList.add(userGroupEntity);
			}
			
		}
		return userGroupEntityList;
		
	}

	private GroupEntity createGroup(RequestOpinionInput requestOpinionRequest) {
		GroupEntity groupEntity = new GroupEntity();
		String groupName = requestOpinionRequest.getGroupName() != null?DEFAULT:requestOpinionRequest.getGroupName(); 
		//groupEntity.setGroupDesc(DEFAULT);
		groupEntity.setGroupName(groupName);
		groupEntity.setGroupOwner(requestOpinionRequest.getRequesterUserId());
		return groupEntity;
	}

	private OpinionEntity createOpinionEntity(RequestOpinionInput requestOpinionRequest) {
		OpinionEntity opinionEntity = new OpinionEntity();
		opinionEntity.setUserId(requestOpinionRequest.getRequesterUserId());
	 
		opinionEntity.setGroupId(requestOpinionRequest.getRequestedGroupIds().get(0));
		opinionEntity.setProductUrl(requestOpinionRequest.getProductUrl());
		
		Product product = requestOpinionRequest.getProduct();
		
		if(null != product) {
			
			//If product is not saved save it
			if(!productRepository.exists(product.getName()))
			{
				ProductEntity productEntity = new ProductEntity();
				productEntity.setName(product.getName());
				productEntity.setLongDesc(product.getLongDesc());
				productEntity.setShortDesc(product.getShortDesc());
				productEntity.setPrice(product.getPrice());
				productRepository.save(productEntity);
			}
			
			opinionEntity.setProduct(product.getName());
			
		}
		
		
		return opinionEntity;
	}

	

}
