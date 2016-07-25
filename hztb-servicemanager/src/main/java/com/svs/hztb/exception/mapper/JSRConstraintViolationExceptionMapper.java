package com.svs.hztb.exception.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.svs.hztb.common.exception.JSRConstraintViolationError;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.HztbResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JSRConstraintViolationExceptionMapper extends AbstractJSRConstraintExceptionMapper {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(JSRConstraintViolationExceptionMapper.class);

	@ExceptionHandler(JSRConstraintViolationError.class)
	public ResponseEntity<HztbResponse> toResponse(JSRConstraintViolationError exception) {
		LOGGER.error("JSON JSR-303 validation exception occured: {}", exception);
		List<Pair<String, String>> errors = new ArrayList<>();
		for (ConstraintViolation<?> violatoin : exception.getConstraintViolations()) {
			String field = violatoin.getPropertyPath().toString();
			String reason = getReason(violatoin);
			errors.add(Pair.of(field, reason));
		}
		return toResponse(errors);
	}

	private String getReason(ConstraintViolation<?> violation) {
		String reason = violation.getConstraintDescriptor().getAnnotation().annotationType().getName();
		int pos = reason.lastIndexOf('.');
		if (pos != -1 && reason.length() > pos) {
			reason = reason.substring(++pos);
		}
		return reason;
	}
}
