<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form"%>
<!-- ALL ELEMENTS WILL BE PLACED IN THE HEADER.JSP FILE
SO ONLY THING YOU NEED TO DO IS TO CREATE SECTION WITH CLASS CONTAINER AND MAIN-CONTENT

SAME AS FOOTER YOU ONLY NEED TO ADD FOOTER.JSP TO INCLUDE FOOTER

AND YOU NEED TO ADD SCRIPTS.JSP TO ADD JAVASCRIPTS TO YOUR CODE

YOU NEED TO ADD ALL THE 3 INCLUDE FILES THEN ONLY YOU CAN USE PAGE PROPERLY WITH TEMPLATE
-->



<!-- REMOVE HEADER STARTS HERE -->
<jsp:include page="include/header.jsp" />
<!-- REMOVE HEADER ENDS HERE -->
<div class="breadcrumbs">
	<section class="container">
		<div class="row">
			<div class="col-md-12">
				<h1>Login</h1>
			</div>
			<div class="col-md-12">
				<div class="crumbs">
					<a href="index">Home</a> <span class="crumbs-span">/</span> <span
						class="current">Login</span>
				</div>
			</div>
		</div>
		<!-- End row -->
	</section>
	<!-- End container -->
</div>
<!-- End breadcrumbs -->

<section class="container main-content">
	<c:if test="${not empty Error}">
		<div class="alert-message warning">
			<i class="icon-exclamation-sign"></i>
			<p>
				<span>Login Error.</span><br> ${postSuccess}
			</p>
		</div>
	</c:if>
	<div class="login">
		<div class="row">
			<!-- LOGIN    (STARTS HERE) -->
			<div class="col-md-6">
				<div class="page-content">
					<h2>Login</h2>
					<div class="form-style form-style-3">

						<forms:form method="POST" modelAttribute="login" action="login">
							<div class="form-inputs clearfix">
								<c:if test="${not empty loginError}">
									<div class="alert-message warning">
										<i class="icon-exclamation-sign"></i>
										<p>
											<span>Login Error</span><br> ${loginError}
										</p>
									</div>
								</c:if>

								<p class="login-text">
									<forms:input type="text" path="email" required="required" />
									<i class="icon-user"></i>
								</p>
								<c:if test="${not empty emailHasBindError}">
									<p>
									<blockquote>
										<forms:errors path="email" />
									</blockquote>
									<!-- </p> -->
								</c:if>
								<p class="login-password">
									<forms:input type="password" path="password"
										required="required" />
									<i class="icon-lock"></i>
								</p>
								<c:if test="${not empty passwordHasBindError}">
									<p>
									<blockquote>
										<forms:errors path="password" />
									</blockquote>
									<!-- </p> -->
								</c:if>
							</div>
							<p class="form-submit login-submit">
								<input type="submit" value="Log in"
									class="button color small login-submit submit">
							</p>
							<p>
								<br> <a class="color small button f_right"
									href="/forgetPassword">Forget</a>
							</p>

						</forms:form>
					</div>
				</div>
				<!-- End page-content -->
			</div>
			<!-- End col-md-6 -->
			<!-- LOGIN    (ENDS HERE) -->



			<!-- REGISTRATION  (STARTS HERE) -->
			<div class="col-md-6">
				<div class="page-content">
					<h2>Register Now</h2>
					<div class="form-style form-style-3">
						<forms:form method="POST" modelAttribute="register"
							action="register">
							<div class="form-inputs clearfix">
								<c:if test="${not empty registerError}">
									<div class="alert-message warning">
										<i class="icon-exclamation-sign"></i>
										<p>
											<span>Registration Error</span><br> ${registerError}
										</p>
									</div>
								</c:if>
								<c:if test="${not empty registerSuccess}">
									<div class="alert-message success">
										<i class="icon-ok"></i>
										<p>
											<span>Registration Success</span><br> ${registerSuccess}
										</p>
									</div>
								</c:if>

								<p>
									<label class="required">Name<span>*</span></label>
									<forms:input type="text" path="name" required="required" />
									<c:if test="${not empty nameHasBindError}">
										<blockquote>
											<forms:errors path="name"
												cssStyle="color: black; font-size: 15px;" />
										</blockquote>
									</c:if>
								</p>
								<p>
									<label class="required">E-Mail<span>*</span></label>
									<forms:input type="email" path="email" />
									<c:if test="${not empty emailHasBindError}">
										<blockquote>
											<forms:errors path="email"
												cssStyle="color: black; font-size: 15px;"
												required="required" />
										</blockquote>
									</c:if>
								</p>
								<p>
									<label class="required">Contact Number<span>*</span></label>
									<forms:input type="text" path="contactNumber"
										required="required" />
									<c:if test="${not empty contactNumberHasBindError}">
										<blockquote>
											<forms:errors path="contactNumber"
												cssStyle="color: black; font-size: 15px;" />
										</blockquote>
									</c:if>
								</p>
								<p>
									<label class="required">Current Profession<span>*</span></label>
									<forms:select path="profession" required="required">
										<forms:option label="Select Profession" value="" />
										<forms:option value="Student" />
										<forms:option value="Professor" />
									</forms:select>
									<c:if test="${not empty professionHasBindError}">
										<blockquote>
											<forms:errors path="profession"
												cssStyle="color: black; font-size: 15px;" />
										</blockquote>
									</c:if>
								</p>
								<p>
									<label class="required">Password<span>*</span></label>
									<forms:input type="password" path="password"
										required="required" />
									<c:if test="${not empty passwordHasBindError}">
										<blockquote>
											<forms:errors path="password"
												cssStyle="color: black; font-size: 15px;" />
										</blockquote>
									</c:if>
								</p>
							</div>
							<p class="form-submit">
								<input type="submit" value="Signup"
									class="button color small submit">
							</p>
						</forms:form>
					</div>
				</div>
				<!-- End page-content -->
			</div>
			<!-- End col-md-6 -->
			<!-- REGISTRATION    (ENDS HERE) -->

		</div>
		<!-- End row -->
	</div>
	<!-- End login -->
</section>
<!-- End container -->

<script>
	function init() {
		// Clear forms here
		document.getElementById("password").value = "";
	}
	window.onload = init;
</script>

<br>
<br>
<!-- REMOVE FOOTER STARTS HERE -->
<jsp:include page="include/footer.jsp" />
<!-- REMOVE FOOTER ENDS HERE -->

<!-- REMOVE SCRIPTS STARTS HERE -->
<jsp:include page="include/script.jsp"></jsp:include>
<!-- REMOVE FOOTER ENDS HERE -->
