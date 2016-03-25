package com.svs.hztb.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.svs.hztb.common.exception.SystemException;
import com.svs.hztb.common.model.HztbResponse;

@ControllerAdvice
public class SystemExceptionMapper extends BaseExceptionMapper<SystemException> {
	private final static Logger logger = LoggerFactory.getLogger(SystemExceptionMapper.class);
	
	@Override
	@ExceptionHandler(SystemException.class)
	public ResponseEntity<HztbResponse> toResponse(SystemException exception) {
		return super.toResponse(exception);
	}
}
