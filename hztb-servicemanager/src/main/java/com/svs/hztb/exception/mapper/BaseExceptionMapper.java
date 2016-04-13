package com.svs.hztb.exception.mapper;

import org.springframework.http.ResponseEntity;

import com.svs.hztb.common.exception.BaseException;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.ErrorStatus;
import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.common.model.ResponseHeader;

public class BaseExceptionMapper<T extends BaseException> {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(BaseExceptionMapper.class);

	public BaseExceptionMapper() {
		super();
	}

	public ResponseEntity<HztbResponse> toResponse(T exception) {
		LOGGER.debug("In BaseExceptionMapper.toResponse()", exception);
		String httpStatusCode = exception.getStatusCodes().get(0).getLeft().getHttpStatusCode();

		ResponseHeader responseHeader = new ResponseHeader("1212", httpStatusCode);
		exception.getStatusCodes().stream().forEach(pair ->	responseHeader.getErrors().add(
				new ErrorStatus(pair.getLeft().getStatusCode(), String.format("%s", pair.getLeft().getMessage()))));

		HztbResponse response = new HztbResponse(responseHeader);

		return ResponseEntity.status(Integer.valueOf(httpStatusCode)).body(response);
	}

}
