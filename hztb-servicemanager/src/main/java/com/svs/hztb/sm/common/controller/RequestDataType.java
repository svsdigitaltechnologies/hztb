package com.svs.hztb.sm.common.controller;

import java.lang.annotation.Target;

import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.sm.common.model.business.RequestMetaData;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
@Target(METHOD)
@Retention(RUNTIME)
public @interface RequestDataType {
	Class<? extends RequestData> value() default RequestMetaData.class;
}
