package com.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.model.AreaOfInterest;
import com.application.repository.AreaOfInterestRepository;

@Service
public class AreaOfInterestService {

	@Autowired
	private AreaOfInterestRepository aoiRepository;
	
	public List<String> listOfInterests() {
		List<String> interests = new ArrayList<String>();
		for(AreaOfInterest aoi:aoiRepository.findAll())
			interests.add(aoi.getName());
		return interests;
	}
	
	public List<String> listOfInterests(List<AreaOfInterest> aoiList) {
		List<String> interests = new ArrayList<String>();
		for(AreaOfInterest aoi:aoiList)
			interests.add(aoi.getName());
		return interests;
	}
}
