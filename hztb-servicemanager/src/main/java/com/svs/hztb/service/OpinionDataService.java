package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.opinion.OpinionOutput;
import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.OpinionRequest;

public interface OpinionDataService {
	OpinionOutput requestOpinion(OpinionRequest opinionRequestrequest);

	OpinionOutput saveResponse(OpinionResponseInput opinionResponseInput);
	
	
}
