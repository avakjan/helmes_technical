package com.example.demo.sector;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sectors")
public class Sector {

	@Id
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder;

	public Sector() {
	}

	public Sector(Long id, String name, Long parentId, Integer sortOrder) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.sortOrder = sortOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
