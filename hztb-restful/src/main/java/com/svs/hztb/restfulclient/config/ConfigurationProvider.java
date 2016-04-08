package com.svs.hztb.restfulclient.config;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationProvider {
	private final Environment env;
	private Set<ClientConfiguration> configurations;
	private Set<String> clients;

	@Autowired
	public ConfigurationProvider(Environment env) {
		this.env = env;
		clients = getRestfulClients();
		configurations = loadClients(clients);
	}

	public Set<ClientConfiguration> getClientConfigurations() {
		return configurations;
	}

	public Set<String> getClients() {
		return clients;
	}

	private Set<String> getRestfulClients() {
		return Arrays.asList(env.getProperty(ConfigurationConstants.CLIENT_VALUE).split(",")).stream()
				.collect(Collectors.toSet());

	}

	private Set<ClientConfiguration> loadClients(Set<String> clients) {
		return clients.stream().map(x -> getConfiguration(x)).collect(Collectors.toSet());
	}

	private ClientConfiguration getConfiguration(final String clientName) {
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setName(clientName);
		clientConfiguration.setBaseUrl(env.getProperty(clientName + ConfigurationConstants.BASE_URL));
		getHeaders(clientConfiguration, clientName);
		getConnectionSettings(clientConfiguration, clientName);
		return clientConfiguration;
	}

	private void getHeaders(final ClientConfiguration configuration, final String clientName) {
		configuration.addHeader(ConfigurationConstants.CONTENT_HEADER,
				env.getProperty(clientName + ConfigurationConstants.HEADER_CONTENT_VALUE));
		configuration.addHeader(ConfigurationConstants.ACCEPT_HEADER,
				env.getProperty(clientName + ConfigurationConstants.HEADER_ACCEPT_VALUE));
		if (StringUtils.isNotEmpty(env.getProperty(clientName + ConfigurationConstants.HEADER_X_VERSION_VALUE))) {
			configuration.addHeader(ConfigurationConstants.X_VERSION_HEADER,
					env.getProperty(clientName + ConfigurationConstants.HEADER_X_VERSION_VALUE));
		}
		if (StringUtils.isNotEmpty(env.getProperty(clientName + ConfigurationConstants.HEADER_AUTHORIZATION_VALUE))) {
			configuration.addHeader(ConfigurationConstants.AUTHORIZATION_HEADER,
					env.getProperty(clientName + ConfigurationConstants.HEADER_AUTHORIZATION_VALUE));
		}
	}

	private void getConnectionSettings(final ClientConfiguration configuration, final String clientName) {
		int maxConnections = getIntProperty(clientName + ConfigurationConstants.HTTP_CONNECTIONS_MAX);
		if (maxConnections == 0) {
			configuration.setMaxConnections(10);
		} else {
			configuration.setMaxConnections(getIntProperty(clientName + ConfigurationConstants.HTTP_CONNECTIONS_MAX));
		}
		configuration
				.setConnectionTimeout(getIntProperty(clientName + ConfigurationConstants.CONNECTION_TIMEOUT_VALUE));
		configuration.setRequestTimeout(getIntProperty(clientName + ConfigurationConstants.REQUEST_TIMEOUT_VALUE));
	}

	private int getIntProperty(String propertyName) {
		int value = 0;
		try {
			value = Integer.parseInt(env.getProperty(propertyName));
		} catch (Exception e) {

		}
		return value;
	}
}
