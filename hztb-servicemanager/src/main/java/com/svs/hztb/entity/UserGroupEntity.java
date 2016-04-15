package com.svs.hztb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_group database table.
 * 
 */
@Entity
@Table(name="user_group")
@NamedQuery(name="UserGroup.findAll", query="SELECT u FROM UserGroupEntity u")
public class UserGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserGroupPK id;

	public UserGroupEntity() {
	}

	public UserGroupPK getId() {
		return this.id;
	}

	public void setId(UserGroupPK id) {
		this.id = id;
	}

}