package com.svs.hztb.adapter.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.hztb.adapter.OneTimePasswordAdapter;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.business.OneTimePassword;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.entity.OneTimePasswordEntity;
import com.svs.hztb.exception.DataServiceException;
import com.svs.hztb.repository.OneTimePasswordRepository;

/**
 * This class is an implementation for one time password adapter interface. It
 * invokes the db operations using the one time password repository
 */
@Service
public class OneTimePasswordAdapterImpl implements OneTimePasswordAdapter {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(OneTimePasswordAdapterImpl.class);

	@Autowired
	private OneTimePasswordRepository oneTimePasswordRepository;

	@Override
	public OneTimePassword findOTPbyPhoneAndUniqueId(DataServiceRequest<OneTimePassword> dataServiceRequest)
			throws DataServiceException {
		LOGGER.debug("In OneTimePasswordAdapterImpl, findOTPbyPhoneAndUniqueId method {}", dataServiceRequest);
		OneTimePassword oneTimePassword = null;
		OneTimePasswordEntity oneTimePasswordEntity = oneTimePasswordRepository.findByPhoneAndUniqueId(
				dataServiceRequest.getPayload().getMobileNumber(), dataServiceRequest.getPayload().getUniqueId());
		if (null != oneTimePasswordEntity) {
			oneTimePassword = populateOneTimePassword(oneTimePasswordEntity);
		}
		return oneTimePassword;
	}

	private OneTimePassword populateOneTimePassword(OneTimePasswordEntity oneTimePasswordEntity) {
		OneTimePassword oneTimePassword = new OneTimePassword();
		Optional.ofNullable(oneTimePasswordEntity.getId()).ifPresent(oneTimePassword::setId);
		Optional.ofNullable(oneTimePasswordEntity.getCode()).ifPresent(oneTimePassword::setOtpCode);
		Optional.ofNullable(oneTimePasswordEntity.getIdentity()).ifPresent(oneTimePassword::setIdentity);
		Optional.ofNullable(oneTimePasswordEntity.getInvalidAttempts()).ifPresent(oneTimePassword::setInvalidOtpCount);
		Optional.ofNullable(oneTimePasswordEntity.getMobileNumber()).ifPresent(oneTimePassword::setMobileNumber);
		Optional.ofNullable(oneTimePasswordEntity.getCodeTime()).ifPresent(oneTimePassword::setOtpCreationDateTime);
		Optional.ofNullable(oneTimePasswordEntity.getSmsSentCount()).ifPresent(oneTimePassword::setSmsSentCount);
		return oneTimePassword;
	}

	@Override
	public OneTimePassword createOTPCode(DataServiceRequest<OneTimePassword> dataServiceRequest)
			throws DataServiceException {
		OneTimePasswordEntity oneTimePasswordEntity = prepareOneTimePasswordEntity(dataServiceRequest.getPayload());
		OneTimePasswordEntity savedOneTimePasswordEntity = oneTimePasswordRepository.save(oneTimePasswordEntity);
		return populateOneTimePassword(savedOneTimePasswordEntity);
	}

	@Override
	public OneTimePassword updateOTPCode(DataServiceRequest<OneTimePassword> dataServiceRequest)
			throws DataServiceException {
		OneTimePasswordEntity oneTimePasswordEntity = prepareOneTimePasswordEntity(dataServiceRequest.getPayload());
		oneTimePasswordEntity.setId(dataServiceRequest.getPayload().getId());
		OneTimePasswordEntity savedOneTimePasswordEntity = oneTimePasswordRepository.save(oneTimePasswordEntity);
		return populateOneTimePassword(savedOneTimePasswordEntity);
	}

	private OneTimePasswordEntity prepareOneTimePasswordEntity(OneTimePassword oneTimePassword) {
		OneTimePasswordEntity oneTimePasswordEntity = new OneTimePasswordEntity();
		oneTimePasswordEntity.setCode(oneTimePassword.getOtpCode());
		oneTimePasswordEntity.setCodeTime(oneTimePassword.getOtpCreationDateTime());
		oneTimePasswordEntity.setIdentity(oneTimePassword.getIdentity());
		oneTimePasswordEntity.setMobileNumber(oneTimePassword.getMobileNumber());
		oneTimePasswordEntity.setSmsSentCount(oneTimePassword.getSmsSentCount());
		oneTimePasswordEntity.setInvalidAttempts(oneTimePassword.getInvalidOtpCount());
		return oneTimePasswordEntity;
	}

	@Override
	public void deleteOTPCode(DataServiceRequest<OneTimePassword> dataServiceRequest) throws DataServiceException {
		oneTimePasswordRepository.delete(dataServiceRequest.getPayload().getId());
	}

	@Override
	public OneTimePassword findOTPbyPhoneNumber(DataServiceRequest<OneTimePassword> dataServiceRequest)
			throws DataServiceException {
		LOGGER.debug("In OneTimePasswordAdapterImpl, findOTPbyPhoneNumber method {}", dataServiceRequest);
		OneTimePassword oneTimePassword = null;
		OneTimePasswordEntity oneTimePasswordEntity = oneTimePasswordRepository
				.findByPhoneNumber(dataServiceRequest.getPayload().getMobileNumber());
		if (null != oneTimePasswordEntity) {
			oneTimePassword = populateOneTimePassword(oneTimePasswordEntity);
		}
		return oneTimePassword;
	}

	@Override
	public OneTimePassword findOTPbyPhoneAndId(DataServiceRequest<OneTimePassword> dataServiceRequest)
			throws DataServiceException {
		LOGGER.debug("In OneTimePasswordAdapterImpl, findOTPbyPhoneAndId method {}", dataServiceRequest);
		OneTimePassword oneTimePassword = null;
		OneTimePasswordEntity oneTimePasswordEntity = oneTimePasswordRepository.findByPhoneAndIdentity(
				dataServiceRequest.getPayload().getMobileNumber(), dataServiceRequest.getPayload().getIdentity());
		if (null != oneTimePasswordEntity) {
			oneTimePassword = populateOneTimePassword(oneTimePasswordEntity);
		}
		return oneTimePassword;
	}

}
