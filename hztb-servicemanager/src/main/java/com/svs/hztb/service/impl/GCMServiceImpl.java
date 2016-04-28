package com.svs.hztb.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svs.hztb.api.sm.model.notification.NotificationRequest;
import com.svs.hztb.api.sm.model.notification.RequestOpinionNotification;
import com.svs.hztb.api.sm.model.notification.WelcomeNotificationRequest;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionInput;
import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.common.exception.SystemException;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.model.business.User;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.orchestration.component.step.StepDefinition;
import com.svs.hztb.orchestration.component.step.StepDefinitionFactory;
import com.svs.hztb.service.GCMService;
import com.svs.hztb.service.UserDataService;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;

@Service
@Transactional
public class GCMServiceImpl implements GCMService {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(GCMServiceImpl.class);

	@Autowired
	private StepDefinitionFactory stepDefinitionFactory;

	@Value("${gcm.welcome.message}")
	private String welcomeMessage;

	@Value("${gcm.welcome.title}")
	private String title;

	/**
	 * Initial delay before first retry, without jitter.
	 */
	protected static final int BACKOFF_INITIAL_DELAY = 1000;
	/**
	 * Maximum delay before a retry.
	 */
	protected static final int MAX_BACKOFF_DELAY = 1024000;

	protected final Random random = new Random();

	@Override
	@Async
	public void sendWelcomeNotification(RequestData requestData, User user) {

		LOGGER.debug("Async Call {}", "sendWelcomeNotification");

		PlatformThreadLocalDataFactory.getInstance().setRequestData(requestData);
		NotificationRequest notificationRequest = new NotificationRequest();

		notificationRequest.addDeviceRegId(user.getDeviceRegId());
		notificationRequest.setMessage(welcomeMessage);
		Optional.ofNullable(user.getName()).ifPresent(p -> notificationRequest.setMessage(welcomeMessage + p));
		notificationRequest.setTitle(title);

		FlowContext flowContext = new FlowContext(PlatformThreadLocalDataFactory.getInstance().getRequestData());
		flowContext.setModelElement(notificationRequest);

		StepDefinition stepDefinition = stepDefinitionFactory
				.createRestfulStep(ServiceManagerRestfulEndpoint.GCM_SEND_NOTIFICATION);
		try {
			stepDefinition.execute(flowContext);
		} catch (Exception exception) {
			throw new SystemException(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		LOGGER.debug("Async call completed {} ", "sendWelcomeNotification");
	}

	@Override
	public void sendRequestOpinionNotification(RequestData requestData, RequestOpinionInput requestOpinionInput) {

		LOGGER.debug("Async Call {}", "sendRequestOpinionNotification");

		NotificationRequest notificationRequest = new NotificationRequest();

		/*
		 * notificationRequest.setDeviceRegIds(requestOpinionInput.
		 * getRequestedUserIds());
		 */
		notificationRequest.setMessage("Welcome to HowzThisBuddy");
		notificationRequest.setTitle("HowzThisBuddy");

		PlatformThreadLocalDataFactory.getInstance().setRequestData(requestData);

		FlowContext flowContext = new FlowContext(PlatformThreadLocalDataFactory.getInstance().getRequestData());

		flowContext.setModelElement(requestOpinionInput);

		StepDefinition stepDefinition = stepDefinitionFactory
				.createRestfulStep(ServiceManagerRestfulEndpoint.GCM_SEND_NOTIFICATION);
		try {
			stepDefinition.execute(flowContext);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		LOGGER.debug("Async call completed {} ", "sendWelcomeNotification");
	}

}
