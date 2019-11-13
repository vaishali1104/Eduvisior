package com.application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.model.Comment;
import com.application.model.Post;
import com.application.model.User;
import com.application.service.AreaOfInterestService;
import com.application.service.PostService;
import com.application.service.UserService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userServices;

	@Autowired
	private AreaOfInterestService areaservice;

	@RequestMapping(value = "/addQuestion", method = RequestMethod.GET)
	public String viewAllPosts(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			model.addAttribute("loggedinuser", new String("Please Login first."));
			return "redirect:";
		}
		List<String> categories = areaservice.listOfInterests();
		model.addAttribute("question", new Post());
		model.addAttribute("categories", categories);
		return "/ask_question";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public void ajaxBody(@RequestParam String id, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		response.getWriter().println(id);
	}

	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
	public String post(@Valid @ModelAttribute("question") Post post, BindingResult bindingResult,
			HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("user") == null) {
			model.addAttribute("loggedinuser", new String("Please Login first."));
			return "redirect:";
		} else if (bindingResult.hasErrors()) {
			List<String> categories = areaservice.listOfInterests();
			model.addAttribute("categories", categories);
			model.addAttribute("errors", bindingResult);
			return "ask_question";
		}
		// set session email to post's email
		User user = (User) request.getSession().getAttribute("user");
		post.setEmail(user.getEmail());

		// save post to database
		Post post2 = postService.setposts(post);

		// if post isn't saved then show error
		if (post2 == null) {
			model.addAttribute("post", new Post());
			model.addAttribute("postError", new String("Unexpected Error! Please try again later."));
			return "redirect:";
		}
		
		// Tell user to refresh their feed to view their post.
		model.addAttribute("postSuccess", new String("Refresh your feed!"));
		return "redirect:view_post";
	}

	// View post
	@RequestMapping(value = "/view_post", method = RequestMethod.GET)
	public String viewPost(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "redirect:login";
		} else {
			User user = (User) request.getSession().getAttribute("user");
			model.addAttribute("postValue", postService.display(user.getEmail()));
			return "view_post";
		}
	}

	@RequestMapping(value = "/postDetail", method = RequestMethod.GET)
	public String postDetailPage(@RequestParam("s") String id, Model model) {
		Post post = postService.onePost(id);
		List<Comment> commentlist = post.getComments();
		
		if(commentlist!=null) 
		{
			List<Comment> sorted = commentlist.stream().sorted(Comparator.comparing(Comment::getUpvote_count).reversed()).collect(Collectors.toList());
			post.setComments(sorted);
		}
		
		model.addAttribute("post", post);
		model.addAttribute("commentform", new Comment());
		model.addAttribute("userUploaded", userServices.getUserByMail(post.getEmail()));
		return "postDetail";
	}

	@RequestMapping(value = "/postDetail", method = RequestMethod.POST)
	public String comment(@RequestParam("s") String id, @Valid @ModelAttribute("commentform") Comment comment,
			Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			/* return "redirect:"; */
			return "redirect:login";
		}
		Post post = postService.onePost(id);
		User user = (User) request.getSession().getAttribute("user");
		comment.setEmail(user.getEmail());
		post = postService.comment(post, comment);
		
		if (post == null) {
			model.addAttribute("message", new String("Unexpected error! Please try again later"));
			return "postDetail?s=" + id;
		} else {
			return "redirect:postDetail?s=" + id;
		}
	}

	// update post
	@RequestMapping(value = "/updatepost", method = RequestMethod.GET)
	public String updatePostPage(@RequestParam("s") String id, Model model, HttpServletRequest request) {

		// check if user logged in or not.
		if (request.getSession().getAttribute("user") == null) {
			return "redirect:login";
		}

		List<String> categories = areaservice.listOfInterests();
		// fetch whole post data by given id in url.
		model.addAttribute("post", postService.onePost(id));
		model.addAttribute("categories", categories);
		return "update_post";
	}

	@RequestMapping(value = "/updatepost", method = RequestMethod.POST)
	public String updatePost(@RequestParam("s") String id, @Valid @ModelAttribute("post") Post post,
			BindingResult bindingResult, Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "redirect:login";
		}
		// Check : Title and description cannot be blank.
		if (post.getDescription() == "" || post.getTitle() == "" || post.getCategory() == "") {
			return "redirect:updatepost?s=" + id;
		}
		Post post1 = postService.onePost(id);
		post.setId(id);
		User user = (User) request.getSession().getAttribute("user");
		post.setEmail(user.getEmail());
		Post post2 = postService.updatepost(post, post1);

		// Check if any error occurs in updating.
		if (post2 == null) {
			return "view_post";
		}
		model.addAttribute("postUpdateSuccess", new String("Refresh your feed!"));
		return "redirect:postDetail?s=" + id;
	}

	// Delete post
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteById(@RequestParam("id") String id, Post post, HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "redirect:login";
		} else {
			postService.deletePost(id);
			return "redirect:view_post";
		}
	}

	@RequestMapping(value = "/upVote", method = RequestMethod.GET)
	public String UpVote(@RequestParam("id") String cid, @RequestParam("post") String post_id, Model model,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "redirect:login";
		}
		Post post = postService.onePost(post_id);
		User user = (User) request.getSession().getAttribute("user");

		List<Comment> commentList = post.getComments();
		Comment comment = new Comment();
		for (Comment c : commentList) {
			if (c.getId().equals(cid)) {
				comment = c;
			}
		}
		if(comment.getEmail().equals(user.getEmail())) {
			return "redirect:postDetail?s=" + post_id;
		}
		
		List<String> a = comment.getUpvote_list() == null ? new ArrayList<>() : comment.getUpvote_list();
		List<String> b = comment.getDownvote_list() == null ? new ArrayList<>() : comment.getDownvote_list();

		if (a == null || !(a.contains(user.getEmail()))) {
			commentList.remove(comment);
			if(b.contains(user.getEmail())) {
				b.remove(user.getEmail());
				comment.setDownvote_list(b);
				comment.setDownvote_count(comment.getDownvote_count() - 1);
			}
			comment.setUpvote_count(comment.getUpvote_count() + 1);
			a.add(user.getEmail());
			comment.setUpvote_list(a);
			postService.upvoteIncrement(comment.getEmail());
			commentList.add(comment);
		}
		post.setComments(commentList);
		postService.setposts(post);
		
		return "redirect:postDetail?s=" + post_id;
	}
	
	@RequestMapping(value = "/downVote", method = RequestMethod.GET)
	public String DownVote(@RequestParam("id") String cid, @RequestParam("post") String post_id, Model model,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "redirect:login";
		}
		Post post = postService.onePost(post_id);
		User user = (User) request.getSession().getAttribute("user");

		List<Comment> commentList = post.getComments();
		Comment comment = new Comment();
		for (Comment c : commentList) {
			if (c.getId().equals(cid)) {
				comment = c;
			}
		}

		if(comment.getEmail().equals(user.getEmail())) {
			return "redirect:postDetail?s=" + post_id;
		}
		
		List<String> a = comment.getDownvote_list() == null ? new ArrayList<>() : comment.getDownvote_list();
		List<String> b = comment.getUpvote_list() == null ? new ArrayList<>() : comment.getUpvote_list();

		if (a == null || !(a.contains(user.getEmail()))) {
			commentList.remove(comment);
			if(b.contains(user.getEmail())) {
				postService.upvoteDecrement(comment.getEmail());
				b.remove(user.getEmail());
				comment.setUpvote_list(b);
				comment.setUpvote_count(comment.getUpvote_count() - 1);
			}
			comment.setDownvote_count(comment.getDownvote_count() + 1);
			a.add(user.getEmail());
			comment.setDownvote_list(a);
			commentList.add(comment);
		}
		post.setComments(commentList);
		postService.setposts(post);
		return "redirect:postDetail?s=" + post_id;
	}
	
	@RequestMapping(value="/deleteComment", method = RequestMethod.GET)
	public String commentDelete(@RequestParam("com_id") String comment_id, @RequestParam("post_id") String post_id, HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			return "redirect:login";
		}
		
		Post post = postService.onePost(post_id);
		List<Comment> commentList = post.getComments();
		Comment comment = new Comment();
		for (Comment c : commentList) {
			if (c.getId().equals(comment_id)) {
				comment = c;
			}
		}
		commentList.remove(comment);
		post.setComments(commentList);
		postService.setposts(post);
		
		return "redirect:postDetail?s="+post_id;
	}
}
