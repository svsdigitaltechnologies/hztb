package com.svs.hztb.common.enums;

import com.svs.hztb.client.ClientErrorMapping;
import com.svs.hztb.client.ClientType;

public enum ServiceManagerClientType implements ClientType {
	DATASERVICES(ClientErrorMappingImpl.DS);
	
	private ClientErrorMappingImpl errorMapping;

	private ServiceManagerClientType(ClientErrorMappingImpl errorMapping) {
		this.errorMapping = errorMapping;
	}
	
	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public ClientErrorMapping getErrorMapping() {
		return errorMapping;
	}

}
