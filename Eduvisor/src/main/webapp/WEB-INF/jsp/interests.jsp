<%@page import="java.util.Arrays"%>
<%@page import="com.application.model.User"%>
<%@page import="java.util.List"%>
<%@page import="com.application.model.AreaOfInterest"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form"%>

<!-- HEADER CODE -->
<jsp:include page="include/header.jsp"></jsp:include>
<!-- HEADER CODE -->

<div class="breadcrumbs">
	<section class="container">
		<div class="row">
			<div class="col-md-12">
				<h1>Interests</h1>
			</div>
			<div class="col-md-12">
				<div class="crumbs">
					<a href="index">Home</a> <span class="crumbs-span">/</span> <span
						class="current">Interests</span>
				</div>
			</div>
		</div>
		<!-- End row -->
	</section>
	<!-- End container -->
</div>
<!-- End breadcrumbs -->

<section class="container main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="page-content">
				<p>
					<%
						User user = (User) session.getAttribute("user");
					%>
					<forms:form modelAttribute="user1" action="/updateInterest">
						<%
							if (user.getAreaOfInterest() != null) {
									List<String> lis = Arrays.asList(user.getAreaOfInterest());
						%>
						<c:forEach var="listValue" items="${interestList}">
							<div class="col-md-4" style="margin-bottom: 10%;">
								<div class="page-content page-shortcode">
									<%
										if (lis.contains(pageContext.getAttribute("listValue"))) {
									%>
									<forms:checkbox path="areaOfInterest" value="${listValue}"
										label="${listValue}" checked="true" />
									<%
										} else {
									%>
									<forms:checkbox path="areaOfInterest" value="${listValue}"
										label="${listValue}" cssStyle="display: hidden;" />
									<%
										}
									%>
									<div class="box_icon">
										<span class="t_center icon_i"><span icon_size="120"><i
												i_color="#1abc9c" i_hover="#34495e"
												class="icon-tint color_default"
												style="font-size: 60px; color: rgb(26, 188, 156);"></i></span></span>
										<div class="t_center">
											<h3>${listValue}</h3>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						<%
							} else {
						%>
						<c:forEach var="listValue" items="${interestList}">
							<div class="col-md-4" style="margin-bottom: 2%;">
								<div class="page-content page-shortcode">
									<forms:checkbox path="areaOfInterest" value="${listValue}" cssStyle="" />
									<div class="box_icon">
										<span class="t_center icon_i"><span icon_size="120"><i
												i_color="#1abc9c" i_hover="#34495e"
												class="icon-tint color_default"
												style="font-size: 60px; color: rgb(26, 188, 156);"></i></span></span>
										<div class="t_center">
											<h3>${listValue}</h3>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						<%
							}
						%>
						<div class="footer-bottom-1">
							<div class="col-md-3">
								<p class="form-submit login-submit">
									<input type="submit" value="Add"
										class="button color small login-submit submit">
								</p>
							</div>
							<div class="col-md-3">
								<p class="form-submit login-submit">
									<a href="/" class="button color small login-submit submit">Skip for now</a>
								</p>
							</div>
						</div>
					</forms:form>
				</p>
			</div>
		</div>
	</div>
</section>
<br />
<br />
<br />
<!-- FOOTER CODE -->
<jsp:include page="include/footer.jsp"></jsp:include>
<!-- FOOTER CODE -->


<!-- SCRIPT CODE -->
<jsp:include page=""></jsp:include>
<!-- SCRIPT CODE -->