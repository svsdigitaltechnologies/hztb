package com.svs.hztb.common.model;


public class HztbResponse {

	private ResponseHeader header;
	
	public HztbResponse(ResponseHeader header) {
		this.header = header;
	}
	
	public ResponseHeader getHeader() {
		return header;
	}
}
