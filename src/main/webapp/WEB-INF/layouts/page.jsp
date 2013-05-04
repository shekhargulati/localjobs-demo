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

<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.js"></script>
<link rel="stylesheet" href="<c:url value="${pageContext.servletContext.contextPath}/resources/page.css" />"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="<c:url value="${pageContext.servletContext.contextPath}/resources/messages/messages.css" />"
	type="text/css" media="screen" />

<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap-responsive.min.css"
	rel="stylesheet">

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
			<ul class="nav nav-pills" >
				<li class="active"><a href="/home">Home</a></li>
				<li><a href="/connect">Connections</a></li>
				<li><a href="/myprofile">My Profile</a></li>
				<li><a href="/search">Location Aware Search</a></li>
				<li><a href="/fulltextsearch">Full Text Search</a></li>
			</ul>
		</sec:authorize>

		<div id="content" class="container">
			<tiles:insertAttribute name="content" />
		</div>

		<hr>
		
		<footer id="footer">
			<div class="links">
				<a href="http://whyjava.wordpress.com">Blog</a> 
				<a href="https://twitter.com/shekhargulati">Twitter</a> 
				<a href="https://github.com/shekhargulati">GitHub</a>
			</div>
			Made by <a href="https://twitter.com/shekhargulati/">Shekhar Gulati</a>. 
			Contact him <a href="mailto:shekhargulati84@gmail.com">shekhargulati84@gmail.com</a>
			Powered by <a href="https://openshift.redhat.com/" target="_blank">OpenShift</a><br />

	   </footer>
		

	</div>
	<!-- /container -->

	<!-- Placed at the end of the document so the pages load faster -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootswatch.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap-tab.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap-scrollspy.js"></script>

</body>
</html>

