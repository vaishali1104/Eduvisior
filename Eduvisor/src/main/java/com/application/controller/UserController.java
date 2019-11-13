package com.application.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.application.event.OnRegistrationSuccessEvent;
import com.application.model.ChangePassword;
import com.application.model.Filter;
import com.application.model.Login;
import com.application.model.Mail;
import com.application.model.Post;
import com.application.model.User;
import com.application.service.AreaOfInterestService;
import com.application.service.NotificationService;
import com.application.service.PostService;
import com.application.service.UserService;

@Controller
public class UserController implements WebMvcConfigurer {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private AreaOfInterestService aoiService;

	@Autowired
	private NotificationService notificationservice;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Model model, HttpServletRequest request) {
		List<String> interest = aoiService.listOfInterests();
		model.addAttribute("interestList", interest);
		if (request.getSession().getAttribute("user") != null) {
			User user = (User) request.getSession().getAttribute("user");
			interest = aoiService.listOfInterests();
			if(interest == null || interest.isEmpty())
				return "redirect:interest";
			model.addAttribute("interestList", interest);
			model.addAttribute("list", postService.filterFunction(Arrays.asList(user.getAreaOfInterest())));
		}
		else
		{
			model.addAttribute("list", postService.allPost());
		}
		model.addAttribute("filter", new Filter());
		return "index";
	}

	@RequestMapping("/index")
	public String home() {
		return "redirect:";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String filterHomePage(Model model, @ModelAttribute("filter") Filter list) {
		List<String> interest = aoiService.listOfInterests();
		model.addAttribute("interestList", interest);
		List<Post> posts = new ArrayList<>();
		try {
			if (list.getName().isEmpty() == false) {
				posts = postService.filterFunction(list.getName());
				model.addAttribute("setFilter", list.getName());
			}
		}
		catch(NullPointerException ex) {
			posts = postService.allPost();
		}
		model.addAttribute("list", posts);
		model.addAttribute("filter", new Filter());
		return "index";
	}

	@RequestMapping("/logout")
	public String logoutFunciton(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null)
			request.getSession().setAttribute("user", null);
		return "redirect:";
	}

	@RequestMapping(value = "/login")
	public String loginPage(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			return "redirect:";
		}
		model.addAttribute("login", new Login());
		model.addAttribute("register", new User());
		return "login";
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute("login") Login user, BindingResult bindingResult, Model model,
			HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			return "redirect:";
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", bindingResult);
			model.addAttribute("register", new User());
			return "login";
		}
		String pass = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16); // Strength set as 16
		User user1 = userService.getUserByMail(user.getEmail());
		if (user1 != null) {
			if (encoder.matches(pass, user1.getPassword()) == false) {
				user.setPassword(null);
				model.addAttribute("register", new User());
				model.addAttribute("loginError", new String("Invalid Password"));
				return "login";
			} else if (user1.isVerified() == true) {
				request.getSession().setAttribute("user", user1);
				try {
					if (user1.getAreaOfInterest().length == 0) {
						return "redirect:interest";
					}
				} catch (NullPointerException ex) {
					return "redirect:interest";
				}
				return "redirect:";
			} else {
				model.addAttribute("register", new User());
				model.addAttribute("loginError", new String("You are not verified user, so kindly check your mail."));
				return "login";
			}
		} else {
			user.setPassword(null);
			model.addAttribute("register", new User());
			model.addAttribute("loginError", new String("No account with this mail found."));
			return "login";
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			return "redirect:";
		}
		model.addAttribute("login", new Login());
		model.addAttribute("register", new User());
		return "login";
	}
	
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
	public String forgetPage(Model model, HttpServletRequest request){
		if(request.getSession().getAttribute("user") != null) {
			return "redirect:";
		}
		model.addAttribute("mailObject", new Mail());
		return "forget_password";
	}
	
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public String forgetPasswordPage(@Valid @ModelAttribute("mailObject") Mail email,BindingResult bindingResult,Model model, HttpServletRequest request) {
		User user = userService.getUserByMail(email.getEmail());
		if (user == null) {
			model.addAttribute("InvalidMail", new String("We didn't find an account for that e-mail address."));
			return "forget_password";
		}
		String token = user.getTokenID();
		String appUrl = request.getContextPath();
		if(userService.forgetPasswordMail(email.getEmail(), appUrl, token) == "") {
			model.addAttribute("MailError", new String("Some error occured in sending mail. Please try again letter."));
			return "forget_password";
		}
		model.addAttribute("MailSuccess", new String("Reset Password Link sent to your Mail ID."));
		return "forget_password";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePasswordPage(@RequestParam("token") String token,Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("user") != null) {
			return "redirect:";
		}
		model.addAttribute("Changepassword", new ChangePassword());
		return "change_password";
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String changePasswordForLoggedinUser(Model model, HttpServletRequest request) {
		
		if(request.getSession().getAttribute("user") == null)
			 return "redirect:login";
		
		model.addAttribute("Changepassword", new ChangePassword());
		return "change_password";
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String newPassword(@Valid @ModelAttribute("Changepassword")ChangePassword newPassword ,BindingResult bindingResult,Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			return "redirect:login";
		}
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getErrorCount());
			model.addAttribute("error", bindingResult);
			return "change_password";
		}
		if(!(newPassword.getPassword().equals(newPassword.getConfirmPassword()))) {
			model.addAttribute("PasswordMismatch", new String("Password should be same in both fields."));
			return "change_password";
		}
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)
			 return "redirect:login";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16); // Strength set as 16
		user.setPassword(encoder.encode(newPassword.getPassword()));
		notificationservice.enableRegisteredUser(user);
		model.addAttribute("PasswordChanged", new String("Please login."));
		return "redirect:";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String newPasswordPage(@RequestParam("token") String token,@Valid @ModelAttribute("Changepassword")ChangePassword newPassword ,BindingResult bindingResult,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("user") != null) {
			return "redirect:";
		}
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getErrorCount());
			model.addAttribute("error", bindingResult);
			return "change_password";
		}
		if(!(newPassword.getPassword().equals(newPassword.getConfirmPassword()))) {
			model.addAttribute("PasswordMismatch", new String("Password should be same in both fields."));
			return "change_password";
		}
		User user = notificationservice.getVerificationToken(token);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16); // Strength set as 16
		user.setPassword(encoder.encode(newPassword.getPassword()));
		notificationservice.enableRegisteredUser(user);
		
		model.addAttribute("PasswordChanged", new String("Please login."));
		return "redirect:login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("register") User user, BindingResult bindingResult,
			WebRequest request, Model model, HttpServletRequest request1) {
		if (request1.getSession().getAttribute("user") != null) {
			return "redirect:";
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult);
			model.addAttribute("login", new Login());
			user.setPassword(null);
			return "login";
		}
		if (userService.getUser(user) == null) {
			User registeredUser = userService.create(user);
			if (registeredUser != null) {
				try {
					String appUrl = request.getContextPath();
					eventPublisher
							.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(), appUrl));
				} catch (Exception re) {
					re.printStackTrace();
//					throw new Exception("Error while sending confirmation email");
				}
				model.addAttribute("login", new Login());
				model.addAttribute("register", new User());
				model.addAttribute("registerSuccess", new String(
						"Registered Successfully, Kindly check your mail to verify your account and Login again"));
				return "login";
			} else {
				model.addAttribute("login", new Login());
				user.setPassword(null);
				model.addAttribute("registerError", new String("Unexpected Error! Please try again later."));
				return "login";
			}
		} else {
			model.addAttribute("login", new Login());
			model.addAttribute("registerError", new String("Account already exist with corresponding email address."));
			return "login";
		}
	}

	@GetMapping("/confirmRegistration")
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
		User verificationToken = notificationservice.getVerificationToken(token);
		if (verificationToken == null || verificationToken.isVerified()) {
			String message = "auth.message.invalidToken";
			model.addAttribute("message", message);
			return "redirect:access-denied";
		}
		verificationToken.setVerified(true);
		notificationservice.enableRegisteredUser(verificationToken);
		return "redirect:login";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String userProfilePage(Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) 
			return "redirect:login";
		User u = userService.getUser((User)request.getSession().getAttribute("user"));
		List<Post> p = postService.display(u.getEmail());
		model.addAttribute("posts", p);
		model.addAttribute("userProfile", u);
		return "viewprofile";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userProfilePageOfOtherUser(@RequestParam("id") String email ,Model model, HttpServletRequest request) {
		User u = userService.getUserByMail(email);
		List<Post> p = postService.display(u.getEmail());
		model.addAttribute("posts", p);
		model.addAttribute("userProfile", u);
		return "viewprofile";
	}
	
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.GET)
	public String updateProfilePage(@RequestParam("data") String email, Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) 
		{
			   return "redirect:login";
		} 
		else
		{
			model.addAttribute("user", request.getSession().getAttribute("user")); 
			return "update_profile";
		}
	}
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String updatePost(@RequestParam("data") String email, @ModelAttribute("user") User user, Model model, HttpServletRequest request,BindingResult bindingResult) 
	{
		if(request.getSession().getAttribute("user") == null) 
		{
			   return "redirect:login";
		}
		
		if(user.getName() == "" || user.getGender()== "") {
			return "redirect:updateProfile?data="+email;
		}

		User loggedinuser = (User) request.getSession().getAttribute("user");
		user.setEmail(loggedinuser.getEmail());
		userService.updateProfile(user, loggedinuser);
		
		model.addAttribute("UpdateSuccess", new String("Your Profile has been updated"));
		return "redirect:profile";
	}
			
}
	
	
