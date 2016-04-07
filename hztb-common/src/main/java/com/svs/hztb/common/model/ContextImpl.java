package com.svs.hztb.common.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ContextImpl implements Context {

	protected Map<String, Object> context = new ConcurrentHashMap<String, Object>();
	
	@Override
	public Object getElement(String name) {
		return context.get(name);
	}

	@Override
	public Object removeElement(String name) {
		return context.remove(name);
	}

}
