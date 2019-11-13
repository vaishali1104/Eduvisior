package com.application.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("AreaOfInterest")
public class AreaOfInterest {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AreaOfInterest [name=" + name + "]";
	}
}
