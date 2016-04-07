package com.svs.hztb.orchestration.component.step;

import java.util.Optional;

import com.svs.hztb.orchestration.component.model.FlowContext;

public abstract class AbstractStepDefinition implements StepDefinition {
	
	private Optional<String> id;

	public AbstractStepDefinition() {

	}
	@Override
	public void execute(FlowContext flowContext) throws Exception {
		try {
			process(flowContext);
		} catch(Exception e) {
			throw e;
		}
	}
	
	public abstract void process(FlowContext flowContext) throws Exception;
	protected abstract String getIdentifier();
	
	public Optional<String> getId() {
		return id;
	}

}
