package com.svs.hztb.orchestration.component.model;

import java.util.List;

import com.svs.hztb.common.exception.SystemException;
import com.svs.hztb.common.model.ContextImpl;
import com.svs.hztb.common.model.RequestData;

public class FlowContext extends ContextImpl {

	private static final String ELEMENT_PREFIX = "ELEMENT-";
	private static final String COLLECTION_PREFIX = "COLLECTION-";
	private final RequestData requestData;

	public FlowContext(RequestData requestData) {
		this.requestData = requestData;
	}

	public static String getThreadSafeKey(String key) {
		return key + "$$" + Thread.currentThread().getId();
	}

	public RequestData getRequestData() {
		return requestData;
	}

	public <T> T getModelElement(Class<T> clazz) {
		return (T) context.get(ELEMENT_PREFIX + clazz.getName());
	}

	public void setModelElement(Object element) {
		if (element instanceof RequestData) {
			throw new SystemException("Request data elements should be added via the FlowContext constructor");
		}
		context.put(ELEMENT_PREFIX+element.getClass().getName(), element);
	}
	
	public <T> List<T> getModelCollection(Class<T> clazz) {
		return (List<T>)context.get(COLLECTION_PREFIX + clazz.getName());
	}
	//to do to add from line 228
}
