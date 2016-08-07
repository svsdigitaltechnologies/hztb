package com.svs.hztb.service.impl;

import static com.svs.hztb.sm.common.model.ServiceManagerConstants.DEFAULT_GROUP_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.hztb.api.sm.model.opinion.OpinionOutput;
import com.svs.hztb.api.sm.model.opinion.OpinionRequest;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseOutput;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionOutput;
import com.svs.hztb.api.sm.model.product.Product;
import com.svs.hztb.aws.client.AWSS3ClientProcessor;
import com.svs.hztb.common.enums.NotificationType;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
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
import com.svs.hztb.service.GCMService;
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

	@Autowired
	GCMService gcmService;

	@Autowired
	AWSS3ClientProcessor awsClientProcessor;

	@Override
	public OpinionOutput requestOpinion(OpinionRequest requestOpinionRequest) {
		// validations

		OpinionEntity opinionEntity = createOpinionEntity(requestOpinionRequest);

		if (requestOpinionRequest.getRequestedGroupIds() == null
				|| requestOpinionRequest.getRequestedGroupIds().size() <= 0) {
			// Create a group and Save requested userIds
			GroupEntity group = createGroup(requestOpinionRequest);
			groupRepository.saveAndFlush(group);
			opinionEntity.setGroupId(group.getGroupId());

			List<UserGroupEntity> userGroupList = createUserGroup(requestOpinionRequest, group);
			userGroupEntityRepository.save(userGroupList);
		} else if (requestOpinionRequest.getRequestedGroupIds().size() == 1) {

			opinionEntity.setGroupId(requestOpinionRequest.getRequestedGroupIds().get(0));
		} else {
			// Create a group and Save requested userIds
			GroupEntity group = createGroup(requestOpinionRequest);
			groupRepository.saveAndFlush(group);
			opinionEntity.setGroupId(group.getGroupId());

			List<UserGroupEntity> userGroupList = combineGroups(requestOpinionRequest, group);
			userGroupEntityRepository.save(userGroupList);
		}

		OpinionEntity savedOpinionEntity = opinionRepository.save(opinionEntity);

		String selfiePicFileName;
		String selfiePicUrl;

		if (null != requestOpinionRequest.getSelfiePic()) {

			Map<String, String> map = awsClientProcessor.prepareFileName(NotificationType.SELFIE.getNotificationId(),
					savedOpinionEntity.getOpinionId() + "",
					PlatformThreadLocalDataFactory.getInstance().getRequestData().getRequestId());
			selfiePicFileName = map.get("FILENAME");
			selfiePicUrl = map.get("URL");
			awsClientProcessor.putObject(requestOpinionRequest.getSelfiePic(), selfiePicFileName,
					PlatformThreadLocalDataFactory.getInstance().getRequestData().getRequestId());
			savedOpinionEntity.setPhoto(selfiePicUrl);

		}

		opinionRepository.save(savedOpinionEntity);

		List<Long> requestedUserIds = new ArrayList<>();
		List<UserGroupEntity> userEntitiesList = userGroupEntityRepository.findByGroupId(opinionEntity.getGroupId());
		for (UserGroupEntity userGroupEntity : userEntitiesList) {
			requestedUserIds.add(userGroupEntity.getId().getUserId());
		}
		gcmService.sendRequestOpinionNotification(PlatformThreadLocalDataFactory.getInstance().getRequestData(),
				requestedUserIds, opinionEntity.getUserId());
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
		gcmService.sendResponseOpinionNotification(PlatformThreadLocalDataFactory.getInstance().getRequestData(),
				opinionResponseInput);

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

	private List<UserGroupEntity> createUserGroup(OpinionRequest requestOpinionRequest, GroupEntity group) {
		List<UserGroupEntity> userGroupEntityList = new ArrayList<UserGroupEntity>();
		for (Long userId : requestOpinionRequest.getRequestedUserIds()) {
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
	 * 
	 * @param requestOpinionRequest
	 * @param group
	 * @return List<UserGroupEntity>
	 */
	private List<UserGroupEntity> combineGroups(OpinionRequest requestOpinionRequest, GroupEntity group) {
		List<UserGroupEntity> userGroupEntityList = new ArrayList<UserGroupEntity>();
		for (int groupId : requestOpinionRequest.getRequestedGroupIds()) {
			List<UserGroupEntity> userGroupEntities = userGroupEntityRepository.findByGroupId(groupId);
			for (UserGroupEntity userGroup : userGroupEntities) {
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

	private GroupEntity createGroup(OpinionRequest requestOpinionInput) {
		GroupEntity groupEntity = new GroupEntity();
		String groupName = requestOpinionInput.getGroupName() == null ? DEFAULT_GROUP_NAME
				: requestOpinionInput.getGroupName();
		// groupEntity.setGroupDesc(DEFAULT);
		groupEntity.setGroupName(groupName);
		groupEntity.setGroupOwner(requestOpinionInput.getRequesterUserId());
		return groupEntity;
	}

	private OpinionEntity createOpinionEntity(OpinionRequest requestOpinionRequest) {
		OpinionEntity opinionEntity = new OpinionEntity();
		opinionEntity.setUserId(requestOpinionRequest.getRequesterUserId());

		Product product = requestOpinionRequest.getProduct();

		if (null != product) {

			// If product is not saved save it
			if (!productRepository.exists(product.getName())) {
				ProductEntity productEntity = new ProductEntity();
				productEntity.setName(product.getName());
				productEntity.setLongDesc(product.getLongDesc());
				productEntity.setShortDesc(product.getShortDesc());
				productEntity.setPrice(product.getPrice());
				productEntity.setImageUrl(product.getImageUrl());
				productRepository.save(productEntity);
			}

			opinionEntity.setProduct(product.getName());

		}

		return opinionEntity;
	}

}
