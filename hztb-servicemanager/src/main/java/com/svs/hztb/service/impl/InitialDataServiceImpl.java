package com.svs.hztb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.svs.hztb.api.sm.model.initial.InitialCheckRequest;
import com.svs.hztb.api.sm.model.initial.InitialCheckResponse;
import com.svs.hztb.service.BaseService;
import com.svs.hztb.service.InitialService;

/**
 * This class is an service implementation for user related methods
 */
@Service
@Transactional
public class InitialDataServiceImpl extends BaseService implements InitialService {

	@Override
	public InitialCheckResponse checkVersion(InitialCheckRequest initialRequest) {
		InitialCheckResponse initialResponse = new InitialCheckResponse();
		initialResponse.setNeedUpdate(false);
		return (InitialCheckResponse) buildSuccessResponse(initialResponse);
	}
}