package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.refresh.OpinionRefreshRequest;
import com.svs.hztb.api.sm.model.refresh.RefreshOutput;

public interface RefreshDataService {

	RefreshOutput getResponsesByUser(OpinionRefreshRequest refreshInput);
	RefreshOutput getResponsesByOpinion(OpinionRefreshRequest refreshInput);
	RefreshOutput getOpinions(OpinionRefreshRequest refreshInput);
	RefreshOutput getAllResponsesCounts(OpinionRefreshRequest refreshInput);
	RefreshOutput getOpinionsGivenPending(OpinionRefreshRequest refreshInput);

}
