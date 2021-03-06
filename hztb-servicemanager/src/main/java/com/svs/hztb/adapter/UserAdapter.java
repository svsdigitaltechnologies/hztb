package com.svs.hztb.adapter;

import java.util.List;

import com.svs.hztb.common.model.business.User;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.exception.DataServiceException;

/**
 * interface for user related database operations
 * 
 * @author skairamk
 *
 */
public interface UserAdapter {
	/**
	 * create user
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	User createUser(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;

	/**
	 * ping user
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	User ping(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;

	/**
	 * get user details
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	User getUserDetails(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;

	/**
	 * update user details
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	User updateUserDetails(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;

	/**
	 * registered user list
	 * 
	 * @param mobileNumbers
	 * @return
	 * @throws DataServiceException
	 */
	List<User> registeredUsers(DataServiceRequest<List<String>> mobileNumbers) throws DataServiceException;

	/**
	 * get user by Mobile Number
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	User findByPhoneNumber(DataServiceRequest<User> dataServiceRequest) throws DataServiceException;

}
