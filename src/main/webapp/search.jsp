<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>

<html lang="en">
<head>
<meta charset="utf-8">
<title>LocalJobs : Helps you find right jobs near your location</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script src="resources/js/jquery.js"></script>
<link rel="stylesheet" href="<c:url value="${pageContext.servletContext.contextPath}/resources/page.css" />"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="<c:url value="${pageContext.servletContext.contextPath}/resources/messages/messages.css" />"
	type="text/css" media="screen" />

<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap-responsive.min.css"
	rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/jquery.tagsinput.css" />
<link href="${pageContext.servletContext.contextPath}/resources/css/jquery.loadmask.css" rel="stylesheet"
	type="text/css" />
	
	
<style>
div.jobBox {
	border : 1px solid black;
	float : bottom;
	margin : 5px;
	
}
</style>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Fav and touch icons -->
<link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/resources/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="${pageContext.servletContext.contextPath}/resources/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="${pageContext.servletContext.contextPath}/resources/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="${pageContext.servletContext.contextPath}/resources/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.servletContext.contextPath}/resources/ico/apple-touch-icon-57-precomposed.png">
</head>

<body>

	<div class="container-narrow">

		<div class="masthead">
			<ul class="nav nav-pills pull-right">
				<sec:authorize access="isAuthenticated()">
					<li><a href="home">Home</a></li>
				</sec:authorize>

				<sec:authorize access="!isAuthenticated()">
					<li class="active"><a href="index.jsp">Home</a></li>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">

					<li><a href="signout">Sign Out</a></li>

				</sec:authorize>
			</ul>
			<h3 class="muted">LocalJobs</h3>
		</div>
		<hr></hr>
		<div id="main" class="container">
		<sec:authorize access="isAuthenticated()">
			<ul class="nav nav-pills">
				<li><a href="${pageContext.servletContext.contextPath}/home">Home</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/connect">Connections</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/myprofile">My Profile</a></li>
				<li class="active"><a href="${pageContext.servletContext.contextPath}/search">Location Aware Search</a></li>
				<li><a href="${pageContext.servletContext.contextPath}/fulltextsearch">Full Text Search</a></li>
			</ul>
		</sec:authorize>

		


			<form id="jobSearchForm" class="form-horizontal">
				<div class="control-group">
					<div class="controls">
						<input type="text" id="skills" name="skills" class="input-xlarge"
							placeholder="Enter skills for which you want to search jobs"
							required>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<textarea rows="3" id="location" class="input-xlarge"
							placeholder="Enter location near which you want to search jobs"></textarea>
					</div>
				</div>
				
				<div class="control-group">
					<div class="controls">
						<input type="checkbox" id="useCurrentLocation" name="useCurrentLocation" value="true"> Use Current Location
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<button id="findJobsButton" type="submit" class="btn btn-success">Find
							Jobs</button>
					</div>
				</div>

			</form>
		
		
		<div id="results"></div>

		<hr>
		
		

		

		<footer id="footer">
			<div class="links">
				<a href="http://whyjava.wordpress.com">Blog</a> <a
					href="https://twitter.com/shekhargulati">Twitter</a> <a
					href="https://github.com/shekhargulati">GitHub</a>
			</div>
			Made by <a href="https://twitter.com/shekhargulati/">Shekhar
				Gulati</a>. Contact him <a href="mailto:shekhargulati84@gmail.com">shekhargulati84@gmail.com</a>.<br />

		</footer>
		</div>

	</div>

	<script type="text/x-mustache-template" id="job-template">

    
    <div class="jobBox">
	   <h3>{{jobtitle}}</h3>
       <p> {{company}} </p>
      <address> {{address}} </address>
	  <p> {{skills}}</p>
	  <p> {{distance}} </p>
    </div>
    </script>

	<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.tagsinput.js"></script>
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/resources/js/jquery.loadmask.min.js"></script>
	<script type="text/javascript"
		src="https://maps.googleapis.com/maps/api/js?sensor=true">
		
	</script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/underscore.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/backbone.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/mustache.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#skills').tagsInput({
				defaultText : "add skills"
			});
		});
	</script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/render-map.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/app.js"></script>



</body>
</html>