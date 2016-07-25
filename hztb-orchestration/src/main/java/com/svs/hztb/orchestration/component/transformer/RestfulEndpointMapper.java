package com.svs.hztb.orchestration.component.transformer;

import com.svs.hztb.restfulclient.RestfulEndPoint;

@FunctionalInterface
public interface RestfulEndpointMapper {
	RestfulEndPoint[] getResfultEndpoint(RestfulServiceTransformer<?, ?> restfulServiceTransformer);
}
