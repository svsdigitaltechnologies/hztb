package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.opinion.RequestOpinionRequest;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionResponse;

public interface OpinionDataService {
	RequestOpinionResponse requestOpinion(RequestOpinionRequest opinionRequestrequest);
}
