package com.svs.hztb.adapter;

import com.svs.hztb.common.model.business.User;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.exception.DataServiceException;

public interface UserAdapter {
	
	User findUser(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;
	
	User createUser(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;

	User ping(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;

	User getUserDetails(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;
	
	User updateUserDetails(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;
}
