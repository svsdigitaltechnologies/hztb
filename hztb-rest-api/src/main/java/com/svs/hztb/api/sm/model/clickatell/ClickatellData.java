package com.svs.hztb.api.sm.model.clickatell;

import java.util.ArrayList;
import java.util.List;

public class ClickatellData {
	private List<ClickatellMessage> message = new ArrayList<ClickatellMessage>();

	public List<ClickatellMessage> getMessage() {
		return message;
	}

	public void setMessage(List<ClickatellMessage> message) {
		this.message = message;
	}

}
