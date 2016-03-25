package com.svs.hztb.common.exception;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.xml.bind.ValidationException;

public abstract class JSRConstraintViolationException extends ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9027256812688596475L;

	private final Set<ConstraintViolation<?>> constraintViolations;

	public JSRConstraintViolationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
		super(message);
		if (constraintViolations == null) {
			this.constraintViolations = null;
		} else {
			this.constraintViolations = new HashSet<ConstraintViolation<?>>(constraintViolations);
		}
	}

	public JSRConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
		this(null, constraintViolations);
	}

	public Set<ConstraintViolation<?>> getConstraintViolations() {
		return constraintViolations;
	}

}
