package com.example.demo.submission;

import java.util.List;

public class SubmissionResponse {

	private Long id;
	private String name;
	private boolean agreeToTerms;
	private List<Long> sectorIds;

	public SubmissionResponse(Long id, String name, boolean agreeToTerms, List<Long> sectorIds) {
		this.id = id;
		this.name = name;
		this.agreeToTerms = agreeToTerms;
		this.sectorIds = sectorIds;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isAgreeToTerms() {
		return agreeToTerms;
	}

	public List<Long> getSectorIds() {
		return sectorIds;
	}
}
