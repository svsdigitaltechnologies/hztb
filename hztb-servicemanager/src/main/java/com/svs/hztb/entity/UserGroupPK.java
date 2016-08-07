package com.svs.hztb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the user_group database table.
 * 
 */
@Embeddable
public class UserGroupPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;

	@Column(name = "group_id", insertable = false, updatable = false)
	private int groupId;

	public UserGroupPK() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserGroupPK)) {
			return false;
		}
		UserGroupPK castOther = (UserGroupPK) other;
		return (this.userId == castOther.userId) && (this.groupId == castOther.groupId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.groupId;

		return hash;
	}
}