package com.svs.hztb.exception.mapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.svs.hztb.common.exception.SystemError;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.HztbResponse;

@ControllerAdvice
public class SystemExceptionMapper extends BaseExceptionMapper<SystemError> {
	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(SystemExceptionMapper.class);

	@Override
	@ExceptionHandler(SystemError.class)
	public ResponseEntity<HztbResponse> toResponse(SystemError exception) {
		LOGGER.debug("System Exception occured: {}", exception);
		return super.toResponse(exception);
	}
}
