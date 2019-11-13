package com.application.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.application.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>  {

	//List<Post> findByCategory(final String Category);
	
	public List<Post> findByEmail(String email);
	
	public List<Post> findByCategoryIn(Collection<String> list);
	
//	public Comment findByComments(String id);
}
