package com.svs.hztb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svs.hztb.adapter.OneTimePasswordAdapter;
import com.svs.hztb.api.common.utils.HZTBUtil;
import com.svs.hztb.api.sm.model.clickatell.ClickatellResponse;
import com.svs.hztb.api.sm.model.registration.GetOTPResponse;
import com.svs.hztb.api.sm.model.registration.OneTimePasswordRequest;
import com.svs.hztb.api.sm.model.registration.OneTimePasswordResponse;
import com.svs.hztb.common.enums.ServiceManagerClientType;
import com.svs.hztb.common.exception.SystemError;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.PlatformThreadLocalDataFactory;
import com.svs.hztb.common.model.business.OneTimePassword;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.exception.DataServiceException;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.orchestration.component.step.StepDefinition;
import com.svs.hztb.orchestration.component.step.StepDefinitionFactory;
import com.svs.hztb.orchestration.exception.BusinessError;
import com.svs.hztb.service.BaseService;
import com.svs.hztb.service.OneTimePasswordDataService;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;
import com.svs.hztb.sm.common.enums.SmsWaitTime;

/**
 * This class is an service implementation for user related methods
 */
@Service
@Transactional
public class OneTimePasswordDataServiceImpl extends BaseService implements OneTimePasswordDataService {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(OneTimePasswordDataServiceImpl.class);

	private static final Integer ZERO = 0;

	private static final String THREE = "3";

	@Autowired
	private OneTimePasswordAdapter oneTimePasswordAdapter;

	@Autowired
	private StepDefinitionFactory stepDefinitionFactory;

