package com.svs.hztb.common.logging.serialize;

import java.lang.annotation.Annotation;

import com.fasterxml.jackson.databind.JsonSerializer;

public abstract class AbstractLogSerializer<T, A extends Annotation> extends JsonSerializer<T> {
	protected abstract void copyAnnotationValues(A annotation);

	@SuppressWarnings("unchecked")
	public void setAnnotation(Annotation annotation) {
		copyAnnotationValues((A) annotation);
	}
}
