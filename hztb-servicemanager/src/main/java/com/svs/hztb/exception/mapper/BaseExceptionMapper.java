package com.svs.hztb.exception.mapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.svs.hztb.common.exception.BaseException;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.ErrorStatus;
import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.model.ResponseHeader;
import com.svs.hztb.sm.common.model.ServiceManagerConstants;

public class BaseExceptionMapper<T extends BaseException> {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(BaseExceptionMapper.class);

	public BaseExceptionMapper() {
		super();
	}

	public ResponseEntity<HztbResponse> toResponse(T exception) {

		LOGGER.error("Returning appropriate response from platform exception: {}", exception);

		RequestData requestData = PlatformThreadLocalDataFactory.getInstance().getRequestData();

		String httpStatusCode = exception.getStatusCodes().get(0).getLeft().getHttpStatusCode();

		ResponseHeader responseHeader = new ResponseHeader(requestData.getRequestId(), httpStatusCode);
		exception.getStatusCodes().stream().forEach(pair -> responseHeader.getErrors().add(
				new ErrorStatus(pair.getLeft().getStatusCode(), String.format("%s", pair.getLeft().getMessage()))));

		HztbResponse response = new HztbResponse(responseHeader);

		return ResponseEntity.status(Integer.valueOf(httpStatusCode))
				.header(HttpHeaders.CONTENT_LANGUAGE, ServiceManagerConstants.CONTENT_LANUGUAGE)
				.header(ServiceManagerConstants.CUSTOM_HTTP_HEADER_REQUEST_ID, requestData.getRequestId())
				.body(response);
	}

}
