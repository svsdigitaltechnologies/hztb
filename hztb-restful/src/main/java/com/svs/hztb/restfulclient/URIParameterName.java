package com.svs.hztb.restfulclient;

public interface URIParameterName {
	String name();
	
	String getValue();
	
	default String param() {
		return "{[" + getValue() + "]}";
	}
}
