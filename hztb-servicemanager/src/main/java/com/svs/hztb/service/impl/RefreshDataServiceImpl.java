package com.svs.hztb.service.impl;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.hztb.api.sm.model.refresh.OpinionData;
import com.svs.hztb.api.sm.model.refresh.OpinionResponseData;
import com.svs.hztb.api.sm.model.refresh.RefreshInput;
import com.svs.hztb.api.sm.model.refresh.RefreshOutput;
import com.svs.hztb.converters.OpinionEntitityToDataConverter;
import com.svs.hztb.converters.OpinionResponseEntityToDataConverter;
import com.svs.hztb.entity.OpinionEntity;
import com.svs.hztb.entity.OpinionResponseEntity;
import com.svs.hztb.repository.OpinionRepository;
import com.svs.hztb.repository.OpinionResponseRepository;
import com.svs.hztb.service.RefreshDataService;
import com.svs.hztb.sm.common.util.DateUtils;
import com.svs.hztb.sm.common.util.FunctionUtils;

import static com.svs.hztb.sm.common.util.DateUtils.*;


@Service
public class RefreshDataServiceImpl implements RefreshDataService {
	@Autowired
	OpinionResponseRepository opinionResponseRepository;
	
	@Autowired
	OpinionRepository opinionRepository;
	
	
	@Override
	public RefreshOutput getResponses(RefreshInput refreshInput) {
		List<OpinionResponseEntity> opinionResponseEntities = null;
		RefreshOutput refreshOutput = new RefreshOutput();
		
		if(null == refreshInput.getLastUpdatedTime()) {
			opinionResponseEntities = opinionResponseRepository.findByResponderUserId(refreshInput.getUserId());
		} else {
			opinionResponseEntities = opinionResponseRepository.findByResponderUserIdLastUpdatedTime(refreshInput.getUserId(), getDate(refreshInput.getLastUpdatedTime()));
		}
		List<OpinionResponseData> opinionResponseDataList =  FunctionUtils.convert(opinionResponseEntities, new OpinionResponseEntityToDataConverter());
		refreshOutput.setOpinionResponseDataList(opinionResponseDataList);
		return refreshOutput;
	}
	
	@Override
	public RefreshOutput getOpinions(RefreshInput refreshInput) {
		
		List<OpinionEntity> opinionEntities = null;
		RefreshOutput refreshOutput = new RefreshOutput();
		
		if(null == refreshInput.getLastUpdatedTime()) {
			 opinionEntities = opinionRepository.findByUserId(refreshInput.getUserId());
		} else {
			opinionEntities = opinionRepository.findByUserIdLastUpdatedTime(refreshInput.getUserId(), getDate(refreshInput.getLastUpdatedTime()));
		}
		List<OpinionData> opinionDataList =  FunctionUtils.convert(opinionEntities, new OpinionEntitityToDataConverter());
		refreshOutput.setOpinionDataList(opinionDataList);
		return refreshOutput;
	}
}