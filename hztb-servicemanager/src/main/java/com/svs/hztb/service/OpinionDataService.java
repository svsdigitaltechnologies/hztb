package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.opinion.OpinionOutput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionRequest;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionResponse;

public interface OpinionDataService {
	RequestOpinionResponse requestOpinion(RequestOpinionRequest opinionRequestrequest);

	OpinionOutput saveResponse(OpinionResponseInput opinionResponseInput);
}
