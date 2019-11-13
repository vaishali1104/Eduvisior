package com.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.model.Comment;
import com.application.model.Post;
import com.application.model.User;
import com.application.repository.PostRepository;
import com.application.repository.UserRepository;

@Service
public class PostService {

	@Autowired
	public PostRepository postrepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<Post> allPost() {
		List<Post> p = postrepository.findAll();
		Collections.sort(p,(p1,p2) -> p2.getPostedDate().compareTo(p1.getPostedDate()));
		return p; 
	}
	
	public Post setposts(Post post) {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDate localDate = localDateTime.toLocalDate();
		post.setPostedDate(localDate);
		postrepository.save(post);
		return post;
	}

	public List<Post> display(String email) {
		return postrepository.findByEmail(email);
	}
	
	public Post updatepost(Post post, Post post1) {
		if(!(post1.getTitle().equalsIgnoreCase(post.getTitle())) || 
				(post1.getDescription().equalsIgnoreCase(post.getDescription()) || 
						(post1.getCategory().equals(post.getCategory())))) {
			post1.setTitle(post.getTitle());
			post1.setCategory(post.getCategory());
			post1.setDescription(post.getDescription());
			LocalDateTime localDateTime = LocalDateTime.now();
			LocalDate localDate = localDateTime.toLocalDate();
			post1.setPostedDate(localDate);
		}
		return setposts(post1);
	}

	public Post onePost(String id) {
		Optional<Post> ans = postrepository.findById(id);
		List<Post> res = new ArrayList<>();
		if (ans.isPresent())
			res.add(ans.get());
		return res.get(0);
	}
	
	public Comment oneComment(String id) {
//		Comment c = postrepository.findByComments(id);
		return new Comment();
	}
	
	public Post comment(Post post, Comment comment) {

		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDate localDate = localDateTime.toLocalDate();
		comment.setPostedDate(localDate);
		String id = UUID.randomUUID().toString();
		comment.setId(id);
//		System.out.println(comment.toString());
		List<Comment> clist;
		if(post.getComments() == null) {
			clist = new ArrayList<>();
		}
		else {
			clist = post.getComments();
		}
		clist.add(comment);
		post.setComments(clist);
		return postrepository.save(post);
	}
	
	public void deletePost(String id)
	{
		postrepository.deleteById(id);
	}
	
	
	public List<Post> filterFunction(List<String> name) {
		List<Post> p = postrepository.findByCategoryIn(name);
		Collections.sort(p,(p1,p2) -> p2.getPostedDate().compareTo(p1.getPostedDate()));
		return p;
	}
	
	public List<Post> filterFunction(String[] name) {
		List<Post> p = postrepository.findByCategoryIn(Arrays.asList(name));
		Collections.sort(p,(p1,p2) -> p2.getPostedDate().compareTo(p1.getPostedDate()));
		return p;
	}
	
	public void upvoteIncrement(String id) {
		User user = userRepository.findByEmail(id);
		user.setUpvoteCount(user.getUpvoteCount()+1);
		userRepository.save(user);
	}
	public void upvoteDecrement(String id) {
		User user = userRepository.findByEmail(id);
		user.setUpvoteCount(user.getUpvoteCount()-1);
		userRepository.save(user);
	}
}
