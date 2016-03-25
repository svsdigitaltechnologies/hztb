package com.svs.hztb.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.exception.BusinessException;
import com.svs.hztb.exception.mapper.BaseExceptionMapper;

@ControllerAdvice
public class BusinessExceptionMapper extends BaseExceptionMapper<BusinessException> {
	private final static Logger logger = LoggerFactory.getLogger(BusinessExceptionMapper.class);
	
	@Override
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<HztbResponse> toResponse(BusinessException exception) {
		return super.toResponse(exception);
	}

	
}
