package com.svs.hztb.sm.common.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.logging.filter.RequestWrapper;
import com.svs.hztb.common.logging.filter.ResponseWrapper;
import com.svs.hztb.common.util.PerformanceTimer;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(RequestResponseLoggingFilter.class);
	private static final String REQUEST_BODY_MESSAGE_TEMPLATE = "Request Body: {}";
	private static final String RESPONSE_BODY_MESSAGE_TEMPLATE = "Response Body: {}";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest requestToUse = request;
		HttpServletResponse responseToUse = response;

		try {
			requestToUse = new RequestWrapper(request);
			responseToUse = new ResponseWrapper(response);
			logRequest((RequestWrapper) requestToUse);
		} catch (Exception exception) {
			LOGGER.debug("Failed to log the request payload with exception {}", exception);
		}
		try {
			filterChain.doFilter(requestToUse, responseToUse);
		} finally {
			try {
				logResponse((ResponseWrapper) responseToUse);
			} catch (Exception exception) {
				LOGGER.debug("Failed to log the response payload with exception {}", exception);

			}
		}
	}

	private void logRequest(final RequestWrapper requestWrapper) {
		try {
			final PerformanceTimer timer = new PerformanceTimer();
			timer.logPerformance(requestWrapper.getRequestURL().toString());
			LOGGER.debug("Requested URI: " + requestWrapper.getRequestURL());
			LOGGER.debug("Logging incoming HTTP request");
			String contents = requestWrapper.getContents();
			LOGGER.debug(REQUEST_BODY_MESSAGE_TEMPLATE, contents);
		} catch (IOException ioException) {
			LOGGER.debug("Failed to parse request payload: {}", ioException);
		}
	}

	private void logResponse(final ResponseWrapper responseWrapper) {
		try {
			LOGGER.debug("Logging Outgoing HTTP response");
			String payload = new String(responseWrapper.toByteArray(), responseWrapper.getCharacterEncoding());
			LOGGER.debug(RESPONSE_BODY_MESSAGE_TEMPLATE, payload);
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			LOGGER.debug("Failed to parse response payload: {}", unsupportedEncodingException);
		}
	}

}
