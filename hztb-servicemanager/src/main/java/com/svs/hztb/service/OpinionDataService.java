package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.opinion.OpinionOutput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.RequestOpinionInput;

public interface OpinionDataService {
	OpinionOutput requestOpinion(RequestOpinionInput opinionRequestrequest);

	OpinionOutput saveResponse(OpinionResponseInput opinionResponseInput);
}
