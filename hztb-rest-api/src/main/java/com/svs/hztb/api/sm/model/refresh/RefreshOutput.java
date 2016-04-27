package com.svs.hztb.api.sm.model.refresh;

import com.svs.hztb.api.sm.model.BasicOutput;

public class RefreshOutput extends BasicOutput {
	
	private Error errorVO;

	public Error getErrorVO() {
		return errorVO;
	}

	public void setErrorVO(Error errorVO) {
		this.errorVO = errorVO;
	}
	

}
