package com.svs.hztb.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svs.hztb.adapter.UserAdapter;
import com.svs.hztb.api.common.utils.HZTBUtil;
import com.svs.hztb.api.sm.model.clickatell.ClickatellResponse;
import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.ping.PingResponse;
import com.svs.hztb.api.sm.model.registration.RegistrationRequest;
import com.svs.hztb.api.sm.model.registration.RegistrationResponse;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPResponse;
import com.svs.hztb.aws.client.AWSClientProcessor;
import com.svs.hztb.common.enums.NotificationType;
import com.svs.hztb.common.enums.ServiceManagerClientType;
import com.svs.hztb.common.enums.ServiceManagerStatusCode;
import com.svs.hztb.common.exception.SystemException;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.business.User;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.exception.DataServiceException;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.orchestration.component.step.StepDefinition;
import com.svs.hztb.orchestration.component.step.StepDefinitionFactory;
import com.svs.hztb.orchestration.exception.BusinessException;
import com.svs.hztb.service.GCMService;
import com.svs.hztb.service.UserDataService;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;

@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(UserDataServiceImpl.class);

	private final String ZERO = "0";

	@Autowired
	private UserAdapter userAdapter;
 
	@Autowired
	private StepDefinitionFactory stepDefinitionFactory;
	
	@Autowired
	private GCMService gcmService;
	
	@Autowired
	private AWSClientProcessor awsClientProcessor;

	@Override
	public RegistrationResponse register(RegistrationRequest registrationRequest) {
		RegistrationResponse registrationResponse = null;
		try {
			Long otpCode = HZTBUtil.generateSixDigitNumber();
			String utcDateTime = HZTBUtil.getUTCDate();

			User user = new User(registrationRequest);
			user.setOtpCode(otpCode.toString());
			user.setOtpCreationDateTime(utcDateTime);
			user.setInvalidOtpCount(ZERO);


			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<User>(user);
			User findUser = userAdapter.findUser(dataServiceRequest);

			if (null != findUser) {
				user.setUserId(findUser.getUserId());
				dataServiceRequest = new DataServiceRequest<User>(user);
				user = userAdapter.updateUserDetails(dataServiceRequest);
			} else {
				user = userAdapter.createUser(dataServiceRequest);
			}
			
			
			FlowContext flowContext = new FlowContext(PlatformThreadLocalDataFactory.getInstance().getRequestData());
			flowContext.setModelElement(user);
			
			StepDefinition stepDefinition = stepDefinitionFactory
					.createRestfulStep(ServiceManagerRestfulEndpoint.CLICKATELL);
			stepDefinition.execute(flowContext);

			ClickatellResponse clickatellResponse = flowContext.getModelElement(ClickatellResponse.class);
			LOGGER.debug("Clickatell Response {} ", clickatellResponse.getData().getMessage().get(0).getApiMessageId());

			registrationResponse = populateRegistrationUserResponse(user);
		} catch (DataServiceException dataServiceException) { 
			throw BusinessException.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessException businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemException(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return registrationResponse;
	}

	private RegistrationResponse populateRegistrationUserResponse(User user) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setMobileNumber(user.getMobileNumber());
		return registrationResponse;
	}

	@Override
	public PingResponse ping(PingRequest pingRequest) {

		PingResponse pingResponse = null;
		try {
			User user = new User(pingRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<User>(user);
			user = userAdapter.ping(dataServiceRequest);
			pingResponse = populatePingResponse(user);
		} catch (DataServiceException dataServiceException) {
			BusinessException businessException = BusinessException.build(ServiceManagerClientType.DS,
					dataServiceException.getMessage(), dataServiceException.getStatusCode());
			throw businessException;
		} catch (Exception exception) {
			throw new SystemException(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return pingResponse;
	}

	private PingResponse populatePingResponse(User user) {
		PingResponse pingResponse = new PingResponse();
		pingResponse.setMobileNumber(user.getMobileNumber());
		return pingResponse;
	}

	@Override
	public ValidateOTPResponse validateOTP(ValidateOTPRequest validateOTPRequest) {
		ValidateOTPResponse validateOTPResponse = null;
		Boolean isOTPValiationSuccesful = false;
		
		try {
			User user = new User(validateOTPRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<User>(user);
			User userFromDB = userAdapter.getUserDetails(dataServiceRequest);
			Boolean isOTPValidationAllowed = HZTBUtil.isOtpValidationAllowed(userFromDB.getOtpCreationDateTime(),
					userFromDB.getInvalidOtpCount());

			if (isOTPValidationAllowed) {
				if (validateOTPRequest.getOtpCode().equals(userFromDB.getOtpCode())) {
					dataServiceRequest.getPayload().setUserId(userFromDB.getUserId());
					
					//updating IMEI, device registration id
					userFromDB = userAdapter.updateUserDetails(dataServiceRequest);
					
					gcmService.sendWelcomeNotification(PlatformThreadLocalDataFactory.getInstance().getRequestData(), userFromDB);
					/*GCMMessageNotificationClient client = new GCMMessageNotificationClient();
					String formattedMessage = "Welcome to hztb";
					client.processPushNotification(userFromDB.getDeviceRegId(), formattedMessage);*/
					isOTPValiationSuccesful = true;
				} else {
					//OTP incorrect scenario
					User updateUser = new User();
					updateUser.setUserId(userFromDB.getUserId());
					Integer invalidOtpEntries = Integer.parseInt(userFromDB.getInvalidOtpCount());
					invalidOtpEntries = invalidOtpEntries + 1;
					updateUser.setInvalidOtpCount(invalidOtpEntries.toString());
					dataServiceRequest = new DataServiceRequest<User>(updateUser);
					userFromDB = userAdapter.updateUserDetails(dataServiceRequest);
				}
			} else {
				throw new BusinessException(ServiceManagerStatusCode.OTP_NOT_VALID.getMessage(),
						ServiceManagerStatusCode.OTP_NOT_VALID);
			}
			validateOTPResponse = populateValidateOTPResponse(user, isOTPValiationSuccesful);
		} catch (DataServiceException dataServiceException) {
			throw BusinessException.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessException businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemException(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return validateOTPResponse;
	}

	private ValidateOTPResponse populateValidateOTPResponse(User user, Boolean isOTPValiationSuccesful) {
		ValidateOTPResponse validateOTPResponse = new ValidateOTPResponse();
		validateOTPResponse.setMobileNumber(user.getMobileNumber());
		validateOTPResponse.setIsValidateOTPSuccesful(isOTPValiationSuccesful);
		return validateOTPResponse;
	}

	@Override
	public UserProfileResponse getUserProfile(UserProfileRequest userProfileRequest) {
		UserProfileResponse userProfileResponse = null;
		try {
			User user = new User(userProfileRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<User>(user);
			user = userAdapter.getUserDetails(dataServiceRequest);
			if (null == user) {
				throw new BusinessException(ServiceManagerStatusCode.USER_NOT_AVAILABLE.getMessage(),
						ServiceManagerStatusCode.USER_NOT_AVAILABLE);
			}
			userProfileResponse = populateUserResponse(user);
		} catch (DataServiceException dataServiceException) {
			throw BusinessException.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessException businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemException(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return userProfileResponse;
	}

	private UserProfileResponse populateUserResponse(User user) {
		UserProfileResponse userProfileResponse = new UserProfileResponse();
		Optional.ofNullable(user.getMobileNumber()).ifPresent(p -> userProfileResponse.setMobileNumber(p));
		Optional.ofNullable(user.getEmailAddress()).ifPresent(p -> userProfileResponse.setEmailAddress(p));
		Optional.ofNullable(user.getName()).ifPresent(p -> userProfileResponse.setName(p));
		Optional.ofNullable(user.getDeviceRegId()).ifPresent(p -> userProfileResponse.setDeviceRegId(p));
		Optional.ofNullable(user.getImei()).ifPresent(p -> userProfileResponse.setImei(p));
		Optional.ofNullable(user.getOtpCode()).ifPresent(p -> userProfileResponse.setOtpCode(p));
		Optional.ofNullable(user.getOtpCreationDateTime())
				.ifPresent(p -> userProfileResponse.setOtpCreationDateTime(p));
		return userProfileResponse;
	}

	@Override
	public UserProfileResponse updateUserProfile(UserProfileRequest userProfileRequest) {
		UserProfileResponse userProfileResponse = null;
		try {
			User user = new User(userProfileRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<User>(user);
			User findUser = userAdapter.getUserDetails(dataServiceRequest);
			user.setUserId(findUser.getUserId());
			//skairamkonda remove this code later
			File fnew=new File("D:/Users/skairamk/personal/photos/family.jpg");
			BufferedImage originalImage=ImageIO.read(fnew);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos );
			byte[] imageInByte=baos.toByteArray();
			
			user.setProfilePic(imageInByte);
			//skairamkonda use optional here
			
			if(null != user.getProfilePic())
				user.setProfilePicUrl(awsClientProcessor.execute(user.getProfilePic(), NotificationType.PROFILE.getNotificationId(), user.getUserId()));
			
			dataServiceRequest = new DataServiceRequest<User>(user);
			user = userAdapter.updateUserDetails(dataServiceRequest);
			userProfileResponse = populateUserResponse(user);
			
			
		} catch (DataServiceException dataServiceException) {
			throw BusinessException.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessException businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemException(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return userProfileResponse;
	}

}
