package com.svs.speakthrough.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6920533499219663779L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createddate")
	private Date createTimeStamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "systemmodstamp")
	private Date updateTimeStamp;

	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public Date getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Date updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	
}
