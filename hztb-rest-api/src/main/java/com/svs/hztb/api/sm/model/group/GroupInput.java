package com.svs.hztb.api.sm.model.group;

import java.util.List;

public class GroupInput {
	private int groupId;
	private Long userId;

	private String groupName;
	private String groupDesc;
	private String newGroupName;
	private List<Long> addMembers;
	private List<Long> deleteMembers;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getAddMembers() {
		return addMembers;
	}

	public void setAddMembers(List<Long> addMembers) {
		this.addMembers = addMembers;
	}

	public List<Long> getDeleteMembers() {
		return deleteMembers;
	}

	public void setDeleteMembers(List<Long> deleteMembers) {
		this.deleteMembers = deleteMembers;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getNewGroupName() {
		return newGroupName;
	}

	public void setNewGroupName(String newGroupName) {
		int i = 0;
		this.newGroupName = newGroupName;
	}

}
