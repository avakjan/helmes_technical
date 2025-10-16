package com.example.demo.submission;

import java.util.List;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class SubmissionRequest {

	@NotBlank
	private String name;

	@NotEmpty
	private List<Long> sectorIds;

	@AssertTrue
	private boolean agreeToTerms;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getSectorIds() {
		return sectorIds;
	}

	public void setSectorIds(List<Long> sectorIds) {
		this.sectorIds = sectorIds;
	}

	public boolean isAgreeToTerms() {
		return agreeToTerms;
	}

	public void setAgreeToTerms(boolean agreeToTerms) {
		this.agreeToTerms = agreeToTerms;
	}
}
