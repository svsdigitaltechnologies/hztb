package com.svs.hztb.api.sm.model.group;

import java.util.ArrayList;
import java.util.List;

public class GroupDetail {
	String groupName;
	List<Integer> groupMembers = new ArrayList<Integer>();
	int groupId;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<Integer> getGroupMembers() {
		return groupMembers;
	}
	public void setGroupMembers(List<Integer> groupMembers) {
		this.groupMembers = groupMembers;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	
}
