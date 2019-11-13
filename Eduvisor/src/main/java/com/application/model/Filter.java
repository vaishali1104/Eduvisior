package com.application.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Filter {

	private List<String> name;

	private String sorting;

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

}
