package com.svs.hztb.api.sm.model.initial;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;
import com.svs.hztb.common.model.HztbRequest;

/**
 * Rest API Request class for Initial Request Process
 * 
 * @author skairamk
 *
 */
public class InitialCheckRequest extends HztbRequest {

	@NotNull
	@Size(min = 1, max = 15)
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private String versionCode;

	@NotNull
	@Size(min = 1, max = 8)
	private String versionName;

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

}
