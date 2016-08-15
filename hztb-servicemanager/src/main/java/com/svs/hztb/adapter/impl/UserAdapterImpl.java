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
			userEntity.setDeviceId(dataServiceRequest.getPayload().getUniqueId());
			userEntity.setIdentity(dataServiceRequest.getPayload().getIdentity());
			userEntity.setRegistered(dataServiceRequest.getPayload().getRegistered());
			userEntity.setGcmRegId(dataServiceRequest.getPayload().getDeviceRegId());
			userEntity.setPw(dataServiceRequest.getPayload().getPw());
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
			UserEntity userEntity = userRepository.findByUserIdAndDeviceIdAndRegistered(
					dataServiceRequest.getPayload().getUserId(), dataServiceRequest.getPayload().getUniqueId());
			if (null == userEntity) {
				throw new DataServiceException("User not available with UserId: ["
						+ dataServiceRequest.getPayload().getUserId() + "] and Device Id : ["
						+ dataServiceRequest.getPayload().getUniqueId() + "] and Registered", "4");
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
		user.setUserId(userEntity.getUserId());
		return user;
	}

	/**
	 * This method is used to get user details of a registered user.
	 * 
	 * @throws DataServiceException
	 */
	@Override
	public User getUserDetails(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {
		User user = null;
		try {
			UserEntity userEntity = userRepository.findOne(dataServiceRequest.getPayload().getUserId());

			if (null == userEntity) {
				throw new DataServiceException(
						"User not available with User Id : [" + dataServiceRequest.getPayload().getUserId() + "]", "2");
			}
			user = populateGetUserProfileResponse(userEntity);
		} catch (DataServiceException dataServiceException) {
			LOGGER.error("Data Services exception occured during getUserDetails. the detailed exception is: {} ",
					dataServiceException);
			throw dataServiceException;
		} catch (Exception exception) {
			LOGGER.error(
					"Data Services - Unexpected exception occured during getUserDetails. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}
		return user;
	}

	private User populateUserResponse(UserEntity userEntity) {
		User user = new User();
		Optional.ofNullable(userEntity.getMobileNumber()).ifPresent(user::setMobileNumber);
		Optional.ofNullable(userEntity.getEmailAddress()).ifPresent(user::setEmailAddress);
		Optional.ofNullable(userEntity.getUserName()).ifPresent(user::setName);
		Optional.ofNullable(userEntity.getGcmRegId()).ifPresent(user::setDeviceRegId);
		Optional.ofNullable(userEntity.getUserId()).ifPresent(user::setUserId);
		Optional.ofNullable(userEntity.getPicUrl()).ifPresent(user::setProfilePicUrl);
		Optional.ofNullable(userEntity.getRegistered()).ifPresent(user::setRegistered);
		Optional.ofNullable(userEntity.getDeviceId()).ifPresent(user::setUniqueId);
		Optional.ofNullable(userEntity.getPw()).ifPresent(user::setPw);
		return user;
	}

	private User populateGetUserProfileResponse(UserEntity userEntity) {
		User user = new User();
		Optional.ofNullable(userEntity.getEmailAddress()).ifPresent(user::setEmailAddress);
		Optional.ofNullable(userEntity.getUserName()).ifPresent(user::setName);
		Optional.ofNullable(userEntity.getUserId()).ifPresent(user::setUserId);
		Optional.ofNullable(userEntity.getPicUrl()).ifPresent(user::setProfilePicUrl);
		Optional.ofNullable(userEntity.getPicVersion()).ifPresent(user::setProfilePicVersion);
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
			userEntity = userRepository.findOne(dataServiceRequest.getPayload().getUserId());

			Optional.ofNullable(dataServiceRequest.getPayload().getDeviceRegId()).ifPresent(userEntity::setGcmRegId);
			Optional.ofNullable(dataServiceRequest.getPayload().getEmailAddress())
					.ifPresent(userEntity::setEmailAddress);
			Optional.ofNullable(dataServiceRequest.getPayload().getMobileNumber())
					.ifPresent(userEntity::setMobileNumber);
			Optional.ofNullable(dataServiceRequest.getPayload().getName()).ifPresent(userEntity::setUserName);
			Optional.ofNullable(dataServiceRequest.getPayload().getUserId()).ifPresent(userEntity::setUserId);
			Optional.ofNullable(dataServiceRequest.getPayload().getProfilePicUrl()).ifPresent(userEntity::setPicUrl);
			Optional.ofNullable(dataServiceRequest.getPayload().getProfilePicVersion())
					.ifPresent(userEntity::setPicVersion);

			Optional.ofNullable(dataServiceRequest.getPayload().getUniqueId()).ifPresent(userEntity::setDeviceId);
			Optional.ofNullable(dataServiceRequest.getPayload().getIdentity()).ifPresent(userEntity::setIdentity);
			Optional.ofNullable(dataServiceRequest.getPayload().getRegistered()).ifPresent(userEntity::setRegistered);
			Optional.ofNullable(dataServiceRequest.getPayload().getPw()).ifPresent(userEntity::setPw);

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

	@Override
	public User findByPhoneNumber(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {
		User user = null;
		try {
			UserEntity userEntity = userRepository
					.findByMobileNumber(dataServiceRequest.getPayload().getMobileNumber());
			if (null != userEntity) {
				user = populateUserResponse(userEntity);
			}
		} catch (Exception exception) {
			LOGGER.error(
					"Data Services - Unexpected exception occured while findUserByMobileAndDeviceId. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}

		return user;
	}

}
