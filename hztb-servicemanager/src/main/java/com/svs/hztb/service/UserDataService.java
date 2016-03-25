package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.registration.RegistrationRequest;
import com.svs.hztb.api.sm.model.registration.RegistrationResponse;

public interface UserDataService {

	RegistrationResponse register(RegistrationRequest registrationRequest);

}
