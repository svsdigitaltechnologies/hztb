package com.svs.hztb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svs.hztb.adapter.UserAdapter;
import com.svs.hztb.api.sm.model.registration.RegistrationRequest;
import com.svs.hztb.api.sm.model.registration.RegistrationResponse;
import com.svs.hztb.common.enums.ServiceManagerClientType;
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
			User user = new User(registrationRequest);
			DataServiceRequest<User> dataServiceRequest = new DataServiceRequest<User>(user);
			user = userAdapter.registerUser(dataServiceRequest);
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
		registrationResponse.setPhoneNumber(user.getPhoneNumber());
		return registrationResponse;
	}
	
	
}
