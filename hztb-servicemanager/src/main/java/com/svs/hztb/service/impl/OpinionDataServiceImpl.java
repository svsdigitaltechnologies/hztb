package com.svs.hztb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.hztb.api.sm.model.opinion.RequestOpinionRequest;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionResponse;
import com.svs.hztb.entity.OpinionEntity;
import com.svs.hztb.repository.OpinionRepository;
import com.svs.hztb.service.OpinionDataService;

@Service
public class OpinionDataServiceImpl implements OpinionDataService {
	@Autowired
	OpinionRepository opinionRepository;

	@Override
	public RequestOpinionResponse requestOpinion(RequestOpinionRequest requestOpinionRequest) {
		// validations

		OpinionEntity opinionEntity = createOpinionEntity(requestOpinionRequest);
		opinionRepository.save(opinionEntity);
		return new RequestOpinionResponse();
	}

	private OpinionEntity createOpinionEntity(RequestOpinionRequest requestOpinionRequest) {
		OpinionEntity opinionEntity = new OpinionEntity();

		opinionEntity.setUserId(requestOpinionRequest.getRequesterUserId());
		opinionEntity.setGroupId(requestOpinionRequest.getRequestedGroupId());
		opinionRepository.save(opinionEntity);
		return opinionEntity;
	}

}
