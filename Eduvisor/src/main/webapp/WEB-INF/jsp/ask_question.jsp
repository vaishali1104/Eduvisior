<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- HEADER INCLUSION CODE -->

<jsp:include page="include/header.jsp"></jsp:include>

<!-- HEADER INCLUSION CODE -->

<!-- PAGE HEADING -->
<div class="breadcrumbs">
	<section class="container">
		<div class="row">
			<div class="col-md-12">
				<h1>Ask Question</h1>
			</div>
			<div class="col-md-12">
				<div class="crumbs">
					<a href="index">Home</a>
					<span class="crumbs-span">/</span>
					<span class="current">Ask Question</span>
				</div>
			</div>
		</div>
		<!-- End row -->
	</section>
	<!-- End container -->
</div>
<!-- End breadcrumbs -->
<!-- PAGE HEADING -->


<!-- MAIN PAGE -->

<section class="container main-content">
	<div class="row">
		<div class="col-md-12">

			<div class="page-content ask-question">
				<div class="boxedtitle page-title">
					<h2>Ask Question</h2>
				</div>
				<div class="form-style form-style-3" id="question-submit">
					<forms:form modelAttribute="question" action="/addQuestion">
						<!-- modelAttribute will have value of variable sent to the page over get request.
						Action URL will have link where the insert query is mapped by the controller.
						The mapped function on controller will have to access the form by creating object of modelAttribute to Post object
						
						EG: @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
						public String AddQuestion(@ModelAttribute("question") Post post) {
							//The post variable will have all the data inputed by the user....
						}
						 -->
						<div class="form-inputs clearfix">
							<c:if test="${not empty postError}">
								<div class="alert-message warning">
									<i class="icon-exclamation-sign"></i>
									<p>
										<span>Post Error</span><br> ${postError}
									</p>
								</div>
							</c:if>

							<c:if test="${not empty loggedinuser}">
								<div class="alert-message warning">
									<i class="icon-exclamation-sign"></i>
									<p>
										<span>Error </span><br> ${postError}
									</p>
								</div>
							</c:if>
							<p>
								<label class="required">Question Title<span>*</span></label>
								<forms:input type="text" path="title" required="required" />
								<span class="form-description">Please choose an appropriate title 
										for the question to answer it even easier .</span>

								<c:if test="${not empty errors }">
									<blockquote>
										<forms:errors path="title" cssStyle="color: black; font-size: 15px;" />
									</blockquote>
								</c:if>
							</p>
							<p>
								<label class="required">Category<span>*</span></label> <span
									class="styled-select"> <!-- 
										<select>
											<option value="">Select a Category</option>
											<option value="1">Category 1</option>
											<option value="2">Category 2</option>
										</select>
										 --> <forms:select path="category" required="required">
										<forms:option value="" label="Select Category" />
										<c:forEach var="listValue" items="${categories}">
											<forms:option value="${listValue}" label="${listValue}" />
										</c:forEach>
									</forms:select> <!-- Value will contain value of the variable having list of area of interest -->
								</span> <span class="form-description">Please choose the
									appropriate section so easily search for your question .</span>
								<c:if test="${not empty errors }">
									<blockquote>
										<forms:errors path="category"
											cssStyle="color: black; font-size: 15px;" />
									</blockquote>
								</c:if>
							</p>
						</div>
						<div id="form-textarea">
							<p>
								<label class="required">Details<span>*</span></label>
								<!-- 
									<textarea id="question-details" aria-required="true" cols="58" rows="8"></textarea>
									 -->
								<forms:textarea path="description" required="required" />
								<span class="form-description">Type the description
									thoroughly and in detail .</span>
								<c:if test="${not empty errors }">
									<blockquote>
										<forms:errors path="description"
											cssStyle="color: black; font-size: 15px;" />
									</blockquote>
								</c:if>
							</p>
						</div>
						<p class="form-submit">
							<input type="submit" id="publish-question"
								value="Publish Your Question" class="button color small submit">
						</p>
					</forms:form>
				</div>
			</div>
			<!-- End page-content -->
		</div>
		<!-- End main -->
	</div>
	<!-- End row -->
</section>
<!-- End container -->
<br>
<br>
<!-- MAIN PAGE -->

<!-- FOOTER INCLUSION CODE -->

<jsp:include page="include/footer.jsp"></jsp:include>

<!-- FOOTER INCLUSION CODE -->

<!-- SCRIPT INCLUSION CODE -->

<jsp:include page="include/script.jsp"></jsp:include>

<!-- SCRIPT INCLUSION CODE -->