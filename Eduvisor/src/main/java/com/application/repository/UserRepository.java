package com.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.application.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public User findByEmail(String email, String password);
	
	public User findByEmail(String email);
	
	public User findByTokenID(String tokenId);
}
