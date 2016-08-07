package com.svs.hztb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the group database table.
 * 
 */
@Entity
@Table(name = "group", schema = "ebdb")
@NamedQuery(name = "GroupEntity.findAll", query = "SELECT g FROM GroupEntity g")
public class GroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private int groupId;

	@Column(name = "group_desc")
	private String groupDesc;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "group_owner")
	private Long groupOwner;

	// // bi-directional many-to-many association to User
	// @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
	// private List<UserEntity> users;

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

	public Long getGroupOwner() {
		return this.groupOwner;
	}

	public void setGroupOwner(Long groupOwner) {
		this.groupOwner = groupOwner;
	}

	// public List<UserEntity> getUsers() {
	// return this.users;
	// }
	//
	// public void setUsers(List<UserEntity> users) {
	// this.users = users;
	// }

}