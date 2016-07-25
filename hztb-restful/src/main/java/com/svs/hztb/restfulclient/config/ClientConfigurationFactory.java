package com.svs.hztb.restfulclient.config;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.svs.hztb.common.exception.SystemError;
import com.svs.hztb.restfulclient.ClientRequestHandler;
import com.svs.hztb.restfulclient.JSONClientRequestHandler;
import com.svs.hztb.restfulclient.validation.ValidatorService;
import com.svs.hztb.security.key.HttpsTrustManager;
import com.svs.hztb.security.key.KeyStoreProvider;

@Service
public class ClientConfigurationFactory {

	@Autowired
	private ConfigurationProvider configurationProvider;

	@Autowired
	private KeyStoreProvider keyStoreProvider;

	@Autowired
	private ValidatorService validatorService;

	private Map<String, ClientRequestHandler> clientRequestHandlerMap = new HashMap<String, ClientRequestHandler>();

	@PostConstruct
	public void init() {
		loadConfigurations();
	}

	private void loadConfigurations() {
		configurationProvider.getClientConfigurations().stream().forEach(
				config -> clientRequestHandlerMap.put(config.getName().toUpperCase(), getClientRequestHandler(config)));

	}

	private ClientRequestHandler getClientRequestHandler(ClientConfiguration clientConfiguration) {
		if (getMediaType(clientConfiguration).equals(MediaType.APPLICATION_JSON_VALUE)) {
			return new JSONClientRequestHandler(clientConfiguration.getBaseUrl(), clientConfiguration.getHeaders(),
					createContext(clientConfiguration), buildHttpClient(clientConfiguration), validatorService);
		}
		return null;

	}

	private CloseableHttpClient buildHttpClient(ClientConfiguration configuration) {
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
		registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
		registryBuilder.register("https", getConnectionSocketFactory(configuration));
		org.apache.http.config.Registry<ConnectionSocketFactory> registry = registryBuilder.build();

		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
				registry);
		poolingHttpClientConnectionManager.setMaxTotal(configuration.getMaxConnections());
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(configuration.getMaxConnections());
		return createClient(configuration, poolingHttpClientConnectionManager);
	}

	private CloseableHttpClient createClient(ClientConfiguration configuration,
			HttpClientConnectionManager httpClientConnectionManager) {
		int connectionTimeout = configuration.getConnectionTimeout();
		int requestTimeout = configuration.getRequestTimeout();

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeout * 1000)
				.setConnectionRequestTimeout(requestTimeout * 1000).build();
		return buildHttpClient(configuration, requestConfig, httpClientConnectionManager);

	}

	private CloseableHttpClient buildHttpClient(ClientConfiguration configuration, RequestConfig requestConfig,
			HttpClientConnectionManager httpClientConnectionManager) {
		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		if (httpClientConnectionManager != null) {
			httpClientBuilder.setConnectionManager(httpClientConnectionManager);
		}
		httpClientBuilder.setDefaultRequestConfig(requestConfig);
		if (httpClientConnectionManager == null) {
			httpClientBuilder.setSSLSocketFactory(getConnectionSocketFactory(configuration));
		}
		return httpClientBuilder.build();
	}

	public SSLConnectionSocketFactory getConnectionSocketFactory(ClientConfiguration configuration) {

		try {
			javax.net.ssl.SSLContext sslContext = SSLContexts.custom().useSSL().build();

			sslContext.init(null, new X509TrustManager[] { new HttpsTrustManager() }, new SecureRandom());
			/*
			 * KeyStore trustStore = keyStoreProvider.getDefaultTrustStore();
			 * sslContext = SSLContexts.custom().loadTrustMaterial(trustStore,
			 * new TrustSelfSignedStrategy()).build();
			 */
			return new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		} catch (Exception e) {
			throw new SystemError("Could not create SSL context", e);
		}
	}

	private static HttpContext createContext(ClientConfiguration configuration) {
		HttpClientContext context = HttpClientContext.create();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(configuration.getConnectionTimeout() * 1000)
				.setConnectionRequestTimeout(configuration.getRequestTimeout() * 1000).build();
		context.setRequestConfig(requestConfig);
		return context;
	}

	public String getMediaType(ClientConfiguration clientConfiguration) {
		if (clientConfiguration.getHeaders().get("Accept").contains("json")) {
			return MediaType.APPLICATION_JSON_VALUE;
		}
		return MediaType.TEXT_PLAIN_VALUE;
	}

	public ClientRequestHandler getClientRequestHandler(String clientType) {
		return clientRequestHandlerMap.get(clientType);
	}

	public Set<String> getClients() {
		return clientRequestHandlerMap.keySet();
	}
}
