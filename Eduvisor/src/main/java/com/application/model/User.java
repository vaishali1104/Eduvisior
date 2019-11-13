package com.application.model;

import java.util.Arrays;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {

	@Id
	@Email(message = "Email must not be empty")
	@Pattern(regexp="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Email Address not valid")
	private String email;

	@NotBlank(message = "Name must not be empty")
	@Pattern(regexp = "[A-Za-z ]*", message = "Name must only contain alphabets and spaces")
	private String name;

	@NotBlank(message = "Contact number must not be empty")
	@Size(min = 10, max = 10, message = "Contact number must be of 10 digits")
	@Pattern(regexp = "^[^0].*", message="Contact number must not start with 0")
	private String contactNumber;

	@NotBlank(message = "Please select appropriate profession")
	private String profession;

	@NotBlank(message = "Password must not be empty")
	@Pattern(regexp ="(?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{8,40}", message = "Password must contain a lowercase, a uppercase, a digit and it must be greater than 8 characters")
	private String password;

	private boolean isVerified = false;
	private String tokenID = null;
	private String[] areaOfInterest;
	
	private String gender;
	private String dob;
	
	private int upvoteCount = 0;
	
	@Pattern(regexp = "([a-zA-Z] {50})",message="Number is not allowed")
	private String university;
	
	@Pattern(regexp = "(?=.*[a-z])",message="Number is not allowed")
	private String degree;
	
	@Pattern(regexp = "(?=.*[a-z])",message="Number is not allowed")
	private String expertise;
	
	@Pattern(regexp = "(?=.*[a-z])",message="Number is not allowed")
	private String currentUniversity;
	
	public String getCurrentUniversity() {
		return currentUniversity;
	}

	public void setCurrentUniversity(String currentUniversity) {
		this.currentUniversity = currentUniversity;
	}

	public String getGender() {
		return gender;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String[] getAreaOfInterest() {
		return areaOfInterest;
	}

	public void setAreaOfInterest(String[] areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public int getUpvoteCount() {
		return upvoteCount;
	}

	public void setUpvoteCount(int upvoteCount) {
		this.upvoteCount = upvoteCount;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", contactNumber=" + contactNumber + ", profession="
				+ profession + ", password=" + password + ", isVerified=" + isVerified + ", tokenID=" + tokenID
				+ ", areaOfInterest=" + Arrays.toString(areaOfInterest) +", gender=" + gender + ", dob= " 
				+ dob +	", university= "+ university + ", degree= " + degree + ",expertise= " + expertise + "]" ;
	}
	
}
