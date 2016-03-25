package com.svs.hztb.ds.model;

public class DataServiceRequest<T> extends AbstractRequest<T> {

	public DataServiceRequest(T payload) {
		this.payload = payload;
	}
}
