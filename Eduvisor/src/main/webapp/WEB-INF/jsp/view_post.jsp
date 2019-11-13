<%@page import="java.time.Period"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- HEADER INCLUSION CODE -->

<jsp:include page="include/header.jsp"></jsp:include>
<div class="breadcrumbs">
	<section class="container">
		<div class="row">
			<div class="col-md-12">
				<h1>User Questions</h1>
			</div>
			<div class="col-md-12">
				<div class="crumbs">
					<a href="index">Home</a> <span class="crumbs-span">/</span><span class="current">User
						Questions</span>
				</div>
			</div>
		</div>
		<!-- End row -->
	</section>
	<!-- End container -->
</div>
<!-- End breadcrumbs -->
<!-- HEADER INCLUSION CODE -->
<section class="container main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="clearfix"></div>
			<div class="page-content page-content-user">
			
				<div class="user-questions">
					<c:forEach var="postItems" items="${postValue}">
						<%
							int i = 0;
						%>
						<c:forEach items="${postItems.comments}" var="comment">
							<%
								//pageContext.setAttribute("i", (Integer.parseInt((String) pageContext.getAttribute("i")) + 1));
										i++;
							%>
						</c:forEach>
						<c:set var="postDate" value="${postItems.postedDate}" />
						<%
							LocalDate now1 = LocalDate.now();
								Period time1 = Period.between((LocalDate) pageContext.getAttribute("postDate"), now1);
						%>
						<article class="question user-question">
							<h3>
								<a href="/postDetail?s=${postItems.id}">${postItems.title}</a>
							</h3>
							<div class="question-type-main">
								<i class="icon-question-sign"></i>${postItems.category}
							</div>
							<div class="question-content">
								<div class="question-bottom">
									<span class="question-category">
										<i class="icon-folder-close"></i>${postItems.category}</span> 
										<span
										class="question-date"><i class="icon-time"></i>
										<%
 											out.print(time1.getDays() + " Days ago");
 										%>
 										</span>
 										<span class="question-comment"><a href="#">
 										<i class="icon-comment"></i><%=i%> Answers</a></span>
 										<!-- <a class="question-reply" href="#"></a> -->
								</div>
							</div>
						</article>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- FOOTER INCLUSION CODE -->

<jsp:include page="include/footer.jsp"></jsp:include>

<!-- FOOTER INCLUSION CODE -->

<!-- SCRIPT INCLUSION CODE -->

<jsp:include page="include/script.jsp"></jsp:include>

<!-- SCRIPT INCLUSION CODE -->