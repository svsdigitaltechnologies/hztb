package com.svs.hztb.converters;

import java.util.function.Function;

import com.svs.hztb.api.sm.model.user.UserData;
import com.svs.hztb.entity.UserEntity;

public class UserEntityToDataConverter implements Function<UserEntity, UserData> {

	@Override
	public UserData apply(UserEntity userEntity) {
		UserData userData = new UserData();
		userData.setUserId(userEntity.getUserId());
		if (null != userEntity.getUserName())
			userData.setFirstName(userEntity.getUserName());
		return userData;
	}
}