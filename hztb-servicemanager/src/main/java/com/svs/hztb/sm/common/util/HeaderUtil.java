package com.svs.hztb.sm.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.svs.hztb.common.exception.SystemException;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.model.StatusCode;
import com.svs.hztb.orchestration.exception.BusinessException;
import com.svs.hztb.sm.common.model.ServiceManagerConstants;
import com.svs.hztb.sm.common.model.business.RequestMetaData;

@Component
public class HeaderUtil {

	public void checkMandatory(RequestData requestData, String... customHttpHeaderName) {
		List<Pair<StatusCode, String>> errors = new ArrayList<>();
		for (Field field : requestData.getClass().getDeclaredFields()) {
			HeaderParam headerParam = field.getAnnotation(HeaderParam.class);
			if (headerParam != null && Arrays.asList(customHttpHeaderName).contains(headerParam.value())) {
				Pair<StatusCode, String> error = getError(requestData, headerParam.value(), field);
				if (error != null) {
					errors.add(error);
				}
			}
		}
		if (!errors.isEmpty()) {
			throw new BusinessException("Custom mandatory check for mandatory header fields failed", errors);
		}
	}

	public void populateRequestDataFromHeader(RequestData requestData, Map<String, String> headers) {
		for (Field field : requestData.getClass().getDeclaredFields()) {
			HeaderParam headerParam = field.getAnnotation(HeaderParam.class);
			if (headerParam != null) {
				String headerValue = headers.get(headerParam.value());
				if (headerValue != null) {
					field.setAccessible(true);
					try {
						field.set(requestData, headerValue);
					} catch (IllegalAccessException | IllegalArgumentException e) {
						throw new SystemException(String.format(
								"Failed to reflectively populate request data with header values for field %s",
								field.getName()));
					}
				}
			}
		}
	}

	public HttpServletRequest getServletThreadLocalRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	public Map<String, String> buildHeaderMap(HttpServletRequest httpServletRequest) {
		Map<String, String> result = new HashMap<>();
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				result.put(headerName.toUpperCase(), httpServletRequest.getHeader(headerName));
			}
		}
		if (result.get(ServiceManagerConstants.HTTP_HEADER_ACCEPT) == null) {
			result.put(ServiceManagerConstants.HTTP_HEADER_ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		}
		if (result.get(ServiceManagerConstants.HTTP_HEADER_CACHE_CONTROL) == null) {
			result.put(ServiceManagerConstants.HTTP_HEADER_CACHE_CONTROL, RequestMetaData.DEFAULT_CACHE_CONTROL);
		}
		if (result.get(ServiceManagerConstants.HTTP_HEADER_ACCEPT_LANGUAGE) == null) {
			result.put(ServiceManagerConstants.HTTP_HEADER_ACCEPT_LANGUAGE, RequestMetaData.DEFAULT_ACCEPT_LANGUAGE);
		}
		return result;
	}

	private Pair<StatusCode, String> getError(RequestData requestData, String headerParamName, Field field) {
		try {
			field.setAccessible(true);
			String fieldValue = (String) field.get(requestData);
			if (StringUtils.isEmpty(fieldValue)) {
				return Pair.of(PlatformStatusCode.MANDATORY_DOCUMENT_FIELD_MISSING,
						String.format("The header field %s is mandatory", headerParamName));
			}
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new SystemException(
					"Custom mandatory check for mandatory header fields failed because the field is NOT accessible",
					PlatformStatusCode.MANDATORY_DOCUMENT_FIELD_MISSING);
		}
		return null;
	}
}
