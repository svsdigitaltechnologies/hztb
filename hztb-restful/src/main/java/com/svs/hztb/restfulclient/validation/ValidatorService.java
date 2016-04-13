package com.svs.hztb.restfulclient.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

import com.svs.hztb.common.exception.JSRConstraintViolationException;

@Component
public class ValidatorService {

	private Validator validator;

	public ValidatorService() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		validator = vf.getValidator();
	}

	public void validate(ValidationRequest<Object> validationRequest) {
		Set<ConstraintViolation<Object>> violations = validator.validate(validationRequest.getPayload());
		if (violations != null && !violations.isEmpty()) {
			throw new JSRConstraintViolationException(String.format("Validation errors for class: %s",
					validationRequest.getPayload().getClass().getSimpleName()), violations);
		}
	}
}
