package com.svs.hztb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test", schema = "ebdb")
public class UserEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8822080324822243685L;

	@Id
	@Column(name = "idtest")
	private Integer id;

	@Column(name = "testcol")
	private String testcol;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTestcol() {
		return testcol;
	}

	public void setTestcol(String testcol) {
		this.testcol = testcol;
	}

}
