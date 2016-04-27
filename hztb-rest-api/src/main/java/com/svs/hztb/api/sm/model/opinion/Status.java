package com.svs.hztb.api.sm.model.opinion;

public enum Status {
	SUCCESS(0);	
	private final int status;
	
	private Status(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
	
}
