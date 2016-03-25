package com.svs.hztb.adapter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.hztb.adapter.UserAdapter;
import com.svs.hztb.common.model.business.User;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.entity.UserEntity;
import com.svs.hztb.exception.DataServiceException;
import com.svs.hztb.repository.UserRepository;

@Service
public class UserAdapterImpl implements UserAdapter {

	private final static Logger logger = LoggerFactory.getLogger(UserAdapterImpl.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * This method is used to register a user with hztb. 
	 * @throws DataServiceException
	 */
	@Override
	public User registerUser(DataServiceRequest<User> dataServiceRequest) throws DataServiceException {
		User user = null;
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setId(1);
			userEntity.setTestcol(dataServiceRequest.getPayload().getPhoneNumber());
			userEntity = userRepository.save(userEntity);
			
			if (null == userEntity) {
				throw new DataServiceException(
						"User not saved with Phone Number: [" + dataServiceRequest.getPayload().getPhoneNumber() + "]",
						"1");
			}
			user = populateUserResponse(userEntity);
		} catch (DataServiceException dataServiceException) {
			logger.error(
					"Data Services - Unexpected exception occured while registering the user. the detailed exception is: {} ",
					dataServiceException);
			throw dataServiceException;
		} catch (Exception exception) {
			logger.error(
					"Data Services - Unexpected exception occured while registering the user. the detailed exception is: {} ",
					exception);
			throw new DataServiceException(exception.getMessage(), "1");
		}
		logger.info("Data Services saved new record in Test table succesfully. Phoner Number: [ "
				+ dataServiceRequest.getPayload().getPhoneNumber() + " ]");
		return user;
	}

	private User populateUserResponse(UserEntity userEntity) {
		User user = new User();
		user.setPhoneNumber(userEntity.getTestcol());
		return user;
	}
}
