package com.svs.hztb.api.sm.model.clickatell;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;

public class ClickatellRequest {
	@NotNull
	@Size(min = 1, max = 15)
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private List<String> to;

	@NotNull
	private String text;

	public List<String> getTo() {
		return to;
	}

	public void setTo(String to) {
		if(this.to == null) {
			this.to = new ArrayList<String>();
		}
		this.to.add(to);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
