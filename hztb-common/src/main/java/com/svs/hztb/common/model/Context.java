package com.svs.hztb.common.model;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Context interface which cache data ina a {@link ConcurrentHashMap}
 * 
 * @author skairamkonda
 *
 */
public interface Context {

	/**
	 * Get the element from the context matching the provided name
	 * 
	 * @param name
	 * @return
	 */
	public Object getElement(String name);

	/**
	 * remove and return the context element matching the name
	 * 
	 * @param name
	 * @return
	 */
	public Object removeElement(String name);
}
