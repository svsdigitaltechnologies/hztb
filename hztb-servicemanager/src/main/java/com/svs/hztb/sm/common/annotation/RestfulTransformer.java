package com.svs.hztb.sm.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RestfulTransformer {
	public ServiceManagerRestfulEndpoint[] value();
}
