package com.svs.hztb.sm.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.util.PerformanceTimer;
import com.svs.hztb.sm.common.model.business.SimpleRequestMetadata;
import com.svs.hztb.sm.common.util.HeaderUtil;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class HeaderFilter implements Filter {

	@Autowired
	private HeaderUtil headerUtil;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		PerformanceTimer timer = new PerformanceTimer();
		String uri = ((HttpServletRequest) request).getRequestURI();
		RequestData requestData = new SimpleRequestMetadata();
		headerUtil.populateRequestDataFromHeader(requestData, headerUtil.buildHeaderMap((HttpServletRequest) request));
		PlatformThreadLocalDataFactory.getInstance().setRequestData(requestData);
		chain.doFilter(request, response);
		timer.logPerformance("Request completed for URI: " + uri + " Overall time took for : "
				+ ((HttpServletRequest) request).getRequestURI());
	}

	@Override
	public void destroy() {
		// do nothing
	}
}
