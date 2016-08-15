package com.svs.hztb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svs.hztb.adapter.OneTimePasswordAdapter;
import com.svs.hztb.adapter.UserAdapter;
import com.svs.hztb.api.common.utils.HZTBUtil;
import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.ping.PingResponse;
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
import com.svs.hztb.common.model.ErrorStatus;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.business.OneTimePassword;
import com.svs.hztb.common.model.business.User;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.exception.DataServiceException;
import com.svs.hztb.orchestration.exception.BusinessError;
import com.svs.hztb.service.BaseService;
import com.svs.hztb.service.GCMService;
import com.svs.hztb.service.UserDataService;

/**
 * This class is an service implementation for user related methods
 */
@Service
@Transactional
public class UserDataServiceImpl extends BaseService implements UserDataService {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(UserDataServiceImpl.class);

	private static final String NO = "N";

	private static final String YES = "Y";

	@Autowired
	private UserAdapter userAdapter;

	@Autowired
	private OneTimePasswordAdapter oneTimePasswordAdapter;

	@Autowired
	private GCMService gcmService;

	@Autowired
	private AWSS3ClientProcessor awsClientProcessor;

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

		try {
			OneTimePassword oneTimePassword = new OneTimePassword(validateOTPRequest);
			DataServiceRequest<OneTimePassword> dataServiceRequest = new DataServiceRequest<>(oneTimePassword);
			oneTimePassword = oneTimePasswordAdapter.findOTPbyPhoneAndUniqueIdAndIdentity(dataServiceRequest);

			List<ErrorStatus> errors = checkOneTimePassword(oneTimePassword);

			if (!errors.isEmpty()) {
				validateOTPResponse = new ValidateOTPResponse();
				return (ValidateOTPResponse) buildErrorResponse(errors, validateOTPResponse);
			}

			return validateOTP(validateOTPRequest, oneTimePassword);

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
	}

	private ValidateOTPResponse populateValidateOTPResponse(User user) {
		ValidateOTPResponse validateOTPResponse = new ValidateOTPResponse();
		validateOTPResponse.setUserId(user.getUserId());
		Optional.ofNullable(user.getRegistered())
				.ifPresent(p -> validateOTPResponse.setIsUserAlreadyRegistered(p.equals(YES) ? true : false));
		Optional.ofNullable(user.getPw()).ifPresent(validateOTPResponse::setPw);
		return (ValidateOTPResponse) buildSuccessResponse(validateOTPResponse);

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

	private User createOrUpdateUser(ValidateOTPRequest validateOTPRequest) throws DataServiceException {
		User user = new User(validateOTPRequest);
		User updatedUser;
		DataServiceRequest<User> userDataServiceRequest = new DataServiceRequest<>(user);
		User findUser = userAdapter.findByPhoneNumber(userDataServiceRequest);
		user.setPw(HZTBUtil.generatePw());
		if (null == findUser) {
			user.setRegistered(YES);
			userDataServiceRequest.setPayload(user);
			updatedUser = userAdapter.createUser(userDataServiceRequest);
			updatedUser.setRegistered(NO);
		} else {
			user.setUserId(findUser.getUserId());
			userDataServiceRequest.setPayload(user);
			updatedUser = userAdapter.updateUserDetails(userDataServiceRequest);
		}
		gcmService.sendWelcomeNotification(PlatformThreadLocalDataFactory.getInstance().getRequestData(), user);
		return updatedUser;
	}

	private List<ErrorStatus> checkOneTimePassword(OneTimePassword oneTimePassword) {
		List<ErrorStatus> errors = new ArrayList<>();

		if (null == oneTimePassword) {
			ErrorStatus error = new ErrorStatus(ServiceManagerStatusCode.USER_NOT_AVAILABLE.getStatusCode(),
					ServiceManagerStatusCode.USER_NOT_AVAILABLE.getMessage());
			errors.add(error);
			return errors;
		}
		return checkOTPValidationAllowed(oneTimePassword);
	}

	private List<ErrorStatus> checkOTPValidationAllowed(OneTimePassword oneTimePassword) {
		Boolean isOTPValidationAllowed = HZTBUtil.isOtpValidationAllowed(oneTimePassword.getOtpCreationDateTime(),
				oneTimePassword.getInvalidOtpCount());
		List<ErrorStatus> errors = new ArrayList<>();

		if (!isOTPValidationAllowed) {
			ErrorStatus error = new ErrorStatus(ServiceManagerStatusCode.OTP_NOT_VALID.getStatusCode(),
					ServiceManagerStatusCode.OTP_NOT_VALID.getMessage());
			errors.add(error);

		}
		return errors;

	}

	private List<ErrorStatus> otpIsInvalid() {
		List<ErrorStatus> errors = new ArrayList<>();
		ErrorStatus error = new ErrorStatus(ServiceManagerStatusCode.INVALID_OTP.getStatusCode(),
				ServiceManagerStatusCode.INVALID_OTP.getMessage());
		errors.add(error);
		return errors;

	}

	private ValidateOTPResponse validateOTP(ValidateOTPRequest validateOTPRequest, OneTimePassword oneTimePassword)
			throws DataServiceException {
		DataServiceRequest<OneTimePassword> dataServiceRequest = new DataServiceRequest<>(oneTimePassword);

		ValidateOTPResponse validateOTPResponse;
		if (validateOTPRequest.getOtpCode().equals(oneTimePassword.getOtpCode())) {
			User updatedUser = createOrUpdateUser(validateOTPRequest);
			oneTimePasswordAdapter.deleteOTPCode(dataServiceRequest);
			validateOTPResponse = populateValidateOTPResponse(updatedUser);
			return validateOTPResponse;
		} else {
			oneTimePassword.setInvalidOtpCount(oneTimePassword.getInvalidOtpCount() + 1);
			oneTimePasswordAdapter.updateOTPCode(dataServiceRequest);
			List<ErrorStatus> errors = otpIsInvalid();
			validateOTPResponse = new ValidateOTPResponse();
			validateOTPResponse = (ValidateOTPResponse) buildErrorResponse(errors, validateOTPResponse);
			validateOTPResponse.setOtpWaitTime((oneTimePassword.getInvalidOtpCount() * (60 / 2)) + "");
			return validateOTPResponse;
		}
	}
}
