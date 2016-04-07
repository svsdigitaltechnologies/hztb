package com.svs.hztb.common.model;

public interface Context {
	
	public Object getElement(String name);
	
	public Object removeElement(String name);
}
