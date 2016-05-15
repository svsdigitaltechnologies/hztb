package com.svs.hztb.converters;

import java.util.function.Function;

import com.svs.hztb.api.sm.model.group.GroupInput;
import com.svs.hztb.entity.GroupEntity;

public class GroupInputToEntityConverter implements 
Function<GroupInput, GroupEntity>{

	@Override
	public GroupEntity apply(GroupInput groupInput) {
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setGroupName(groupInput.getGroupName());
		groupEntity.setGroupOwner(groupInput.getUserId());
		return groupEntity;
	}

}
