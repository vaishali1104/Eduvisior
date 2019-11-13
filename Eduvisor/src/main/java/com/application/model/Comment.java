package com.application.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Comment")
public class Comment {

	@Id
	private String id;
	
	private String comment;
	
	private int upvote_count = 0;
	
	private int downvote_count = 0;
	
	private List<String> upvote_list;
	
	private List<String> downvote_list;
	
	private String email;
	
	private LocalDate postedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public int getUpvote_count() {
		return upvote_count;
	}

	public void setUpvote_count(int upvote_count) {
		this.upvote_count = upvote_count;
	}

	public int getDownvote_count() {
		return downvote_count;
	}

	public void setDownvote_count(int downvote_count) {
		this.downvote_count = downvote_count;
	}

	public List<String> getUpvote_list() {
		return upvote_list;
	}

	public void setUpvote_list(List<String> upvote_list) {
		this.upvote_list = upvote_list;
	}

	public List<String> getDownvote_list() {
		return downvote_list;
	}

	public void setDownvote_list(List<String> downvote_list) {
		this.downvote_list = downvote_list;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment=" + comment + ", upvote_count=" + upvote_count + ", downvote_count="
				+ downvote_count + ", upvote_list=" + upvote_list + ", downvote_list=" + downvote_list + ", email="
				+ email + ", postedDate=" + postedDate + "]";
	}
	
	
	
}
