package com.svs.hztb.orchestration.component.step;

import java.util.Optional;

import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.util.PerformanceTimer;
import com.svs.hztb.orchestration.component.model.FlowContext;

public abstract class AbstractStepDefinition implements StepDefinition {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(AbstractStepDefinition.class);

	private Optional<String> id;

	public AbstractStepDefinition() {

	}

	@Override
	public void execute(FlowContext flowContext) throws Exception {
		LOGGER.trace("Executing step {}",this.getClass());
		
		PerformanceTimer timer = new PerformanceTimer();
		try {
			process(flowContext);
		} catch (Exception exception) {
			LOGGER.error("Exception occured while processing the step {} ",this.getClass());
			throw exception;
		} finally {
			timer.logPerformance(String.format("%s%s", "step.", getIdentifier()));
		}
	}

	public abstract void process(FlowContext flowContext) throws Exception;

	protected abstract String getIdentifier();

	public Optional<String> getId() {
		return id;
	}

}
