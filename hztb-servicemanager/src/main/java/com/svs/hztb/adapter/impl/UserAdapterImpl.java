package com.svs.hztb.adapter.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.hztb.adapter.UserAdapter;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.business.User;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.entity.UserEntity;
import com.svs.hztb.exception.DataServiceException;
import com.svs.hztb.repository.UserRepository;

@Service
public class UserAdapterImpl implements UserAdapter {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(UserAdapterImpl.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * This method is used to find a user using mobile number.
	 * 
	 * @throws DataServiceException
	 */
	@Override
	public User findUser(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {
		User user = null;
		try {
			UserEntity userEntity = userRepository
					.findByMobileNumber(dataServiceRequest.getPayload().getMobileNumber());
			if (null != userEntity) {
				user = populateUserResponse(userEntity);
			}
		} catch (Exception exception) {
			LOGGER.error(
					"Data Services - Unexpected exception occured while registering the user. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}

		return user;
	}

	/**
	 * This method is used to register a user with hztb.
	 * 
	 * @throws DataServiceException
	 */
	@Override
	public User createUser(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {

		User user = null;
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setMobileNumber(dataServiceRequest.getPayload().getMobileNumber());
			userEntity.setOtpCode(dataServiceRequest.getPayload().getOtpCode());
			userEntity.setOtpCreateTime(dataServiceRequest.getPayload().getOtpCreationDateTime());
			userEntity.setInvalidOtpRetries(dataServiceRequest.getPayload().getOtpCode());
			userRepository.save(userEntity);
			user = populateUserResponse(userEntity);
		} catch (Exception exception) {
			LOGGER.error(
					"Data Services - Unexpected exception occured while registering the user. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}
		LOGGER.info("Data Services saved new record in Test table succesfully. Phoner Number: [ "
				+ dataServiceRequest.getPayload().getMobileNumber() + " ]");
		return user;
	}

	@Override
	public User ping(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {
		User user = null;
		try {
			UserEntity userEntity = userRepository.findByMobileAndImei(
					dataServiceRequest.getPayload().getMobileNumber(), dataServiceRequest.getPayload().getImei());
			if (null == userEntity) {
				throw new DataServiceException(
						"User not available with Mobile Number : [" + dataServiceRequest.getPayload().getMobileNumber()
								+ "] and Imei : [" + dataServiceRequest.getPayload().getImei() + "]",
						"4");
			}
			user = populatePingResponse(userEntity);
		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Data Services exception occured during ping. the detailed exception is: {} ",
					dataServiceException);
			throw dataServiceException;
		} catch (Exception exception) {
			LOGGER.error("Data Services - Unexpected exception occured during ping. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}
		return user;
	}

	private User populatePingResponse(UserEntity userEntity) {
		User user = new User();
		user.setMobileNumber(userEntity.getMobileNumber());
		user.setOtpCode(userEntity.getOtpCode());
		return user;
	}

	@Override
	public User getUserDetails(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {
		User user = null;
		try {
			UserEntity userEntity = userRepository
					.findByMobileNumber(dataServiceRequest.getPayload().getMobileNumber());

			if (null == userEntity) {
				throw new DataServiceException("User not available with Mobile Number : ["
						+ dataServiceRequest.getPayload().getMobileNumber() + "]", "2");
			}
			user = populateUserResponse(userEntity);
		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Data Services exception occured during ping. the detailed exception is: {} ",
					dataServiceException);
			throw dataServiceException;
		} catch (Exception exception) {
			LOGGER.error("Data Services - Unexpected exception occured during ping. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}
		return user;
	}

	private User populateUserResponse(UserEntity userEntity) {
		User user = new User();
		Optional.ofNullable(userEntity.getMobileNumber()).ifPresent(p -> user.setMobileNumber(p));
		Optional.ofNullable(userEntity.getDataPushedInd()).ifPresent(p -> user.setDataPushed(p));
		Optional.ofNullable(userEntity.getEmailAddress()).ifPresent(p -> user.setEmailAddress(p));
		Optional.ofNullable(userEntity.getFirstname()).ifPresent(p -> user.setName(p));
		Optional.ofNullable(userEntity.getGcmRegId()).ifPresent(p -> user.setDeviceRegId(p));
		Optional.ofNullable(userEntity.getImeiCode()).ifPresent(p -> user.setImei(p));
		Optional.ofNullable(userEntity.getOtpCode()).ifPresent(p -> user.setOtpCode(p));
		Optional.ofNullable(userEntity.getOtpCreateTime()).ifPresent(p -> user.setOtpCreationDateTime(p));
		Optional.ofNullable(userEntity.getUserId()).ifPresent(p -> user.setUserId(p.toString()));
		Optional.ofNullable(userEntity.getInvalidOtpRetries()).ifPresent(p -> user.setInvalidOtpCount(p));
		return user;
	}

	@Override
	public User updateUserDetails(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {
		UserEntity userEntity;
		try {
			userEntity = userRepository
					.findOne(Integer.parseInt(dataServiceRequest.getPayload().getUserId()));

			Optional.ofNullable(dataServiceRequest.getPayload().getDeviceRegId())
					.ifPresent(p -> userEntity.setGcmRegId(p));
			Optional.ofNullable(dataServiceRequest.getPayload().getEmailAddress())
					.ifPresent(p -> userEntity.setEmailAddress(p));
			Optional.ofNullable(dataServiceRequest.getPayload().getImei()).ifPresent(p -> userEntity.setImeiCode(p));
			Optional.ofNullable(dataServiceRequest.getPayload().getMobileNumber())
					.ifPresent(p -> userEntity.setMobileNumber(p));
			Optional.ofNullable(dataServiceRequest.getPayload().getName()).ifPresent(p -> userEntity.setFirstname(p));
			Optional.ofNullable(dataServiceRequest.getPayload().getUserId())
					.ifPresent(p -> userEntity.setUserId(Integer.parseInt(p)));
			Optional.ofNullable(dataServiceRequest.getPayload().getOtpCode()).ifPresent(p -> userEntity.setOtpCode(p));
			Optional.ofNullable(dataServiceRequest.getPayload().getOtpCreationDateTime())
					.ifPresent(p -> userEntity.setOtpCreateTime(p));
			Optional.ofNullable(dataServiceRequest.getPayload().getInvalidOtpCount())
					.ifPresent(p -> userEntity.setInvalidOtpRetries(p));

			userRepository.save(userEntity);
		} catch (Exception exception) {
			LOGGER.error("Data Services - Unexpected exception occured during ping. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}
		return  populateUserResponse(userEntity);

	}

}
