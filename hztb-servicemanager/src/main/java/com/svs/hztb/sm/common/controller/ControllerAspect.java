package com.svs.hztb.sm.common.controller;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.svs.hztb.common.exception.SystemException;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.restfulclient.validation.ValidationRequest;
import com.svs.hztb.restfulclient.validation.ValidatorService;
import com.svs.hztb.sm.common.model.business.RequestMetaData;
import com.svs.hztb.sm.common.util.HeaderUtil;

@Component
@Aspect
public class ControllerAspect {
	
	@Autowired
	private ValidatorService validatorService;
	
	@Autowired
	private HeaderUtil headerUtil;
	
	@Before("within(@org.springframework.web.bind.annotation.RestController *)" + " && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void processControllerRequest(JoinPoint joinPoint) {
		RequestData requestData = buildRequestData(joinPoint);
		ValidationRequest<Object> request = new ValidationRequest<Object>(requestData, requestData);
		PlatformThreadLocalDataFactory.getInstance().setRequestData(requestData);
		validatorService.validate(request);
	}
	
	private RequestData buildRequestData(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Class<? extends RequestData> requestDataType = RequestMetaData.class;
		RequestDataType requestDataTypeAnno = method.getAnnotation(RequestDataType.class);
		if(requestDataTypeAnno != null) {
			requestDataType = requestDataTypeAnno.value();
		}
		try {
			RequestData requestData = requestDataType.newInstance();
			headerUtil.populateRequestDataFromHeader(requestData, headerUtil.buildHeaderMap(headerUtil.getServletThreadLocalRequest()));
			return requestData;
		} catch(InstantiationException | IllegalAccessException e) {
			throw new SystemException("Unable to build request data instance from headers", e);
		}
	}
}
