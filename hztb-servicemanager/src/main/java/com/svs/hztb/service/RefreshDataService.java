package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.refresh.RefreshInput;
import com.svs.hztb.api.sm.model.refresh.RefreshOutput;

public interface RefreshDataService {

	RefreshOutput getResponsesByUser(RefreshInput refreshInput);
	RefreshOutput getResponsesByOpinion(RefreshInput refreshInput);
	RefreshOutput getOpinions(RefreshInput refreshInput);

}
