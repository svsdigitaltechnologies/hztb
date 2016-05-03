package com.svs.hztb.converters;

import java.util.function.Function;

import com.svs.hztb.api.sm.model.refresh.OpinionData;
import com.svs.hztb.entity.OpinionEntity;

public class OpinionEntitityToDataConverter implements 
		Function<OpinionEntity, OpinionData>{

	@Override
	public OpinionData apply(OpinionEntity opinionEntity) {
		OpinionData opinionData = new OpinionData();
		opinionData.setOpinionId(opinionEntity.getOpinionId());
		opinionData.setProductName(opinionEntity.getProduct());
		opinionData.setRequestedGroupId(opinionEntity.getGroupId());
		//opinionData.setRequestedUserId(opinionEntity.getUserId());
		return opinionData;
	}



}