	@Override
	public OneTimePasswordResponse requestOTPCode(OneTimePasswordRequest oneTimePasswordRequest) {
		try {
			OneTimePassword oneTimePassword = new OneTimePassword(oneTimePasswordRequest);
			DataServiceRequest<OneTimePassword> dataServiceRequest = new DataServiceRequest<>(oneTimePassword);
			OneTimePassword findOneTimePassword = oneTimePasswordAdapter.findOTPbyPhoneNumber(dataServiceRequest);

			// if pn, unique id doesn't exist's, then create a new record and
			// send otp to phone number
			if (null == findOneTimePassword) {
				dataServiceRequest = prepareDataServiceRequest(dataServiceRequest, oneTimePassword,
						SmsWaitTime.SMS_SENT_1);
				oneTimePassword = oneTimePasswordAdapter.createOTPCode(dataServiceRequest);
				oneTimePassword.setSmsWaitTime(SmsWaitTime.SMS_SENT_1.getWaitTime().toString());
				oneTimePassword.setVoiceWaitTime(SmsWaitTime.SMS_SENT_4.getWaitTime().toString());
				oneTimePassword.setOtpWaitTime(THREE);
				sendOTPToMobileNumber(oneTimePassword);
				return populateOneTimePasswordResponse(oneTimePassword);
			}

			findOneTimePassword.setIdentity(oneTimePassword.getIdentity());
			// if pn, unique id exists, check sms sent count
			oneTimePassword = updateOTPCode(dataServiceRequest, findOneTimePassword);
			oneTimePassword.setVoiceWaitTime(SmsWaitTime.SMS_SENT_4.getWaitTime().toString());
			oneTimePassword.setOtpWaitTime(THREE);
			return populateOneTimePasswordResponse(oneTimePassword);
		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Error occured during requestCode : {}", dataServiceException);
			throw BusinessError.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
	}

	private void sendOTPToMobileNumber(OneTimePassword oneTimePassword) throws Exception {
		if (oneTimePassword.getMobileNumber().startsWith("1")) {
			FlowContext flowContext = new FlowContext(PlatformThreadLocalDataFactory.getInstance().getRequestData());
			flowContext.setModelElement(oneTimePassword);

			StepDefinition stepDefinition = stepDefinitionFactory
					.createRestfulStep(ServiceManagerRestfulEndpoint.CLICKATELL);
			stepDefinition.execute(flowContext);

			ClickatellResponse clickatellResponse = flowContext.getModelElement(ClickatellResponse.class);
			LOGGER.debug("Clickatell Response {} ", clickatellResponse.getData().getMessage().get(0).getApiMessageId());
			/*
			 * if (null !=
			 * clickatellResponse.getData().getMessage().get(0).getApiMessageId(
			 * )) { oneTimePassword.setOtpStatus("SENT"); } else {
			 * oneTimePassword.setOtpStatus("NOT SENT"); }
			 */
		}
	}

	private OneTimePasswordResponse populateOneTimePasswordResponse(OneTimePassword oneTimePassword) {
		OneTimePasswordResponse oneTimePasswordResponse = new OneTimePasswordResponse();
		Optional.ofNullable(oneTimePassword.getSmsWaitTime()).ifPresent(oneTimePasswordResponse::setSmsWaitTime);
		Optional.ofNullable(oneTimePassword.getVoiceWaitTime()).ifPresent(oneTimePasswordResponse::setVoiceWaitTime);
		Optional.ofNullable(oneTimePassword.getOtpWaitTime()).ifPresent(oneTimePasswordResponse::setOtpWaitTime);

		return (OneTimePasswordResponse) buildSuccessResponse(oneTimePasswordResponse);

		/*
		 * Optional.ofNullable(oneTimePassword.getOtpStatus()).ifPresent(
		 * oneTimePasswordResponse::setStatus);
		 */
	}

	private DataServiceRequest<OneTimePassword> prepareDataServiceRequest(
			DataServiceRequest<OneTimePassword> dataServiceRequest, OneTimePassword oneTimePassword,
			SmsWaitTime smsWaitTime) {
		oneTimePassword.setInvalidOtpCount(ZERO);
		oneTimePassword.setOtpCode(HZTBUtil.generateSixDigitNumber().toString());
		oneTimePassword.setOtpCreationDateTime(HZTBUtil.getUTCDate());
		oneTimePassword.setSmsSentCount(smsWaitTime.getSentCount());
		dataServiceRequest.setPayload(oneTimePassword);
		return dataServiceRequest;
	}

	private OneTimePassword updateOTPCode(DataServiceRequest<OneTimePassword> dataServiceRequest,
			OneTimePassword findOneTimePassword) throws Exception {
		OneTimePassword localOneTimePassword = null;

		if (findOneTimePassword.getSmsSentCount().intValue() == SmsWaitTime.SMS_SENT_1.getSentCount()) {
			localOneTimePassword = checkSmsCountAndWaitTime(dataServiceRequest, findOneTimePassword,
					SmsWaitTime.SMS_SENT_1, SmsWaitTime.SMS_SENT_2);
			if (localOneTimePassword.getSmsWaitTime() == null) {
				sendOTPToMobileNumber(localOneTimePassword);
				localOneTimePassword.setSmsWaitTime(SmsWaitTime.SMS_SENT_2.getWaitTime().toString());
			}
		} else if (findOneTimePassword.getSmsSentCount().intValue() == SmsWaitTime.SMS_SENT_2.getSentCount()) {
			localOneTimePassword = checkSmsCountAndWaitTime(dataServiceRequest, findOneTimePassword,
					SmsWaitTime.SMS_SENT_2, SmsWaitTime.SMS_SENT_3);
			if (localOneTimePassword.getSmsWaitTime() == null) {
				sendOTPToMobileNumber(localOneTimePassword);
				localOneTimePassword.setSmsWaitTime(SmsWaitTime.SMS_SENT_3.getWaitTime().toString());
			}

		} else if (findOneTimePassword.getSmsSentCount().intValue() == SmsWaitTime.SMS_SENT_3.getSentCount()) {
			localOneTimePassword = checkSmsCountAndWaitTime(dataServiceRequest, findOneTimePassword,
					SmsWaitTime.SMS_SENT_3, SmsWaitTime.SMS_SENT_4);
			if (localOneTimePassword.getSmsWaitTime() == null) {
				sendOTPToMobileNumber(localOneTimePassword);
				localOneTimePassword.setSmsWaitTime(SmsWaitTime.SMS_SENT_4.getWaitTime().toString());
			}
		} else if (findOneTimePassword.getSmsSentCount().intValue() == SmsWaitTime.SMS_SENT_4.getSentCount()) {
			localOneTimePassword = checkSmsCountAndWaitTime(dataServiceRequest, findOneTimePassword,
					SmsWaitTime.SMS_SENT_4, SmsWaitTime.SMS_SENT_1);
			if (localOneTimePassword.getSmsWaitTime() == null) {
				sendOTPToMobileNumber(localOneTimePassword);
				localOneTimePassword.setSmsWaitTime(SmsWaitTime.SMS_SENT_1.getWaitTime().toString());
			}
		}
		return localOneTimePassword;
	}

	private OneTimePassword checkSmsCountAndWaitTime(DataServiceRequest<OneTimePassword> dataServiceRequest,
			OneTimePassword findOneTimePassword, SmsWaitTime curremtSmsWaitTime, SmsWaitTime smsWaitTime)
			throws DataServiceException {
		Long differencInSeconds = HZTBUtil.differenceInSeconds(findOneTimePassword.getOtpCreationDateTime());
		Long smsWait;
		if (differencInSeconds < curremtSmsWaitTime.getWaitTime()) {
			smsWait = curremtSmsWaitTime.getWaitTime().intValue() - differencInSeconds.longValue();
			findOneTimePassword.setSmsWaitTime(smsWait.toString());
			return findOneTimePassword;
		} else {
			DataServiceRequest<OneTimePassword> dataServiceRequest2 = prepareDataServiceRequest(dataServiceRequest,
					findOneTimePassword, smsWaitTime);
			return oneTimePasswordAdapter.updateOTPCode(dataServiceRequest2);
		}

	}

	@Override
	public GetOTPResponse getOTPCode(OneTimePasswordRequest oneTimePasswordRequest) {
		try {
			OneTimePassword oneTimePassword = new OneTimePassword(oneTimePasswordRequest);
			DataServiceRequest<OneTimePassword> dataServiceRequest = new DataServiceRequest<>(oneTimePassword);
			OneTimePassword findOneTimePassword = oneTimePasswordAdapter.findOTPbyPhoneNumber(dataServiceRequest);
			if (null == findOneTimePassword) {
				throw new DataServiceException("User not available with phone number : ["
						+ dataServiceRequest.getPayload().getMobileNumber() + "]", "2");
			}
			return populateGetOTPResponse(findOneTimePassword);

		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Error occured during requestCode : {}", dataServiceException);
			throw BusinessError.build(ServiceManagerClientType.DS, dataServiceException.getMessage(),
					dataServiceException.getStatusCode());
		} catch (Exception exception) {
			throw new SystemError(exception.getMessage(), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
	}

	private GetOTPResponse populateGetOTPResponse(OneTimePassword oneTimePassword) {
		GetOTPResponse getOTPResponse = new GetOTPResponse();
		Optional.ofNullable(oneTimePassword.getOtpCode()).ifPresent(getOTPResponse::setOtpCode);

		return (GetOTPResponse) buildSuccessResponse(getOTPResponse);

	}

}