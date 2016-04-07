package com.svs.hztb.sm.common.annotation;

import org.springframework.stereotype.Component;

import com.svs.hztb.orchestration.component.transformer.RestfulEndpointMapper;
import com.svs.hztb.orchestration.component.transformer.RestfulServiceTransformer;
import com.svs.hztb.restfulclient.RestfulEndPoint;
@Component
public class RestfulEndpointMapperImpl implements RestfulEndpointMapper {

	@Override
	public RestfulEndPoint[] getResfultEndpoint(RestfulServiceTransformer<?, ?> restfulServiceTransformer) {
		RestfulTransformer transformerAnnotation = restfulServiceTransformer.getClass()
				.getAnnotation(RestfulTransformer.class);
		if(transformerAnnotation != null) {
			return transformerAnnotation.value();
		}
		return new RestfulEndPoint[] {};
	}

}
