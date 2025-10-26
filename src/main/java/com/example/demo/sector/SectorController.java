package com.example.demo.sector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Comparator;
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
		List<Sector> sectors = orderSectorsHierarchically(sectorRepository.findAll());
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

	private List<Sector> orderSectorsHierarchically(List<Sector> sectors) {
		Map<Long, Sector> byId = new HashMap<>();
		for (Sector s : sectors) {
			byId.put(s.getId(), s);
		}

		Map<Long, List<Sector>> childrenByParent = new HashMap<>();
		List<Sector> roots = new ArrayList<>();
		for (Sector s : sectors) {
			Long parentId = s.getParentId();
			if (parentId == null || !byId.containsKey(parentId)) {
				roots.add(s);
			} else {
				childrenByParent.computeIfAbsent(parentId, k -> new ArrayList<>()).add(s);
			}
		}

		Comparator<Sector> byIdAsc = (a, b) -> Long.compare(a.getId(), b.getId());
		roots.sort(byIdAsc);
		for (List<Sector> children : childrenByParent.values()) {
			children.sort(byIdAsc);
		}

		List<Sector> ordered = new ArrayList<>(sectors.size());
		Set<Long> visited = new HashSet<>();
		for (Sector root : roots) {
			traversePreOrder(root, childrenByParent, visited, ordered);
		}
		for (Sector s : sectors) {
			if (!visited.contains(s.getId())) {
				traversePreOrder(s, childrenByParent, visited, ordered);
			}
		}
		return ordered;
	}

	private void traversePreOrder(
			Sector node,
			Map<Long, List<Sector>> childrenByParent,
			Set<Long> visited,
			List<Sector> ordered
	) {
		if (!visited.add(node.getId())) {
			return;
		}
		ordered.add(node);
		List<Sector> children = childrenByParent.get(node.getId());
		if (children != null) {
			for (Sector child : children) {
				traversePreOrder(child, childrenByParent, visited, ordered);
			}
		}
	}
}
