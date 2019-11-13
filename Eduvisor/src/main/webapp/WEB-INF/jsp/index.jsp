<%@page import="com.application.model.Post"%>
<%@page import="java.util.List"%>
<%@page import="java.time.Period"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="include/header.jsp" />
<style>
input[type=checkbox] {
	display: none;
}
label {
	font-weight: normal;
	padding: 5px;
}

:checked+label {
	border: 1px solid black;
	box-shadow: 0 0 5px rgba(81, 203, 238, 1);
	font-weight: bold;
}

li {
	display: flex;
	flex-direction: column;
}

</style>

<section class="container main-content">
	<br> <br>
	<div class="row">
		<aside class="col-md-3 sidebar">
			<forms:form modelAttribute="filter" method="POST">
				<div class="widget widget_stats">
					<h3 class="widget_title">
						Filter <input type="submit" value="&#10003;"
							class="color small button f_right margin_0">
					</h3>
					<div class="ul_list ul_list-icon-ok">
						<ul>
							<%
								int i = 0;
							%>
							<c:forEach items="${interestList}" var="listItem">
								<%
									i++;
											List<?> obj = (List<?>) request.getAttribute("setFilter");
								%>
								<li><input type="checkbox" name="name" id="name<%=i%>"
									value="${listItem}"
									<% out.print(obj == null ? "" : obj.isEmpty() ? "" : obj.contains(pageContext.getAttribute("listItem")) ? "checked" : ""); %>><label
									for="name<%=i%>">${listItem}</label></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</forms:form>
		</aside>
		<!-- End sidebar -->
		<div class="col-md-9">

			<div class="tabs-warp question-tab">
				<div class="tab-inner-warp">
					<div class="tab-inner">
						<c:if test="${not empty postSuccess}">
							<div class="alert-message warning">
								<i class="icon-exclamation-sign"></i>
								<p>
									<span>Your Question posted.</span><br> ${postSuccess}
								</p>
							</div>
						</c:if>
						<c:forEach var="postItems" items="${list}">
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
							<article class="question question-type-normal">
								<h2>
									<a href="/postDetail?s=${postItems.id}">${postItems.title}</a>
								</h2>
						<!-- 		<a class="question-report" href="#">${postItems.category}</a>
							 -->	
								<div class="question-report">
									<i class="icon-question-sign"></i><span> &nbsp; ${postItems.category}</span>
								</div>
								<div class="question-author">
									<!-- <a href="" original-title="ahmed"
										class="question-author-img tooltip-n"><span></span><img
										alt="" src="../ask-me/images/demo/avatar.png"></a>
										<span><i class="icon-question-sign"></i></span> -->
										
										<img alt="" src="https://www.kisspng.com/png-question-mark-computer-icons-exclamation-mark-desk-5421437/"
													height="100%" width="100%">
								</div>
								<div class="question-inner">
									<div class="clearfix"></div>
									<p class="question-desc">
									<%
									Post desc = (Post) pageContext.getAttribute("postItems");
									String descrip = desc.getDescription();
									if(descrip == null || descrip == "" || descrip.length() == 0)
										out.print("");
									else
										out.print(descrip.substring(0, descrip.length() > 50 ? 49 : descrip.length()-1) + ".....");
									%>
									</p>
									<span class="question-date"><i class="icon-time"></i>
									<%
 										out.print(time1.getDays() + " Days ago");
 									%>
 									</span>
 									<span class="question-comment"><a href="/postDetail?s=${postItems.id}">
 										<i class="icon-comment"></i><%=i%> Answer</a>
 									</span>
									<div class="clearfix"></div>
								</div>
							</article>
						</c:forEach>
						<!-- <a href="#" class="load-questions"><i class="icon-refresh"></i>Load
							More Questions</a> -->
					</div>
				</div>
			</div>
			<!-- End page-content -->
		</div>
		<!-- End main -->
	</div>
	<!-- End row -->
</section>
<!-- End container -->
<!-- Inclusion of footer  -->
<jsp:include page="include/footer.jsp" />


<!-- Inclusion of Script  -->
<jsp:include page="include/script.jsp" />