package com.svs.hztb.service.impl;

import static com.svs.hztb.sm.common.model.ServiceManagerConstants.DEFAULT_GROUP_NAME;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svs.hztb.api.sm.model.group.GroupDetail;
import com.svs.hztb.api.sm.model.group.GroupInput;
import com.svs.hztb.api.sm.model.group.GroupOutput;
import com.svs.hztb.api.sm.model.opinion.Status;
import com.svs.hztb.api.sm.model.user.UserData;
import com.svs.hztb.converters.GroupInputToEntityConverter;
import com.svs.hztb.converters.UserEntityToDataConverter;
import com.svs.hztb.entity.GroupEntity;
import com.svs.hztb.entity.UserEntity;
import com.svs.hztb.entity.UserGroupEntity;
import com.svs.hztb.entity.UserGroupPK;
import com.svs.hztb.repository.GroupRepository;
import com.svs.hztb.repository.UserGroupEntityRepository;
import com.svs.hztb.repository.UserRepository;
import com.svs.hztb.service.GroupDataService;
import com.svs.hztb.sm.common.util.FunctionUtils;

@Service
@Transactional
public class GroupDataServiceImpl implements GroupDataService {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	UserGroupEntityRepository userGroupEntityRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public GroupOutput removeGroup(GroupInput groupInput) {

		GroupOutput groupOutput = null;
		try {
			groupRepository.setGroupNameFor(DEFAULT_GROUP_NAME, groupInput.getGroupId(), groupInput.getUserId());
			groupOutput = buildGroupOutput();
		} catch (Exception e) {

		}
		return groupOutput;

	}

	@Override
	public GroupOutput createGroup(GroupInput groupInput) {
		GroupOutput groupOutput = null;
		try {
			// Create group
			GroupEntity groupEntity = FunctionUtils.convert(groupInput, new GroupInputToEntityConverter());
			groupRepository.saveAndFlush(groupEntity);
			// groupRepository.findByGroupName(groupEntity.getGroupName());

			// Add group members
			List<UserGroupEntity> userGroupList = createUserGroup(groupInput.getAddMembers(), groupEntity);
			userGroupEntityRepository.save(userGroupList);
			groupOutput = buildGroupOutput();
			GroupDetail groupDetail = new GroupDetail();
			List<GroupDetail> groupDetailList = new ArrayList<GroupDetail>();

			groupDetail.setGroupId(groupEntity.getGroupId());
			groupDetail.setGroupName(groupEntity.getGroupName());
			groupDetailList.add(groupDetail);
			groupOutput.setGroupDetailList(groupDetailList);

		} catch (Exception e) {

		}
		return groupOutput;
	}

	private List<UserGroupEntity> createUserGroup(List<Long> addMembers, GroupEntity groupEntity) {
		List<UserGroupEntity> userGroupEntityList = new ArrayList<UserGroupEntity>();
		for (Long userId : addMembers) {
			UserGroupEntity userGroupEntity = new UserGroupEntity();
			UserGroupPK userGroupPK = new UserGroupPK();
			userGroupPK.setGroupId(groupEntity.getGroupId());
			userGroupPK.setUserId(userId);
			userGroupEntity.setId(userGroupPK);
			userGroupEntityList.add(userGroupEntity);

		}
		return userGroupEntityList;

	}

	@Override
	public GroupOutput listGroups(GroupInput groupInput) {

		List<GroupDetail> groupDetailList = new ArrayList<GroupDetail>();
		List<GroupEntity> groupEntityList = groupRepository.findByGroupOwner(groupInput.getUserId(),
				DEFAULT_GROUP_NAME);
		for (GroupEntity groupEntity : groupEntityList) {
			GroupDetail groupDetail = new GroupDetail();
			groupDetail.setGroupId(groupEntity.getGroupId());
			groupDetail.setGroupName(groupEntity.getGroupName());
			List<UserGroupEntity> userGroupList = userGroupEntityRepository.findByGroupId(groupEntity.getGroupId());
			for (UserGroupEntity userGroupEntity : userGroupList) {
				UserEntity userEntity = userRepository.findOne(userGroupEntity.getId().getUserId());
				UserData userData = FunctionUtils.convert(userEntity, new UserEntityToDataConverter());
				groupDetail.getGroupMembers().add(userData);
			}
			groupDetailList.add(groupDetail);
		}
		GroupOutput groupOutput = buildGroupOutput();
		groupOutput.setGroupDetailList(groupDetailList);
		return groupOutput;

	}

	private GroupOutput buildGroupOutput() {
		GroupOutput groupOutput = new GroupOutput();
		groupOutput.setError(false);
		groupOutput.setStatus(Status.SUCCESS);

		return groupOutput;
	}

}
