package com.application.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.application.model.User;

import com.application.repository.UserRepository;

@Service
public class NotificationService {

//private JavaMailSender javaMailSender;
	
	@Autowired
	public UserRepository userrepository;
	
	public User findUserByEmail(String email) {
	    return userrepository.findByEmail(email);
	}
	
	public User registerUser(User user) {
		User u = new User();
		
		u.setName(user.getName());
		u.setPassword(user.getPassword());
		u.setEmail(user.getEmail());
		u.setContactNumber(user.getContactNumber());
		u.setProfession(user.getProfession());
		u.setVerified(user.isVerified());
		u.setTokenID(user.getTokenID());
		userrepository.save(u);
		
		return u;
	}
	
	public User getVerificationToken(String t) {
		User user = userrepository.findByTokenID(t);
		if(user != null)
			return user;
		else
			return null;
	}
	
	public void enableRegisteredUser(User user) {
		userrepository.save(user);
	}
	
//	@Autowired
//	public NotificationService(JavaMailSender javaMailSender) {
//		this.javaMailSender = javaMailSender;
//	}
//	
//	public void sendNotification(User user) throws MailException {
//		SimpleMailMessage mail = new SimpleMailMessage();
//		mail.setTo("ojhaharsh7@gmail.com");
//		mail.setFrom("edunetwork000@gmail.com");
//		mail.setSubject("Confirmation mail");
//		mail.setText("EduVisor - Please verify your registration ");
//		
//		javaMailSender.send(mail);
//	}

}
