package com.svs.hztb.service.impl;

import static com.svs.hztb.sm.common.util.DateUtils.getDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.hztb.api.sm.model.product.Product;
import com.svs.hztb.api.sm.model.refresh.GivenPendingData;
import com.svs.hztb.api.sm.model.refresh.OpinionCountData;
import com.svs.hztb.api.sm.model.refresh.OpinionData;
import com.svs.hztb.api.sm.model.refresh.OpinionResponseData;
import com.svs.hztb.api.sm.model.refresh.RefreshInput;
import com.svs.hztb.api.sm.model.refresh.RefreshOutput;
import com.svs.hztb.api.sm.model.refresh.ResponseGivenPendingInfo;
import com.svs.hztb.converters.OpinionEntitityToDataConverter;
import com.svs.hztb.converters.OpinionResponseEntityToDataConverter;
import com.svs.hztb.converters.ProductEntityToDataConverter;
import com.svs.hztb.entity.OpinionEntity;
import com.svs.hztb.entity.OpinionResponseEntity;
import com.svs.hztb.entity.ProductEntity;
import com.svs.hztb.entity.UserGroupEntity;
import com.svs.hztb.repository.OpinionRepository;
import com.svs.hztb.repository.OpinionResponseRepository;
import com.svs.hztb.repository.ProductRepository;
import com.svs.hztb.repository.UserGroupEntityRepository;
import com.svs.hztb.service.RefreshDataService;
import com.svs.hztb.sm.common.util.FunctionUtils;


@Service
public class RefreshDataServiceImpl implements RefreshDataService {
	@Autowired
	OpinionResponseRepository opinionResponseRepository;
	
	@Autowired
	OpinionRepository opinionRepository;
	
	@Autowired
	UserGroupEntityRepository userGroupRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	
//	@Autowired
//	NativeRepository nativeRepository;
	
	
	
	
	
	@Override
	public RefreshOutput getResponsesByUser(RefreshInput refreshInput) {
		List<OpinionResponseEntity> opinionResponseEntities = null;
		RefreshOutput refreshOutput = new RefreshOutput();
		
		if(null == refreshInput.getLastUpdatedTime()) {
			opinionResponseEntities = opinionResponseRepository.findByResponderUserId(refreshInput.getUserId());
		} else {
			opinionResponseEntities = opinionResponseRepository.findByResponderUserIdLastUpdatedTime(refreshInput.getUserId(), getDate(refreshInput.getLastUpdatedTime()));
		}
		List<OpinionResponseData> opinionResponseDataList =  FunctionUtils.convert(opinionResponseEntities, new OpinionResponseEntityToDataConverter());
		
		refreshOutput.getOpinionResponseInfo().setOpinionResponseDataList(opinionResponseDataList);
		return refreshOutput;
	}
	@Override
	public RefreshOutput getResponsesByOpinion(RefreshInput refreshInput) {
		List<OpinionResponseEntity> opinionResponseEntities = null;
		RefreshOutput refreshOutput = new RefreshOutput();
		
		if(null == refreshInput.getLastUpdatedTime()) {
			opinionResponseEntities = opinionResponseRepository.findByOpinionId(refreshInput.getOpinionId());
		} else {
			opinionResponseEntities = opinionResponseRepository.findByOpinionIdLastUpdatedTime
					(refreshInput.getOpinionId(), getDate(refreshInput.getLastUpdatedTime()));
		}
		List<OpinionResponseData> opinionResponseDataList =  FunctionUtils.convert(opinionResponseEntities, new OpinionResponseEntityToDataConverter());
		
		refreshOutput.getOpinionResponseInfo().setOpinionResponseDataList(opinionResponseDataList);
		
		//Get list of unresponsive users
		
		int groupId = opinionRepository.findByOpinionId(refreshInput.getOpinionId()).getGroupId();
		List<UserGroupEntity> userGroupList = userGroupRepository.findByGroupId(groupId);
		List<Integer> groupUsers = userGroupList.stream().map(ug->ug.getId().getUserId()).collect(Collectors.toList());
		List<Integer> respondedUsers = opinionResponseDataList.stream().map(or->or.getResponderUserId()).collect(Collectors.toList());
		groupUsers.removeAll(respondedUsers);
		refreshOutput.getOpinionResponseInfo().setUnResponsiveUsers(groupUsers);
		
		return refreshOutput;
	}
	@Override
	public RefreshOutput getOpinions(RefreshInput refreshInput) {
		
		List<OpinionEntity> opinionEntities = null;
		RefreshOutput refreshOutput = new RefreshOutput();
		
		if(null == refreshInput.getLastUpdatedTime()) {
			 opinionEntities = opinionRepository.findByUserId(refreshInput.getUserId());
		} else {
			opinionEntities = opinionRepository.findByUserIdLastUpdatedTime(refreshInput.getUserId(), getDate(refreshInput.getLastUpdatedTime()));
		}
		List<OpinionData> opinionDataList =  FunctionUtils.convert(opinionEntities, new OpinionEntitityToDataConverter());
		for(OpinionData opinionData : opinionDataList) {
			List<OpinionResponseEntity> responseEntities =  opinionResponseRepository.findByOpinionId(opinionData.getOpinionId());
			Map<String, List<OpinionResponseEntity>> responseEntitiesMap = 
					responseEntities.stream().collect(Collectors.groupingBy(OpinionResponseEntity::getOpinionResponseType));
			for(String key : responseEntitiesMap.keySet()) {
				opinionData.getResponseCounts().put(key, responseEntitiesMap.get(key).size());
			}
		}
		refreshOutput.setOpinionDataList(opinionDataList);
		return refreshOutput;
	}
	@Override
	public RefreshOutput getAllResponsesCounts(RefreshInput refreshInput) {
		//Get all opinions of the users//
		RefreshOutput refreshOutput = new RefreshOutput();
		
		List<OpinionEntity> opinionEntities = opinionRepository.findByUserId(refreshInput.getUserId());
		Map<Integer, OpinionCountData> givenCounts = new HashMap<Integer, OpinionCountData>();
		
		for(OpinionEntity opinionEntity : opinionEntities) {
			//Get groups and groupMembers
			
			//Get the responded count
			List<OpinionResponseEntity> opinionResponseEntities = opinionResponseRepository.findByOpinionId(opinionEntity.getOpinionId());
			List<OpinionResponseData> opinionResponseDataList =  FunctionUtils.convert(opinionResponseEntities, new OpinionResponseEntityToDataConverter());
			
			int groupId = opinionRepository.findByOpinionId(opinionEntity.getOpinionId()).getGroupId();
			List<UserGroupEntity> userGroupList = userGroupRepository.findByGroupId(groupId);
			List<Integer> groupUsers = userGroupList.stream().map(ug->ug.getId().getUserId()).collect(Collectors.toList());
			List<Integer> respondedUsers = opinionResponseDataList.stream().map(or->or.getResponderUserId()).collect(Collectors.toList());
			groupUsers.removeAll(respondedUsers);
			
			for(Integer respondedUser : respondedUsers) {
				OpinionCountData opinionCountData = givenCounts.get(respondedUser);
				if(null == opinionCountData) {					
					opinionCountData = new OpinionCountData();
					opinionCountData.setUserId(respondedUser);
					
				}
				opinionCountData.setGivenCount(opinionCountData.getGivenCount() + 1);
				givenCounts.put(respondedUser, opinionCountData);
			}
			
			for(Integer groupUser : groupUsers) {
				OpinionCountData opinionCountData = givenCounts.get(groupUser);
				if(null == opinionCountData) {					
					opinionCountData = new OpinionCountData();
					opinionCountData.setUserId(groupUser);
					
				}
				opinionCountData.setPendingCount(opinionCountData.getPendingCount() + 1);
				givenCounts.put(groupUser, opinionCountData);
			}
			

		}
		//Get 
		refreshOutput.setOpinionCountsList(givenCounts.values()); 
		
		
		
		return refreshOutput;
	}
	
	
	
