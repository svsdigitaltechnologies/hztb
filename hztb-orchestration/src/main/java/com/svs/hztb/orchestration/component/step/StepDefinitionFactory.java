package com.svs.hztb.orchestration.component.step;

import com.svs.hztb.restfulclient.RestfulEndPoint;

public interface StepDefinitionFactory {
	<T, S> RestfulStepDefinition<T, S> createRestfulStep(RestfulEndPoint endPoint);
}
