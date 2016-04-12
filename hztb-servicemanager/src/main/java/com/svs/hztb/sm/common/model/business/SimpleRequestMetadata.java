package com.svs.hztb.sm.common.model.business;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.HeaderParam;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;
import com.svs.hztb.common.model.RequestData;
import static com.svs.hztb.sm.common.model.ServiceManagerConstants.CUSTOM_HTTP_HEADER_REQUEST_ID;

public class SimpleRequestMetadata implements RequestData {
	
	@HeaderParam(CUSTOM_HTTP_HEADER_REQUEST_ID)
	@Pattern(regexp = HZTBRegularExpressions.REQUEST_ID)
	@Size(min = 1, max = 64)
	@NotNull
	private String requestId;
	
	
	@Override
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	
}
