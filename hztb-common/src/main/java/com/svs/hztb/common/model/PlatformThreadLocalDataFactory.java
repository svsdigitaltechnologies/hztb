package com.svs.hztb.common.model;

public class PlatformThreadLocalDataFactory implements ThreadLocalDataFactory {
	private static final PlatformThreadLocalDataFactory INSTANCE = new PlatformThreadLocalDataFactory();
	
	private ThreadLocal<RequestData> requestDataThreadLocal;
	
	private PlatformThreadLocalDataFactory() {
		requestDataThreadLocal = new ThreadLocal<RequestData>() {
			@Override
			protected RequestData initialValue() {
				return new NonAPIRequestData();
			}
		};
	}
	
	public static PlatformThreadLocalDataFactory getInstance() {
		return INSTANCE;
	}
	
	public RequestData getRequestData() {
		return requestDataThreadLocal.get();
	}
	
	public void setRequestData(RequestData requestData) {
		this.requestDataThreadLocal.set(requestData);
	}
	
}
