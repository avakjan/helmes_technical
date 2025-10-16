package com.example.demo.sector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

	private final SectorRepository sectorRepository;

	public SectorController(SectorRepository sectorRepository) {
		this.sectorRepository = sectorRepository;
	}

	@GetMapping
	public List<Map<String, Object>> listSectors() {
		List<Sector> sectors = sectorRepository.findAllByOrderBySortOrderAscIdAsc();
		List<Map<String, Object>> response = new ArrayList<>();
		for (Sector s : sectors) {
			Map<String, Object> m = new HashMap<>();
			m.put("id", s.getId());
			m.put("name", s.getName());
			m.put("parentId", s.getParentId());
			m.put("sortOrder", s.getSortOrder());
			response.add(m);
		}
		return response;
	}
}
