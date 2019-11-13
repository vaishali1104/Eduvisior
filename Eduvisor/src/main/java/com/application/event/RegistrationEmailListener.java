package com.application.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.application.controller.UserController;
import com.application.model.User;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnRegistrationSuccessEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationSuccessEvent event) {
		User user = event.getUser();
		
        try {
        	String token = user.getTokenID();
        	String recipient = user.getEmail();
    		String subject = "Registration Confirmation";
            String url 
              = event.getAppUrl() + "/confirmRegistration?token=" + token;
            String message = "You have registered successfully.click the link to confirm your account.";
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipient);
            email.setSubject(subject);
            email.setText(message + "https://eduvisor.herokuapp.com" + url);
            mailSender.send(email);
		}catch(MailException e) {
			logger.info("Error Sending Email" + e.getMessage());
		}
		
	}
	
	
}