	@Override
	public RefreshOutput getOpinionsGivenPending(RefreshInput refreshInput) {
		//Get all opinions of the users//
		RefreshOutput refreshOutput = new RefreshOutput();
		
		List<OpinionEntity> opinionEntities = opinionRepository.findByUserId(refreshInput.getUserId());
		Map<Integer, OpinionCountData> givenCounts = new HashMap<Integer, OpinionCountData>();
		ResponseGivenPendingInfo responseGivenPendingInfo = new ResponseGivenPendingInfo();

		
		for(OpinionEntity opinionEntity : opinionEntities) {
			//Get groups and groupMembers
			
			//Get the responded count
			int groupId = opinionRepository.findByOpinionId(opinionEntity.getOpinionId()).getGroupId();
			List<UserGroupEntity> userGroupList = userGroupRepository.findByGroupId(groupId);
			List<Integer> groupUsers = userGroupList.stream().map(ug->ug.getId().getUserId()).collect(Collectors.toList());
			if(!groupUsers.contains(refreshInput.getResponderUserId())) {
				continue;
			}
			
			List<OpinionResponseEntity> opinionResponseEntities = opinionResponseRepository.findByOpinionId(opinionEntity.getOpinionId()).stream().
					filter(or -> or.getResponderUserId() ==refreshInput.getResponderUserId()).collect(Collectors.toList());
			
			ProductEntity productEntity = productRepository.findOne(opinionEntity.getProduct());
			Product product =  new ProductEntityToDataConverter().apply(productEntity);
			GivenPendingData givenPendingData = new GivenPendingData();
			givenPendingData.setOpinionId(opinionEntity.getOpinionId());
			givenPendingData.setProduct(product);
			
			if(null == opinionResponseEntities || opinionResponseEntities.isEmpty()) {
				
				responseGivenPendingInfo.getPendingData().add(givenPendingData);
				//Add it to pending
				
			} else {
				OpinionResponseEntity opinionResponseEntity = opinionResponseEntities.get(0);
				givenPendingData.setResponseText(opinionResponseEntity.getResponseTxt());
				givenPendingData.setResponseType(opinionResponseEntity.getOpinionResponseType());
				responseGivenPendingInfo.getGivenData().add(givenPendingData);
				//Add it to Given
				//opinionResponseEntities
			}
			
		}
		//Get 
		refreshOutput.setResponseGivenPendingInfo(responseGivenPendingInfo); 
		
		
		
		return refreshOutput;
	}
}