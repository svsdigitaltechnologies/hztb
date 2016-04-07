package com.svs.hztb.orchestration.component.step;

import com.svs.hztb.orchestration.component.model.FlowContext;

public interface StepDefinition {

	void execute(FlowContext flowContext) throws Exception;
}
