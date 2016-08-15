package com.svs.hztb.service;

import java.util.List;

import javax.ws.rs.core.Response;

import com.svs.hztb.common.model.ErrorStatus;
import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.model.ResponseHeader;

public abstract class BaseService {

	public HztbResponse buildErrorResponse(List<ErrorStatus> errorStatusList, HztbResponse hztbResponse) {
		RequestData requestData = PlatformThreadLocalDataFactory.getInstance().getRequestData();

		ResponseHeader responseHeader = new ResponseHeader(requestData.getRequestId(),
				String.valueOf(Response.Status.OK.getStatusCode()), errorStatusList);
		hztbResponse.setHeader(responseHeader);
		hztbResponse.setIsError(true);
		return hztbResponse;
	}

	public HztbResponse buildSuccessResponse(HztbResponse hztbResponse) {
		RequestData requestData = PlatformThreadLocalDataFactory.getInstance().getRequestData();

		ResponseHeader responseHeader = new ResponseHeader(requestData.getRequestId(),
				String.valueOf(Response.Status.OK.getStatusCode()));
		hztbResponse.setHeader(responseHeader);
		hztbResponse.setIsError(false);
		return hztbResponse;
	}

}
