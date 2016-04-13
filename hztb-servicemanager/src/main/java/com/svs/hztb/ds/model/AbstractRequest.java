package com.svs.hztb.ds.model;

public abstract class AbstractRequest<T> {

	protected T payload;

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}
}
