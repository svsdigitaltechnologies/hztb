package com.svs.hztb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the group database table.
 * 
 */
@Entity
@Table(name = "group", schema = "ebdb")
@NamedQuery(name="GroupEntity.findAll", query="SELECT g FROM GroupEntity g")
public class GroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="group_id")
	private int groupId;

	@Column(name="group_desc")
	private String groupDesc;

	@Column(name="group_name")
	private String groupName;

	@Column(name="group_owner")
	private int groupOwner;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="groups")
	private List<UserEntity> users;

	public GroupEntity() {
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupDesc() {
		return this.groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupOwner() {
		return this.groupOwner;
	}

	public void setGroupOwner(int groupOwner) {
		this.groupOwner = groupOwner;
	}

	public List<UserEntity> getUsers() {
		return this.users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

}