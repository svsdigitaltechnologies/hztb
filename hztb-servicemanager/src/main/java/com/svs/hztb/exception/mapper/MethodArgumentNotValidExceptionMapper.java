package com.svs.hztb.exception.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.svs.hztb.common.model.HztbResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MethodArgumentNotValidExceptionMapper extends AbstractJSRConstraintExceptionMapper {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<HztbResponse> toResponse(MethodArgumentNotValidException exception) {
		List<Pair<String, String>> errors = new ArrayList<>();

		for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			String reason = error.getCode();
			String field = getField(error);
			errors.add(Pair.of(field, reason));
		}
		return toResponse(errors);
	}

	private String getField(ObjectError error) {
		String fieldName = error.getCodes()[0];
		int pos = fieldName.lastIndexOf('.');
		if (pos != -1 && fieldName.length() > pos) {
			fieldName = fieldName.substring(++pos);
		}
		return fieldName;
	}
}
