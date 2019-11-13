<%@page import="com.application.model.Comment"%>
<%@page import="com.application.model.Post"%>
<%@page import="com.application.model.User"%>
<%@page import="java.util.List"%>
<%@page import="java.time.Period"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.application.model.*"%>


<!-- HEADER FILE INCLUSION -->
<jsp:include page="include/header.jsp"></jsp:include>
<!-- HEADER FILE INCLUSION -->

<div class="breadcrumbs">
	<section class="container">
		<div class="row">
			<div class="col-md-12">
				<h1>${post.title}</h1>
			</div>
			<div class="col-md-12">
				<div class="crumbs">
					<a href="index">Home</a> <span class="crumbs-span">/</span><span class="current">
						${post.title} </span>
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
			<article class="question single-question question-type-normal">
				<h2>
					<a href="/postDetail?s=${ post.id }"> ${post.title} </a>
				</h2>
				<div class="question-inner">
					<div class="clearfix"></div>
					<div class="question-desc">
						<p>${post.description}</p>
					</div>
					<span class="question-category">
					<i class="icon-folder-close"></i>${post.category}</span>
					 <span class="question-date"><i class="icon-time"></i> 
					 <c:set	var="postDate" value="${post.postedDate}" /> 
							<%
								LocalDate now1 = LocalDate.now();
								Period time1 = Period.between((LocalDate) pageContext.getAttribute("postDate"), now1);
								out.print(time1.getDays() + " Days ago");
							%>
					</span>
					<span class="question-comment"> 
					<i class="icon-comment"></i>
					 <c:set var="i" value="0" /> 
					 <%
								int i = 0;
					 %> 
					 <c:forEach items="${post.comments}" var="comment">
					<%
						//pageContext.setAttribute("i", (Integer.parseInt((String) pageContext.getAttribute("i")) + 1));
						i++;
					%>
					</c:forEach> <%=i%> 
					</span>

					<%
						if (request.getSession().getAttribute("user") != null) {
							User user = (User) request.getSession().getAttribute("user");
							String current_user = user.getEmail();

							Post post = (Post) request.getAttribute("post");
							String post_user = post.getEmail();

							if (current_user.equals(post_user)) {
					%>
					<span> <a class="question-reply"
						href="updatepost?s=${post.id}"><i class="icon-edit"></i>Update</a>
						&emsp;
					</span> <span> <a class="question-reply" onclick="deleteButton()"
						href="#"><i class="icon-remove"></i>Delete</a>
					</span>
					<%
						}
					}
					%>
					<script type="text/javascript">
						function deleteButton() {

							var result = confirm("Do you want to continue?");

							if (result)
							{
								window.location.href = "/delete?id=${post.id}";
							}
							else 
							{
								window.location.href = "/view_post";
							}
						}
					</script>

					<!-- Add Comment button here -->

					<div class="clearfix"></div>
				</div>
			</article>
			<!-- End share-tags -->
			<div class="about-author clearfix">
			    <div class="author-image">
				<a href="/user?id=${userUploaded.email}" original-title="admin" class="tooltip-n"><img alt="" src="../ask-me/images/demo/admin.jpg"></a>
			    </div>
			    <div class="author-bio">
				<a href="/user?id=${userUploaded.email}"><h4>${userUploaded.name}</h4></a>
			    </div>
			</div><!-- End about-author -->
			<div id="commentlist" class="page-content">
				<div class="boxedtitle page-title">
					<h2>
						Answers ( <span class="color"><%=i %></span> )
					</h2>
				</div>
				<ol class="commentlist clearfix">
					<c:forEach items="${post.comments}" var="comment">
						<li class="comment">
							<div class="comment-body clearfix">
								<div class="avatar">
									<img alt="" src="images/demo/avatar.png">
								</div>
								<div id="output"></div>
								<div class="comment-text">
									<div class="author clearfix">
										<div class="comment-author">
											<a href="/user?id=${comment.email}"> ${comment.email} </a>
										</div>
										<div class="comment-vote">
											<ul class="question-vote">
												<li><a
													href="/upVote?id=${comment.id}&post=${post.id}"
													class="question-vote-up" title="Like"></a></li>
												<li>
													${comment.upvote_count}
												</li>
												<li><a
													href="/downVote?id=${ comment.id }&post=${post.id}"
													class="question-vote-down" title="Dislike"
													onclick="downvote()"></a></li>
											</ul>
										</div>
										<div class="comment-meta">
											<div class="date">
												<i class="icon-time"></i>
												<!--  ${comment.postedDate}  -->
												<c:set var="commentDate" value="${comment.postedDate}" />
												<%
													LocalDate now = LocalDate.now();
														Period time = Period.between((LocalDate) pageContext.getAttribute("commentDate"), now);
														out.print(time.getDays() + " Days ago");
												%>
											</div>
										</div>
									</div>
									<div class="text">
										<p>${comment.comment}</p>
									</div>
									<%
									Comment com = (Comment) pageContext.getAttribute("comment");
									Post post = (Post) request.getAttribute("post");
									User user = (User) request.getSession().getAttribute("user");
									if(user != null) {
										if(com.getEmail().equals(user.getEmail()) || post.getEmail().equals(com.getEmail())) {
									%>
									<div>
										<a href="deleteComment?com_id=${comment.id}&post_id=${post.id}">Delete</a>
									</div>
									<%
										}
									}
									%>
								</div>
							</div>
						</li>
					</c:forEach>
				</ol>
				<!-- End commentlist -->
			</div>
			<!-- End page-content -->

			<div id="respond" class="comment-respond page-content clearfix">
				<div class="boxedtitle page-title">
					<h2>Leave a reply</h2>
				</div>
				<forms:form modelAttribute="commentform" action="" method="post">
					<div id="respond-textarea">
						<p>
							<label class="required" for="comment">Comment<span>*</span></label>
							<forms:input type="text" path="comment" />
						</p>
					</div>
					<p class="form-submit">
						<input name="submit" type="submit" id="submit"
							value="Post your answer" class="button small color">
					</p>
				</forms:form>
			</div>
			<!-- End post-next-prev -->
		</div>
		<!-- End main -->
	</div>
	<!-- End row -->
</section>
<!-- End container -->
<br>
<br>



<!-- FOOTER FILE INCLUSION -->
<jsp:include page="include/footer.jsp"></jsp:include>
<!-- FOOTER FILE INCLUSION -->


<!-- SCRIPT FILE INCLUSION -->
<jsp:include page="include/script.jsp"></jsp:include>
<!-- SCRIPT FILE INCLUSION -->
