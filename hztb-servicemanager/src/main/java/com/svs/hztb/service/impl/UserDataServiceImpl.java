package com.svs.hztb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.svs.hztb.api.sm.model.user.RegisteredProfileResponse;
import com.svs.hztb.api.sm.model.user.RegisteredProfilesRequest;
import com.svs.hztb.api.sm.model.user.RegisteredProfilesResponse;
import com.svs.hztb.api.sm.model.user.UpdateUserProfileRequest;
import com.svs.hztb.api.sm.model.user.UpdateUserProfileResponse;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPResponse;
import com.svs.hztb.aws.client.AWSS3ClientProcessor;
import com.svs.hztb.common.enums.NotificationType;
import com.svs.hztb.common.enums.ServiceManagerClientType;
import com.svs.hztb.common.enums.ServiceManagerStatusCode;
import com.svs.hztb.common.exception.SystemError;
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
import com.svs.hztb.orchestration.exception.BusinessError;
import com.svs.hztb.service.GCMService;
import com.svs.hztb.service.UserDataService;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;

/**
 * This class is an service implementation for user related methods
 */
@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(UserDataServiceImpl.class);

	private static final String ZERO = "0";

	private static final String NO = "N";

	private static final String YES = "Y";

	@Autowired
	private UserAdapter userAdapter;

	@Autowired
	private StepDefinitionFactory stepDefinitionFactory;

	@Autowired
	private GCMService gcmService;

	@Autowired
	private AWSS3ClientProcessor awsClientProcessor;

	@Override
	public RegistrationResponse register(RegistrationRequest registrationRequest) {
		RegistrationResponse registrationResponse = null;
		try {

			User user = new User(registrationRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<>(user);
			User findUser = userAdapter.findUserByMobileAndDeviceId(dataServiceRequest);

			user.setOtpCode(HZTBUtil.generateSixDigitNumber().toString());
			user.setOtpCreationDateTime(HZTBUtil.getUTCDate());
			user.setInvalidOtpCount(ZERO);

			if (null != findUser) {
				user.setUserId(findUser.getUserId());
				user.setRegistered(NO);
				dataServiceRequest.setPayload(user);
				user = userAdapter.updateUserDetails(dataServiceRequest);
			} else {
				dataServiceRequest.setPayload(user);
				user = userAdapter.createUser(dataServiceRequest);
			}

			if (user.getMobileNumber().startsWith("1")) {
				FlowContext flowContext = new FlowContext(
						PlatformThreadLocalDataFactory.getInstance().getRequestData());
				flowContext.setModelElement(user);

				StepDefinition stepDefinition = stepDefinitionFactory
						.createRestfulStep(ServiceManagerRestfulEndpoint.CLICKATELL);
				stepDefinition.execute(flowContext);

				ClickatellResponse clickatellResponse = flowContext.getModelElement(ClickatellResponse.class);
				LOGGER.debug("Clickatell Response {} ",
						clickatellResponse.getData().getMessage().get(0).getApiMessageId());
			}

			registrationResponse = populateRegistrationUserResponse(user);
		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Error occured during register : {}", dataServiceException);
			throw BusinessError.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessError businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
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
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<>(user);
			userAdapter.ping(dataServiceRequest);
			pingResponse = populatePingResponse();
		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Error occured during ping : {}", dataServiceException);
			throw BusinessError.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return pingResponse;
	}

	private PingResponse populatePingResponse() {
		return new PingResponse();
	}

	@Override
	public ValidateOTPResponse validateOTP(ValidateOTPRequest validateOTPRequest) {
		ValidateOTPResponse validateOTPResponse = null;
		Boolean isOTPValiationSuccesful = false;

		try {
			User user = new User(validateOTPRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<>(user);
			User userFromDB = userAdapter.getUserByMobileAndDeviceId(dataServiceRequest);
			Boolean isOTPValidationAllowed = HZTBUtil.isOtpValidationAllowed(userFromDB.getOtpCreationDateTime(),
					userFromDB.getInvalidOtpCount());
			User updatedUser;
			if (isOTPValidationAllowed) {
				if (validateOTPRequest.getOtpCode().equals(userFromDB.getOtpCode())) {
					updatedUser = updateUserDetails(dataServiceRequest, userFromDB, user);
					isOTPValiationSuccesful = true;
				} else {
					// OTP incorrect scenario
					updatedUser = new User();
					updatedUser.setUserId(userFromDB.getUserId());
					Integer invalidOtpEntries = Integer.parseInt(userFromDB.getInvalidOtpCount());
					invalidOtpEntries = invalidOtpEntries + 1;
					updatedUser.setInvalidOtpCount(invalidOtpEntries.toString());
					dataServiceRequest = new DataServiceRequest<>(updatedUser);
					userAdapter.updateUserDetails(dataServiceRequest);
					isOTPValiationSuccesful = false;
				}
			} else {
				throw new BusinessError(ServiceManagerStatusCode.OTP_NOT_VALID.getMessage(),
						ServiceManagerStatusCode.OTP_NOT_VALID);
			}
			validateOTPResponse = populateValidateOTPResponse(updatedUser, isOTPValiationSuccesful);
		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Error occured during validateOTP : {}", dataServiceException);
			throw BusinessError.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessError businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return validateOTPResponse;
	}

	private ValidateOTPResponse populateValidateOTPResponse(User user, Boolean isOTPValiationSuccesful) {
		ValidateOTPResponse validateOTPResponse = new ValidateOTPResponse();
		validateOTPResponse.setIsValidateOTPSuccesful(isOTPValiationSuccesful);
		validateOTPResponse.setUserId(user.getUserId());
		validateOTPResponse.setIsUserAlreadyRegistered(user.getRegisteredAlready().equals(YES) ? true : false);
		return validateOTPResponse;
	}

	@Override
	public UserProfileResponse getUserProfile(UserProfileRequest userProfileRequest) {
		UserProfileResponse userProfileResponse = null;
		try {
			User user = new User(userProfileRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<>(user);
			user = userAdapter.getUserDetails(dataServiceRequest);
			userProfileResponse = populateUserResponse(user);
		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Error occured during getUserProfile : {}", dataServiceException);
			throw BusinessError.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessError businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return userProfileResponse;
	}

	private RegisteredProfileResponse populateRegisteredProfilesResponse(User user) {
		RegisteredProfileResponse registeredProfileResponse = new RegisteredProfileResponse();
		Optional.ofNullable(user.getMobileNumber()).ifPresent(registeredProfileResponse::setMobileNumber);
		Optional.ofNullable(user.getName()).ifPresent(registeredProfileResponse::setName);
		Optional.ofNullable(user.getUserId()).ifPresent(registeredProfileResponse::setUserId);

		Optional.ofNullable(user.getProfilePicUrl()).ifPresent(registeredProfileResponse::setProfilePictureURL);
		return registeredProfileResponse;
	}

	private UserProfileResponse populateUserResponse(User user) {
		UserProfileResponse userProfileResponse = new UserProfileResponse();
		Optional.ofNullable(user.getEmailAddress()).ifPresent(userProfileResponse::setEmailAddress);
		Optional.ofNullable(user.getName()).ifPresent(userProfileResponse::setName);
		Optional.ofNullable(user.getUserId()).ifPresent(userProfileResponse::setUserId);

		Optional.ofNullable(user.getProfilePicUrl()).ifPresent(userProfileResponse::setProfilePictureURL);
		return userProfileResponse;
	}

	private UpdateUserProfileResponse populateUpdateUserResponse(User user) {
		UpdateUserProfileResponse updateUserProfileResponse = new UpdateUserProfileResponse();
		Optional.ofNullable(user.getProfilePicUrl()).ifPresent(updateUserProfileResponse::setProfilePictureURL);
		return updateUserProfileResponse;
	}

	@Override
	public UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest updateUserProfileRequest) {
		UpdateUserProfileResponse updateUserProfileResponse = null;
		try {
			User user = new User(updateUserProfileRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<>(user);
			User findUser = userAdapter.getUserDetails(dataServiceRequest);
			user.setUserId(findUser.getUserId());

			Optional.ofNullable(updateUserProfileRequest.getProfilePic()).ifPresent(user::setProfilePic);

			String profilePicfileName;
			String profilePicUrl;
			if (null != user.getProfilePic()) {
				Map<String, String> map = awsClientProcessor.prepareFileName(
						NotificationType.PROFILE.getNotificationId(), user.getUserId().toString(),
						PlatformThreadLocalDataFactory.getInstance().getRequestData().getRequestId());
				profilePicfileName = map.get("FILENAME");
				profilePicUrl = map.get("URL");
				awsClientProcessor.putObject(user.getProfilePic(), profilePicfileName,
						PlatformThreadLocalDataFactory.getInstance().getRequestData().getRequestId());

				user.setProfilePicUrl(profilePicUrl);
				user.setProfilePicVersion((Integer.parseInt(findUser.getProfilePicVersion()) + 1) + "");
			}

			dataServiceRequest.setPayload(user);
			user = userAdapter.updateUserDetails(dataServiceRequest);
			updateUserProfileResponse = populateUpdateUserResponse(user);

		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Error occured during updateUserProfile : {}", dataServiceException);
			throw BusinessError.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessError businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return updateUserProfileResponse;
	}

	@Override
	public RegisteredProfilesResponse registeredUsers(RegisteredProfilesRequest registeredProfilesRequest) {
		List<User> usersList = null;
		RegisteredProfilesResponse registeredProfilesResponse = null;

		try {
			List<String> mobileNumbers = new ArrayList<>();
			registeredProfilesRequest.getRegisteredProfileRequests().stream()
					.forEach(p -> mobileNumbers.add(p.getMobileNumber()));

			DataServiceRequest<List<String>> dataServiceRequest = new DataServiceRequest<>(mobileNumbers);
			usersList = userAdapter.registeredUsers(dataServiceRequest);
			registeredProfilesResponse = populateUserResponses(usersList);

		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Error occured during registeredUser : {}", dataServiceException);
			throw BusinessError.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (BusinessError businessException) {
			throw businessException;
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		return registeredProfilesResponse;
	}

	private RegisteredProfilesResponse populateUserResponses(List<User> usersList) {
		RegisteredProfilesResponse registeredProfilesResponse = new RegisteredProfilesResponse();
		usersList.stream().forEach(
				p -> registeredProfilesResponse.addRegisteredProfileResponse(populateRegisteredProfilesResponse(p)));
		return registeredProfilesResponse;
	}

	private User updateUserDetails(DataServiceRequest<User> dataServiceRequest, User userFromDB, User userFromRequest)
			throws DataServiceException {

		dataServiceRequest.setPayload(userFromDB);

		User oldUserFromDB = userAdapter.findByMobileNumberAndRegisteredAndNotUserId(dataServiceRequest);
		User updatedUser;

		if (null != oldUserFromDB) {
			oldUserFromDB.setDeviceId(userFromDB.getDeviceId());
			oldUserFromDB.setDeviceRegId(userFromRequest.getDeviceRegId());
			oldUserFromDB.setInvalidOtpCount(userFromDB.getInvalidOtpCount());
			oldUserFromDB.setOtpCode(userFromDB.getOtpCode());
			oldUserFromDB.setOtpCreationDateTime(userFromDB.getOtpCreationDateTime());
			oldUserFromDB.setRegistered(YES);
			dataServiceRequest.setPayload(oldUserFromDB);
			updatedUser = userAdapter.updateUserDetails(dataServiceRequest);
			dataServiceRequest.setPayload(userFromDB);
			userAdapter.deleteUserByUserId(dataServiceRequest);
		} else {

			userFromDB.setDeviceRegId(userFromRequest.getDeviceRegId());
			userFromDB.setRegistered(YES);
			String userAlreadyRegistered = userFromDB.getRegisteredAlready();
			userFromDB.setRegisteredAlready(YES);
			updatedUser = userAdapter.updateUserDetails(dataServiceRequest);
			updatedUser.setRegisteredAlready(userAlreadyRegistered.equals(YES) ? YES : NO);
		}

		gcmService.sendWelcomeNotification(PlatformThreadLocalDataFactory.getInstance().getRequestData(), userFromDB);
		return updatedUser;

	}

}
