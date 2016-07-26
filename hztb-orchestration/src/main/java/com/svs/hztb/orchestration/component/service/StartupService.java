package com.svs.hztb.orchestration.component.service;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.orchestration.component.transformer.RestfulEndpointMapper;
import com.svs.hztb.orchestration.component.transformer.RestfulServiceTransformer;
import com.svs.hztb.orchestration.component.transformer.RestfulServiceTransformerFactory;
import com.svs.hztb.restfulclient.RestfulEndPoint;

@Component
public class StartupService {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(StartupService.class);

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private RestfulServiceTransformerFactory restfulServiceTransformerFactory;

	@Autowired
	private RestfulEndpointMapper restfulEndpointMapper;

	private AtomicBoolean invoked = new AtomicBoolean();

	public void start() {
		if (!invoked.getAndSet(true)) {
			scanTransformers();
		}
	}

	private void scanTransformers() {
		LOGGER.trace("Scanning transformers for spring components of type {}", RestfulServiceTransformer.class);
		for (RestfulServiceTransformer<?, ?> restfulServiceTransformer : ctx
				.getBeansOfType(RestfulServiceTransformer.class).values()) {
			RestfulEndPoint[] restfulEndPoint = restfulEndpointMapper.getResfultEndpoint(restfulServiceTransformer);
			if (restfulEndPoint != null) {
				restfulServiceTransformerFactory.addTransformer(restfulEndPoint, restfulServiceTransformer);
			}
		}
	}
}
