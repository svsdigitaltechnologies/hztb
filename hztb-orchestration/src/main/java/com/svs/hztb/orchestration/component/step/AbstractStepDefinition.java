package com.svs.hztb.orchestration.component.step;

import java.util.Optional;

import com.svs.hztb.common.exception.SystemError;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.util.PerformanceTimer;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.orchestration.exception.BusinessError;

public abstract class AbstractStepDefinition implements StepDefinition {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(AbstractStepDefinition.class);

	private Optional<String> id;

	public AbstractStepDefinition() {

	}

	@Override
	public void execute(FlowContext flowContext) throws Exception {
		LOGGER.trace("Executing step {}", this.getClass());

		PerformanceTimer timer = new PerformanceTimer();
		try {
			process(flowContext);
		} catch (BusinessError exception) {
			LOGGER.error("Business Exception occured while processing the step {} ", this.getClass());
			throw exception;
		} 
		catch (Exception exception) {
			LOGGER.error("Exception occured while processing the step {} ", this.getClass());
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
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
