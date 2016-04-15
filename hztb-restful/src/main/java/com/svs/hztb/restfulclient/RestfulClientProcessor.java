package com.svs.hztb.restfulclient;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.svs.hztb.restfulclient.config.ClientConfigurationFactory;
import com.svs.hztb.restfulclient.model.RestfulRequest;
import com.svs.hztb.restfulclient.model.RestfulResponse;

@Component
public class RestfulClientProcessor {
	@Autowired
	private ClientConfigurationFactory clientConfigurationFactory;

	public <T, S> RestfulResponse<S> execute(RestfulRequest<T, S> request) throws IOException {
		ClientRequestHandler clientRequestHandler = clientConfigurationFactory
				.getClientRequestHandler(request.getEndpoint().getClientType().getName());
		return clientRequestHandler.execute(request);
	}
}
