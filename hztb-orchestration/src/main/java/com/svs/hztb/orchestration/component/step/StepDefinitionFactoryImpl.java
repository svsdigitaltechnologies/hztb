package com.svs.hztb.orchestration.component.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.svs.hztb.orchestration.component.transformer.RestfulServiceTransformerFactory;
import com.svs.hztb.restfulclient.RestfulClientProcessor;
import com.svs.hztb.restfulclient.RestfulEndPoint;

@Component
public class StepDefinitionFactoryImpl implements StepDefinitionFactory {

	@Autowired
	protected RestfulClientProcessor restfulClientProcessor;

	@Autowired
	protected RestfulServiceTransformerFactory transformerFactory;

	@Override
	public <T, S> RestfulStepDefinition<T, S> createRestfulStep(RestfulEndPoint endPoint) {
		return new RestfulStepDefinition<>(restfulClientProcessor, transformerFactory.getTransformer(endPoint),
				endPoint);
	}

}
