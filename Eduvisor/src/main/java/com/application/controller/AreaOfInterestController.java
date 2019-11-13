package com.application.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.application.model.User;
import com.application.service.AreaOfInterestService;
import com.application.service.UserService;

@Controller
public class AreaOfInterestController {

	@Autowired
	private AreaOfInterestService aoiService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/interest")
	public String interestPage(Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			return "redirect:";
		}
		List<String> interest = aoiService.listOfInterests();
		model.addAttribute("interestList", interest);
		model.addAttribute("user1", new User());
		model.addAttribute("user", request.getSession().getAttribute("user"));
		return "interests";
	}
	
	@RequestMapping(value="/updateInterest", method=RequestMethod.POST)
	public String interestUpdate(@ModelAttribute("user1") User user, Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			return "redirect:";
		}
		//System.out.println(user.getEmail());
		userService.updateAreaOfInterest((User) request.getSession().getAttribute("user"), user.getAreaOfInterest());
		return "redirect:";
	}
	
}
