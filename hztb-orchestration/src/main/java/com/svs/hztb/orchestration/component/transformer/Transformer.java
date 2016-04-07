package com.svs.hztb.orchestration.component.transformer;

import com.svs.hztb.orchestration.component.model.FlowContext;

public interface Transformer {
	public default boolean proceed(FlowContext flowContext) {
		return true;
	}
}
