package com.application.model;

import javax.validation.constraints.Pattern;

public class ChangePassword {

	@Pattern(regexp ="(?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{8,40}", message = "Invalid password")
	String Password;
	
	@Pattern(regexp ="(?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{8,40}", message = "Invalid password")
	String ConfirmPassword;
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getConfirmPassword() {
		return ConfirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}
	
}
