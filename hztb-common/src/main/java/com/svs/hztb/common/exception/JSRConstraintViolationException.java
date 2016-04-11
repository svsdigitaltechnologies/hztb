package com.svs.hztb.common.exception;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

public class JSRConstraintViolationException extends ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9027256812688596475L;

	private final Set<ConstraintViolation<Object>> constraintViolations;

	public JSRConstraintViolationException(String message,
			Set<? extends ConstraintViolation<Object>> constraintViolations) {
		super(message);
		if (constraintViolations == null) {
			this.constraintViolations = null;
		} else {
			this.constraintViolations = new HashSet<ConstraintViolation<Object>>(constraintViolations);
		}
	}

	public JSRConstraintViolationException(Set<? extends ConstraintViolation<Object>> constraintViolations) {
		this(null, constraintViolations);
	}

	public Set<ConstraintViolation<Object>> getConstraintViolations() {
		return constraintViolations;
	}

}
