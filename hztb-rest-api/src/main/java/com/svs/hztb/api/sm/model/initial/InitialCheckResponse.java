package com.svs.hztb.api.sm.model.initial;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svs.hztb.common.model.HztbResponse;

/**
 * Rest API Request class for Initial Response Process
 * 
 * @author skairamk
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InitialCheckResponse extends HztbResponse {

	private Boolean needUpdate;

	public Boolean getNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(Boolean needUpdate) {
		this.needUpdate = needUpdate;
	}

}
