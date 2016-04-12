package com.svs.hztb.sm.common.exception.mapper;

import static com.svs.hztb.common.model.PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING;
import static com.svs.hztb.sm.common.model.ServiceManagerConstants.CUSTOM_HTTP_HEADER_REQUEST_ID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.svs.hztb.common.model.ErrorStatus;
import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.model.ResponseHeader;
import com.svs.hztb.sm.common.model.business.RequestMetaData;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultExceptionMapper {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<HztbResponse> toResponse(HttpServletRequest request, Exception exception) {
		RequestData requestData = PlatformThreadLocalDataFactory.getInstance().getRequestData();

		String message = exception.toString();
		Throwable cause;
		Throwable result = exception;
		while (null != (cause = result.getCause()) && (result != cause)) {
			message = String.format("%s - caused by - %s", message, cause.toString());
			result = cause;
		}

		ResponseHeader responseHeader = new ResponseHeader(requestData.getRequestId(),
				ERROR_OCCURED_DURING_BUSINESS_PROCESSING.getHttpStatusCode());
		responseHeader.getErrors().add(new ErrorStatus(ERROR_OCCURED_DURING_BUSINESS_PROCESSING.getStatusCode(),
				String.format("%s - %s", ERROR_OCCURED_DURING_BUSINESS_PROCESSING.getMessage(), message)));

		HztbResponse response = new HztbResponse(responseHeader);

		return ResponseEntity.status(Integer.valueOf(ERROR_OCCURED_DURING_BUSINESS_PROCESSING.getHttpStatusCode()))
				.header(HttpHeaders.CONTENT_LANGUAGE, RequestMetaData.DEFAULT_ACCEPT_LANGUAGE)
				.header(CUSTOM_HTTP_HEADER_REQUEST_ID, requestData.getRequestId()).body(response);

	}
}
