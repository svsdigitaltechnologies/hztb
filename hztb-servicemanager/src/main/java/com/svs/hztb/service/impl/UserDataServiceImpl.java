package com.svs.hztb.service.impl;

import java.text.MessageFormat;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.svs.hztb.adapter.UserAdapter;
import com.svs.hztb.api.common.utils.GCMMessageNotificationClient;
import com.svs.hztb.api.common.utils.HZTBUtil;
import com.svs.hztb.api.sm.model.clickatell.ClickatellRequest;
import com.svs.hztb.api.sm.model.clickatell.ClickatellResponse;
import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.ping.PingResponse;
import com.svs.hztb.api.sm.model.registration.RegistrationRequest;
import com.svs.hztb.api.sm.model.registration.RegistrationResponse;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPResponse;
import com.svs.hztb.common.enums.ServiceManagerClientType;
import com.svs.hztb.common.enums.ServiceManagerStatusCode;
import com.svs.hztb.common.exception.SystemException;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.business.User;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.exception.BusinessException;
import com.svs.hztb.exception.DataServiceException;
import com.svs.hztb.service.UserDataService;

@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {

	@Autowired
	private UserAdapter userAdapter;

	private final static Logger logger = LoggerFactory.getLogger(UserDataServiceImpl.class);

	@Override
	public RegistrationResponse register(RegistrationRequest registrationRequest) {
		RegistrationResponse registrationResponse = null;
		try {
			Long otpCode = HZTBUtil.generateSixDigitNumber();
			String utcDateTime = HZTBUtil.getUTCDate();
			User user = new User(registrationRequest);
			user.setOtpCode(otpCode.toString());
			user.setOtpCreationDateTime(utcDateTime);

			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<User>(user);
			User findUser = userAdapter.findUser(dataServiceRequest);
			if (null != findUser) {
				user.setUserId(findUser.getUserId());
				dataServiceRequest = new DataServiceRequest<User>(user);
				user = userAdapter.updateUserDetails(dataServiceRequest);
			} else {
				user = userAdapter.createUser(dataServiceRequest);
			}

			RestTemplate restTemplate = new RestTemplate();
			ClickatellRequest clickatellRequest = new ClickatellRequest();
			clickatellRequest.setTo(user.getMobileNumber());
			clickatellRequest.setText(user.getOtpCode());

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			requestHeaders.add("Accept", "application/json;charset=utf-8");
			requestHeaders.add("X-Version", "1");
			requestHeaders.set("Authorization",
					"bearer VAHTu2T5yNlcyXKBDV6PdXsZuhQACZWfSLViD9Q7emmkkxbpXUfW3Tu8dUi_Y_HiP_o");

			/*
			 * HttpEntity requestEntity = new HttpEntity(clickatellRequest,
			 * requestHeaders);
			 * 
			 * ResponseEntity<ClickatellResponse> clickatellResponse =
			 * restTemplate.exchange( "https://api.clickatell.com/rest/message",
			 * HttpMethod.POST, requestEntity, ClickatellResponse.class);
			 */

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<ClickatellResponse> clickatellResponse = restTemplate.exchange(
					"http://localhost:8082/user/jsonResponse", HttpMethod.GET, requestEntity, ClickatellResponse.class);
			System.out.println("Clickatell to : " + clickatellResponse.getBody().getData().getMessage().get(0).getTo());

			registrationResponse = populateRegistrationUserResponse(user);
		} catch (DataServiceException e) {
			throw BusinessException.build(ServiceManagerClientType.DATASERVICES, e.getMessage(), e.getStatusCode());
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e, PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
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
			throw BusinessException.build(ServiceManagerClientType.DATASERVICES, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
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
			Boolean isOTPValidationAllowed = HZTBUtil.isOtpValidationAllowed(userFromDB.getOtpCreationDateTime());

			if (isOTPValidationAllowed) {
				if (validateOTPRequest.getOtpCode().equals(userFromDB.getOtpCode())) {
					dataServiceRequest.getPayload().setUserId(userFromDB.getUserId());
					userFromDB = userAdapter.updateUserDetails(dataServiceRequest);
					
					GCMMessageNotificationClient client=new GCMMessageNotificationClient();
					
					String formattedMessage = "Welcome to hztb";
					client.processPushNotification(userFromDB.getDeviceRegId(), formattedMessage);
					
					isOTPValiationSuccesful = true;
				} else {
					// update the code to increment the otp attempt failed
					// count.
					User updateUser = new User();
					updateUser.setUserId(userFromDB.getUserId());
					dataServiceRequest = new DataServiceRequest<User>(updateUser);
					userFromDB = userAdapter.updateUserDetails(dataServiceRequest);
				}
			} else {
				throw new BusinessException(ServiceManagerStatusCode.EXPIRED_OTP.getMessage(),
						ServiceManagerStatusCode.EXPIRED_OTP);
			}
			validateOTPResponse = populateValidateOTPResponse(user, isOTPValiationSuccesful);
		} catch (DataServiceException dataServiceException) {
			throw BusinessException.build(ServiceManagerClientType.DATASERVICES, dataServiceException.getMessage(),
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
			throw BusinessException.build(ServiceManagerClientType.DATASERVICES, dataServiceException.getMessage(),
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
			dataServiceRequest = new DataServiceRequest<User>(user);
			user = userAdapter.updateUserDetails(dataServiceRequest);
			userProfileResponse = populateUserResponse(user);
		} catch (DataServiceException dataServiceException) {
			throw BusinessException.build(ServiceManagerClientType.DATASERVICES, dataServiceException.getMessage(),
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
