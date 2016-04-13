package com.svs.hztb.exception.mapper;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.orchestration.exception.BusinessException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BusinessExceptionMapper extends BaseExceptionMapper<BusinessException> {
	
	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(BusinessExceptionMapper.class);

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<HztbResponse> toResponse(BusinessException exception) {
		LOGGER.debug("In BusinessExceptionMapper.toResponse()", exception);
		return super.toResponse(exception);
	}

	
}
