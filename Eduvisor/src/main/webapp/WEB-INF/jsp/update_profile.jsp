<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.application.model.*" %>
<!-- HEADER STARTS HERE -->
<jsp:include page="include/header.jsp" />
<!-- HEADER ENDS HERE -->

<div class="breadcrumbs">
		<section class="container">
			<div class="row">
				<div class="col-md-12">
					<h1>Edit Profile</h1>
				</div>
				<div class="col-md-12">
					<div class="crumbs">
						<a href="index">Home</a>
						<span class="crumbs-span">/</span>
						<span class="current">Edit Profile</span>
					</div>
				</div>
			</div><!-- End row -->
		</section><!-- End container -->
	</div><!-- End bread crumbs -->

<style>
	.page-content .boxedtitle{
		margin-left : 40%;
	}
</style>

	<section class="container main-content" style="margin-left:23%;">
		<div class="row">
			<div class="col-md-9">
				<div class="page-content">
					<div class="boxedtitle page-title"><h2>Edit Profile</h2></div>
					
						<div class="form-style form-style-4">
							<forms:form modelAttribute="user" action="/updateProfile?data=${user.email}">
							
								<c:if test="${not empty UpdateSuccess}">
									<div class="alert-message success">
										<i class="icon-ok"></i>
										<p>
											<span>Updated!</span><br> ${UpdateSuccess}
										</p>
									</div>
								</c:if>
								
									<p>
										<label>Name</label>
										<forms:input type="text" path="name" value="${user.name}"/>
										<forms:errors path="name" cssStyle="color: black; font-size: 15px;" />
									</p>
								
									<p>
										<label>Date of Birth</label>

										<forms:input type="date" path="dob" value="${user.dob}" />
										<forms:errors path="dob"
										cssStyle="color: black; font-size: 15px;" />

									</p>
								
									<p>
									<forms:select path="gender" required="required">
										<forms:option label="Select Gender" value="${ user.gender } " />
										<forms:option value="Male" />
										<forms:option value="Female" />
									</forms:select>
									</p>
		
									<p>
										<label>Contact Number</label>
										<forms:input type="text" path="contactNumber" value="${user.contactNumber}"/>
										<forms:errors path="contactNumber" cssStyle="color: black; font-size: 15px;" />
									</p>
								
								<div><h3>Qualification</h3></div>
			
										<p>
											<label>Graduation University</label>
											<forms:input type="text" path="university"  value="${user.university}"/>
											<forms:errors path="university" cssStyle="color: black; font-size: 15px;" />
										</p>
								
										<p>
											<label>Degree</label>
											<forms:input type="text" path="degree" value="${user.degree}"/>
											<forms:errors path="degree"	cssStyle="color: black; font-size: 15px;" />
										</p>
									
										<% 
												User user = (User) request.getSession().getAttribute("user");
												String current_user=user.getProfession();
												
												if(current_user.equals("Professor"))
												{
						 				%>
									
						
										<p>
											<label>Current University</label>
											<forms:input type="text" path="currentUniversity" value="${user.currentUniversity}" />
											<forms:errors path="currentUniversity" cssStyle="color: black; font-size: 15px;" />
										</p>
								
										<p>
											<label>Expertise</label>
											<forms:input type="text" path="expertise" value="${user.expertise}" />
											<forms:errors path="expertise" cssStyle="color: black; font-size: 15px;" />
										</p>
								
									<%	}	%>
								<p class="form-submit">
									<input type="submit" value="Update" class="button color small login-submit submit">
								</p>
							</forms:form>
						</div>
				</div><!-- End page-content -->
			</div><!-- End main -->
		</div>
	</section>

<!-- SCRIPT INCLUSION CODE -->

<jsp:include page="include/script.jsp"></jsp:include>

<!-- SCRIPT INCLUSION CODE -->
<script>
$(function()
  {
  $("#datepicker").datepicker(
  {
  showOn:"both",
  buttonImage:"image.jpg",
  dateFormat:"yy-mm-dd",
  buttonImageOnly:false,
  minDate:+0, //you do not want to show previous date.
  maxDate:+0   // you do not want to show next day.
  });
 });

</script>

<!-- REMOVE FOOTER STARTS HERE -->
<jsp:include page="include/footer.jsp" />
<!-- REMOVE FOOTER ENDS HERE -->