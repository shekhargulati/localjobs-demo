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
<link rel="stylesheet" href="<c:url value="/resources/page.css" />"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="<c:url value="/resources/messages/messages.css" />"
	type="text/css" media="screen" />

<link href="/resources/css/bootstrap.min.css" rel="stylesheet">

<link href="/resources/css/bootstrap-responsive.min.css"
	rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="/resources/css/jquery.tagsinput.css" />
<link href="/resources/css/jquery.loadmask.css" rel="stylesheet"
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
<link rel="shortcut icon" href="/resources/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="/resources/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="/resources/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="/resources/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="/resources/ico/apple-touch-icon-57-precomposed.png">
</head>

<body>

	<div class="container-narrow">

		<div class="masthead">
			<ul class="nav nav-pills pull-right">
				<sec:authorize access="isAuthenticated()">
					<li class="active"><a href="home">Home</a></li>
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
		<sec:authorize access="isAuthenticated()">
			<ul class="nav nav-pills">
				<li class="active"><a href="/home">Home</a></li>
				<li><a href="/connect">Connections</a></li>
				<li><a href="/myprofile">My Profile</a></li>
				<li><a href="/search">Location Aware Search</a></li>
				<li><a href="/fulltextsearch">Full Text Search</a></li>
			</ul>
		</sec:authorize>

		<div id="main" class="container">


			<form id="fullTextSearchForm" class="form-horizontal">
				<div class="control-group">
					<div class="controls">
						<input type="text" id="query" name="query" class="input-xlarge"
							placeholder="Lucene Query To Search Jobs"
							required>
					</div>
				</div>
				
				<div class="control-group">
					<div class="controls">
						<button id="findJobsButton" type="submit" class="btn btn-success">Find Jobs</button>
					</div>
				</div>

			</form>

		</div>

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

	<script type="text/x-mustache-template" id="job-template">

    
    <div class="jobBox">
	   <h3>{{jobtitle}}</h3>
       <p> {{company}} </p>
      <address> {{address}} </address>
	  <p> {{skills}}</p>
	  <p> {{distance}} </p>
    </div>
    </script>

	<script src="/resources/js/jquery.js"></script>
	<script src="/resources/js/jquery.tagsinput.js"></script>
	<script type="text/javascript"
		src="/resources/js/jquery.loadmask.min.js"></script>
	<script type="text/javascript"
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyATvS5bY-3CkeiedEWtr5WFHEQFOi-9uYs&sensor=true">
		
	</script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="/resources/js/underscore.js"></script>
	<script src="/resources/js/backbone.js"></script>
	<script src="/resources/js/mustache.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#skills').tagsInput({
				defaultText : "add skills"
			});
		});
	</script>
	<script src="/resources/js/fulltextsearch.js"></script>



</body>
</html>