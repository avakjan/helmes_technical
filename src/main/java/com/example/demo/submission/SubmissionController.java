package com.example.demo.submission;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.sector.Sector;
import com.example.demo.sector.SectorRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/submissions")
@Validated
public class SubmissionController {

	private final UserSubmissionRepository submissionRepository;
	private final SectorRepository sectorRepository;

	public SubmissionController(UserSubmissionRepository submissionRepository, SectorRepository sectorRepository) {
		this.submissionRepository = submissionRepository;
		this.sectorRepository = sectorRepository;
	}

	@GetMapping("/me")
	@Transactional(readOnly = true)
	public Optional<SubmissionResponse> getMySubmission(HttpSession session) {
		Long id = (Long) session.getAttribute("submissionId");
		if (id == null) {
			return Optional.empty();
		}
		return submissionRepository.findById(id)
			.map(s -> new SubmissionResponse(
				s.getId(),
				s.getName(),
				s.isAgreeTerms(),
				s.getSectors().stream().map(Sector::getId).collect(Collectors.toList())
			));
	}

	@PostMapping
	@Transactional
	public SubmissionResponse saveSubmission(@Valid @RequestBody SubmissionRequest request, HttpSession session) {
		UserSubmission submission;
		Long id = (Long) session.getAttribute("submissionId");
		if (id != null) {
			submission = submissionRepository.findById(id).orElseGet(UserSubmission::new);
		} else {
			submission = new UserSubmission();
		}

		submission.setName(request.getName());
		submission.setAgreeTerms(request.isAgreeToTerms());

		List<Long> ids = request.getSectorIds();
		Set<Sector> sectors = ids.stream()
			.map(sectorRepository::getReferenceById)
			.collect(Collectors.toSet());
		submission.setSectors(sectors);

		UserSubmission saved = submissionRepository.save(submission);
		session.setAttribute("submissionId", saved.getId());
		return new SubmissionResponse(
			saved.getId(),
			saved.getName(),
			saved.isAgreeTerms(),
			saved.getSectors().stream().map(Sector::getId).collect(Collectors.toList())
		);
	}
}
