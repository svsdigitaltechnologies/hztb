package com.svs.hztb.orchestration.component.transformer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.svs.hztb.common.exception.SystemError;
import com.svs.hztb.restfulclient.RestfulEndPoint;

@Component
public class RestfulServiceTransformerFactory {
	private Map<RestfulEndPoint, RestfulServiceTransformer<?, ?>> transformerMap = new HashMap<>();

	public void addTransformer(RestfulEndPoint[] restfulEndPoints,
			RestfulServiceTransformer<?, ?> restfulServiceTransformer) {
		for (RestfulEndPoint p : restfulEndPoints) {
			if (transformerMap.containsKey(p)) {
				throw new SystemError(
						String.format("Multiple transformers configured for the same endpoint %s", p.name()));
			}
			transformerMap.put(p, restfulServiceTransformer);
		}
	}

	public <T, S> RestfulServiceTransformer<T, S> getTransformer(RestfulEndPoint endpoint) {
		return (RestfulServiceTransformer<T, S>) transformerMap.get(endpoint);
	}
}
