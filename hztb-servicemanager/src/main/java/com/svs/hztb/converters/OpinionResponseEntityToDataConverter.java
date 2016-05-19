package com.svs.hztb.converters;

import java.util.function.Function;

import com.svs.hztb.api.sm.model.refresh.OpinionResponseData;
import com.svs.hztb.entity.OpinionResponseEntity;

public class OpinionResponseEntityToDataConverter  implements 
Function<OpinionResponseEntity, OpinionResponseData>{
	@Override
	public OpinionResponseData apply(OpinionResponseEntity opinionResponseEntity) {
		OpinionResponseData opinionResponseData = new OpinionResponseData();
		opinionResponseData.setOpinionId(opinionResponseEntity.getOpinionId());
		opinionResponseData.setResponseType(opinionResponseEntity.getOpinionResponseType());
		//opinionResponseData.setResponseTime(opinionResponseEntity.getResponseTime());
		opinionResponseData.setResponseText(opinionResponseEntity.getResponseTxt());
		opinionResponseData.setResponderUserId(opinionResponseEntity.getResponderUserId());
		return opinionResponseData;
	}
}
