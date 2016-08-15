package com.svs.hztb.adapter;

import com.svs.hztb.common.model.business.OneTimePassword;
import com.svs.hztb.ds.model.DataServiceRequest;
import com.svs.hztb.exception.DataServiceException;

/**
 * 
 * @author skairamk
 *
 */
public interface OneTimePasswordAdapter {

	/**
	 * find otp record using phone number and unique id
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	OneTimePassword findOTPbyPhoneAndUniqueId(DataServiceRequest<OneTimePassword> dataServiceRequest)
			throws DataServiceException;

	/**
	 * create otp record
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	OneTimePassword createOTPCode(DataServiceRequest<OneTimePassword> dataServiceRequest) throws DataServiceException;

	/**
	 * create otp record
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	OneTimePassword updateOTPCode(DataServiceRequest<OneTimePassword> dataServiceRequest) throws DataServiceException;

	/**
	 * find otp record using phone number, unique id and Identity.
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	OneTimePassword findOTPbyPhoneAndUniqueIdAndIdentity(DataServiceRequest<OneTimePassword> dataServiceRequest)
			throws DataServiceException;

	/**
	 * find otp record using phone number, unique id and Identity.
	 * 
	 * @param dataServiceRequest
	 * @return
	 * @throws DataServiceException
	 */
	void deleteOTPCode(DataServiceRequest<OneTimePassword> dataServiceRequest) throws DataServiceException;

}
