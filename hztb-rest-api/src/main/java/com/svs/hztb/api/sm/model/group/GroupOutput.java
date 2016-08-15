package com.svs.hztb.api.sm.model.group;

import java.util.ArrayList;
import java.util.List;

import com.svs.hztb.api.sm.model.BasicOutput;
import com.svs.hztb.api.sm.model.Error;
import com.svs.hztb.api.sm.model.opinion.Status;

public class GroupOutput extends BasicOutput {
	private Status status;
	private Error errorOutput;
	private List<GroupDetail> groupDetailList = new ArrayList<>();

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Error getErrorOutput() {
		return errorOutput;
	}

	public void setErrorOutput(Error errorOutput) {
		this.errorOutput = errorOutput;
	}

	public List<GroupDetail> getGroupDetailList() {
		return groupDetailList;
	}

	public void setGroupDetailList(List<GroupDetail> groupDetailList) {

		this.groupDetailList = groupDetailList;
	}
}
