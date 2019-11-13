package com.application.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.application.controller.UserController;
import com.application.model.Login;
import com.application.model.User;
import com.application.repository.UserRepository;

@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender mailSender;

	public User create(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		String token = UUID.randomUUID().toString();
		user.setTokenID(token);
		User user1 = userRepository.save(user);
		return user1;
	}

	public User getUser(Login user) {
		User user1 = userRepository.findByEmail(user.getEmail(), user.getPassword());
		return user1;
	}

	public User getUser(User user) {
		User user1 = userRepository.findByEmail(user.getEmail());
		return user1;
	}

	public User getUserByMail(String email) {
		User user1 = userRepository.findByEmail(email);
		return user1;
	}

	public User updateAreaOfInterest(User user1, String[] interest) {
		user1.setAreaOfInterest(interest);
		return userRepository.save(user1);
	}

	public String forgetPasswordMail(String emailId, String appUrl, String token) {
		try {
			String recipient = emailId;
			String subject = "Password reset";
			String url = appUrl + "/changePassword?token=" + token;
			String message = "To reset your password, click the link below:\n";
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(recipient);
			email.setSubject(subject);
			email.setText(message + "http://localhost:8080" + url);
			mailSender.send(email);
		} catch (MailException e) {
			logger.info("Error Sending Email" + e.getMessage());
			return "";
		}
		return "sent";
	}

	public boolean checkAoI(User user) {
		// userRepository.findByAreaOfInterestExists(true);
		return true;
	}

	public User updateProfile(User user, User loggedinuser)
	{	
		  if(user.getEmail().equals(loggedinuser.getEmail())) 
		  {
			  loggedinuser.setName(user.getName());
			  loggedinuser.setContactNumber(user.getContactNumber());
			  loggedinuser.setDob(user.getDob());
			  loggedinuser.setGender(user.getGender());
			  loggedinuser.setUniversity(user.getUniversity());
			  loggedinuser.setCurrentUniversity(user.getCurrentUniversity());
			  loggedinuser.setExpertise(user.getExpertise());
			  loggedinuser.setDegree(user.getDegree());
			  user = userRepository.save(loggedinuser); 
		  }
		return user;
	}
}
