package com.svs.hztb.adapter.impl;

import java.util.ArrayList;
import java.util.List;
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

/**
 * This class is an implementation for user adapter interface. It invokes the db
 * operations using the user repository
 */
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
	 * This method is used to register a user.
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
			userEntity.setInvalidOtpRetries(dataServiceRequest.getPayload().getInvalidOtpCount());
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

	/**
	 * This method is used to get user details.
	 * 
	 * @throws DataServiceException
	 */
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
		Optional.ofNullable(userEntity.getMobileNumber()).ifPresent(user::setMobileNumber);
		Optional.ofNullable(userEntity.getDataPushedInd()).ifPresent(user::setDataPushed);
		Optional.ofNullable(userEntity.getEmailAddress()).ifPresent(user::setEmailAddress);
		Optional.ofNullable(userEntity.getFirstname()).ifPresent(user::setName);
		Optional.ofNullable(userEntity.getGcmRegId()).ifPresent(user::setDeviceRegId);
		Optional.ofNullable(userEntity.getImeiCode()).ifPresent(user::setImei);
		Optional.ofNullable(userEntity.getOtpCode()).ifPresent(user::setOtpCode);
		Optional.ofNullable(userEntity.getOtpCreateTime()).ifPresent(user::setOtpCreationDateTime);
		Optional.ofNullable(userEntity.getUserId()).ifPresent(p -> user.setUserId(p.toString()));
		Optional.ofNullable(userEntity.getInvalidOtpRetries()).ifPresent(user::setInvalidOtpCount);
		Optional.ofNullable(userEntity.getPicUrl()).ifPresent(user::setProfilePicUrl);
		return user;
	}

	/**
	 * This method is used to update user details.
	 * 
	 * @throws DataServiceException
	 */
	@Override
	public User updateUserDetails(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {
		UserEntity userEntity;
		try {
			userEntity = userRepository.findOne(Integer.parseInt(dataServiceRequest.getPayload().getUserId()));

			Optional.ofNullable(dataServiceRequest.getPayload().getDeviceRegId()).ifPresent(userEntity::setGcmRegId);
			Optional.ofNullable(dataServiceRequest.getPayload().getEmailAddress())
					.ifPresent(userEntity::setEmailAddress);
			Optional.ofNullable(dataServiceRequest.getPayload().getImei()).ifPresent(userEntity::setImeiCode);
			Optional.ofNullable(dataServiceRequest.getPayload().getMobileNumber())
					.ifPresent(userEntity::setMobileNumber);
			Optional.ofNullable(dataServiceRequest.getPayload().getName()).ifPresent(userEntity::setFirstname);
			Optional.ofNullable(dataServiceRequest.getPayload().getUserId())
					.ifPresent(p -> userEntity.setUserId(Integer.parseInt(p)));
			Optional.ofNullable(dataServiceRequest.getPayload().getOtpCode()).ifPresent(userEntity::setOtpCode);
			Optional.ofNullable(dataServiceRequest.getPayload().getOtpCreationDateTime())
					.ifPresent(userEntity::setOtpCreateTime);
			Optional.ofNullable(dataServiceRequest.getPayload().getInvalidOtpCount())
					.ifPresent(userEntity::setInvalidOtpRetries);
			Optional.ofNullable(dataServiceRequest.getPayload().getProfilePicUrl()).ifPresent(userEntity::setPicUrl);
			Optional.ofNullable(dataServiceRequest.getPayload().getDeviceId()).ifPresent(userEntity::setDeviceId);

			userRepository.save(userEntity);
		} catch (Exception exception) {
			LOGGER.error(
					"Data Services - Unexpected exception occured during updateUserDetails. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}
		return populateUserResponse(userEntity);

	}

	/**
	 * This method is used to retrieve the registered users.
	 * 
	 * @throws DataServiceException
	 */
	@Override
	public List<User> registeredUsers(DataServiceRequest<List<String>> dataServiceRequest) throws DataServiceException {

		List<UserEntity> userEntities;
		try {
			userEntities = userRepository.findByMobileNumberIn(dataServiceRequest.getPayload());

		} catch (Exception exception) {
			LOGGER.error(
					"Data Services - Unexpected exception occured during registeredUsers. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}
		return populateUserResponses(userEntities);
	}

	private List<User> populateUserResponses(List<UserEntity> userEntities) {
		List<User> usersList = new ArrayList<>();
		userEntities.stream().forEach(p -> usersList.add(populateUserResponse(p)));
		return usersList;
	}
}
