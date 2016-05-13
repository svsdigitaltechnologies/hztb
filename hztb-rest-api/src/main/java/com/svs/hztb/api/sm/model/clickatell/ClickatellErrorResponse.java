package com.svs.hztb.api.sm.model.clickatell;

public class ClickatellErrorResponse {
	private ClickatellData data;
	private ClickatellError error;

	public ClickatellError getError() {
		return error;
	}

	public void setError(ClickatellError error) {
		this.error = error;
	}

	public ClickatellData getData() {
		return data;
	}

	public void setData(ClickatellData data) {
		this.data = data;
	}
	
}
