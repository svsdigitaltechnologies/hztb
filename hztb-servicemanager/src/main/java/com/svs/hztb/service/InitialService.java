package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.initial.InitialCheckRequest;
import com.svs.hztb.api.sm.model.initial.InitialCheckResponse;

/**
 * 
 * interface for user data service methods
 * 
 * @author skairamk
 *
 */
public interface InitialService {

	/**
	 * method for checking version
	 * 
	 * @param initialRequest
	 * @return
	 */
	InitialCheckResponse checkVersion(InitialCheckRequest initialRequest);

}
