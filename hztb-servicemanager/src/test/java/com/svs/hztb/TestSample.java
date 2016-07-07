package com.svs.hztb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;
import com.svs.hztb.service.UserDataService;
import com.svs.hztb.sm.user.controller.UserController;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
public class TestSample {

	@InjectMocks
	UserController userController;
	
	@Mock
	UserDataService userDataService;
	
	
	@Test
	public void testPing() {
		UserProfileRequest userProfileRequest = new UserProfileRequest();
		ResponseEntity<UserProfileResponse> userProfileResponse;
		
		//Given
		Mockito.when(userDataService.getUserProfile(Mockito.anyObject())).thenReturn(new UserProfileResponse());
		
		userProfileResponse = userController.getUserProfile(userProfileRequest);
		Assert.assertNotNull(userProfileResponse);
	}
}
