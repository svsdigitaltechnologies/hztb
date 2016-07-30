package com.svs.hztb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svs.hztb.api.sm.model.notification.NotificationRequest;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.OpinionRequest;
import com.svs.hztb.api.sm.model.product.Product;
import com.svs.hztb.common.enums.NotificationType;
import com.svs.hztb.common.exception.SystemError;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.model.business.User;
import com.svs.hztb.entity.OpinionEntity;
import com.svs.hztb.entity.ProductEntity;
import com.svs.hztb.entity.UserEntity;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.orchestration.component.step.StepDefinition;
import com.svs.hztb.orchestration.component.step.StepDefinitionFactory;
import com.svs.hztb.repository.OpinionRepository;
import com.svs.hztb.repository.ProductRepository;
import com.svs.hztb.repository.UserRepository;
import com.svs.hztb.service.GCMService;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;

@Service
@Transactional
public class GCMServiceImpl implements GCMService {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(GCMServiceImpl.class);

	@Autowired
	private StepDefinitionFactory stepDefinitionFactory;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;


	@Autowired
	private OpinionRepository opinionRepository;
	
	@Value("${gcm.welcome.message}")
	private String welcomeMessage;

	@Value("${gcm.welcome.title}")
	private String title;

	@Value("${gcm.opinionrequest.message}")
	private String opinionRequestMessage;
	
	@Value("${gcm.opinionresponse.message}")
	private String opinionResponseMessage;
	
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
		Optional.ofNullable(user.getName()).ifPresent(p -> notificationRequest.setMessage(welcomeMessage + "," + p));
		notificationRequest.setTitle(title);
		notificationRequest.setNotificationType(NotificationType.WELCOME.getNotificationId());

		FlowContext flowContext = new FlowContext(PlatformThreadLocalDataFactory.getInstance().getRequestData());
		flowContext.setModelElement(notificationRequest);

		StepDefinition stepDefinition = stepDefinitionFactory
				.createRestfulStep(ServiceManagerRestfulEndpoint.GCM_SEND_NOTIFICATION);
		try {
			stepDefinition.execute(flowContext);
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		LOGGER.debug("Async call completed {} ", "sendWelcomeNotification");
	}

	@Override
	@Async
	public void sendRequestOpinionNotification(RequestData requestData, List<Integer> toUserIds, Integer fromUserId) {

		LOGGER.debug("Async Call {}", "sendRequestOpinionNotification");

		NotificationRequest notificationRequest = new NotificationRequest();
		
		List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll(toUserIds);
		UserEntity requestedUserEntity = userRepository.findOne(fromUserId);
		
		userEntities.stream().forEach(p -> notificationRequest.addDeviceRegId(p.getGcmRegId()));
		
		notificationRequest.setTitle(title);
		notificationRequest.setMessage("HowzThisBuddy " + opinionRequestMessage);
		notificationRequest.setNotificationType(NotificationType.REQUEST.getNotificationId());

		
		Optional.ofNullable(requestedUserEntity.getFirstname()).ifPresent(p -> notificationRequest.setMessage(p + " " + opinionRequestMessage));
		
		PlatformThreadLocalDataFactory.getInstance().setRequestData(requestData);

		FlowContext flowContext = new FlowContext(PlatformThreadLocalDataFactory.getInstance().getRequestData());

		flowContext.setModelElement(notificationRequest);

		StepDefinition stepDefinition = stepDefinitionFactory
				.createRestfulStep(ServiceManagerRestfulEndpoint.GCM_SEND_NOTIFICATION);
		try {
			stepDefinition.execute(flowContext);
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		LOGGER.debug("Async call completed {} ", "sendRequestOpinionNotification");
	}

	@Override
	@Async
	public void sendResponseOpinionNotification(RequestData requestData, OpinionResponseInput opinionResponseInput) {
		LOGGER.debug("Async Call {}", "sendResponseOpinionNotification");
		NotificationRequest notificationRequest = new NotificationRequest();
		
		UserEntity responderUserEntity = userRepository.findOne(opinionResponseInput.getUserId());
		OpinionEntity opinionEntity = opinionRepository.findOne(opinionResponseInput.getOpinionReqId());
		UserEntity requestedUserEntity = userRepository.findOne(opinionEntity.getUserId());
		
		notificationRequest.addDeviceRegId(requestedUserEntity.getGcmRegId());
		Product product = new Product();
		product.setName(opinionEntity.getProduct());
		notificationRequest.setTitle(title);
		notificationRequest.setMessage("HowzThisBuddy " + opinionResponseMessage);
		notificationRequest.setNotificationType(NotificationType.RESPONSE.getNotificationId());
		
		Optional.ofNullable(responderUserEntity.getFirstname()).ifPresent(p -> notificationRequest.setMessage(p + " " + opinionResponseMessage + " " + opinionResponseInput.getResponseCode()));


		PlatformThreadLocalDataFactory.getInstance().setRequestData(requestData);

		FlowContext flowContext = new FlowContext(PlatformThreadLocalDataFactory.getInstance().getRequestData());

		flowContext.setModelElement(notificationRequest);

		StepDefinition stepDefinition = stepDefinitionFactory
				.createRestfulStep(ServiceManagerRestfulEndpoint.GCM_SEND_NOTIFICATION);
		try {
			stepDefinition.execute(flowContext);
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		LOGGER.debug("Async call completed {} ", "sendResponseOpinionNotification");

	}

}
