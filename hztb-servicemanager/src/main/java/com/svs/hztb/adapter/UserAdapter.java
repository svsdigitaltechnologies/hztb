package com.svs.hztb.adapter;

import com.svs.hztb.common.model.business.User;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.exception.DataServiceException;

public interface UserAdapter {
	User registerUser(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;
}
