<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.application.model.*" %>
<!DOCTYPE html>
<html lang="en"> 
<head>

	<!-- Basic Page Needs -->
	<meta charset="utf-8">
	<title>Ask me Questions and Answers</title>
	<meta name="description" content="Ask Educational questions and get proper response by experienced professors">
	<meta name="author" content="DA-IICT">
	
	<!-- Mobile Specific Metas -->
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<!-- Main Style -->
	<link rel="stylesheet" href="css/style.css">
	
	<!-- Skins -->
	<link rel="stylesheet" href="css/skins/skins.css">
	
	<!-- Responsive Style -->
	<link rel="stylesheet" href="css/responsive.css">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<!-- Favicons -->
	<link rel="shortcut icon" href="images/favicon.png">
  
</head>
<body>

<!-- <div class="loader">
	<div class="loader_html">
	</div>
</div> -->

<div id="wrap" class="grid_1200">
	
	<header id="header">
		<section class="container clearfix">
			<div class="logo"><a href="index"><img alt="" src="images/logo.png"></a></div>
			<nav class="navigation">
				<ul>
					<li><a href="index">Home</a></li>
					
					<% 
					if(session.getAttribute("user") != null)
					{
						User user = (User) request.getSession().getAttribute("user");
					%>
					<li><a href="addQuestion">Ask Question</a></li>
					<li><a href=""> ${user.name} </a>
						<ul>
							<li><a href="profile">User Profile</a></li>
							<li><a href="view_post">User Questions</a></li>
							<!-- <li><a href="">User Answers</a></li> -->
							<li><a href="updateProfile?data=<%=user.getEmail() %>">Edit Profile</a></li>
							<li><a href="changepassword">Change Password</a></li>
							<li><a href="interest">Area Of Interest</a></li>
						</ul>
					</li>
					<li><a href="/logout">Logout</a></li>
					<%
					}
					else {
					%>
					<li><a href="login">Ask Question</a></li>
					<li><a href="/login">Login / SignUp</a></li>
					<% 
						} 
					%>
				</ul>
			</nav>
		</section><!-- End container -->
	</header><!-- End header -->
</div>