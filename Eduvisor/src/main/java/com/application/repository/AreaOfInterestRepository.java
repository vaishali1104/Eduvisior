package com.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.application.model.AreaOfInterest;

@Repository
public interface AreaOfInterestRepository extends MongoRepository<AreaOfInterest, String> {

}
