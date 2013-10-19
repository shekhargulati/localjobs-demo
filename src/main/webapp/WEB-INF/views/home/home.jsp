<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>

<p>
	Welcome,<c:out value="${account.firstName}" />!
</p>

<p class="lead">Recommended Jobs</p>
<table class="table table-bordered table-striped table-hover" id="recommendedJobs" title="Recommended Jobs">
	<tr>
		<th>Job Title</th>
		<th>Company Name</th>
		<th>Skills</th>
		<th>Address</th>
		<th>Distance</th>
	</tr>
	<c:forEach items="${recommendedJobs}" var="recommendedJob">
	<tr>
		<td>${recommendedJob.title}</td>
		<td>${recommendedJob.company}</td>
		<td><c:forEach items="${recommendedJob.skills}" var="skill">
		<dd><c:out value="${skill}"/></dd>
	</c:forEach></td>
		<td>${recommendedJob.address}</td>
		<td>${recommendedJob.distanceText}</td>
		<td>
		<form id="applyJob" action="jobs/apply/${recommendedJob.id}" method="POST">
			<button class="btn btn-large btn-success" type="submit">Apply Job</button>
		</form>
		</td>
	</tr>

	</c:forEach>
</table>

<hr>

<p class="lead">Jobs Applied By You</p>
<table class="table table-bordered table-striped table-hover" id="appliedJobs" title="Applied Jobs">
	<tr>
		<th>Job Title</th>
		<th>Company Name</th>
		<th>Skills</th>
		<th>Address</th>
		<th>Distance</th>
	</tr>
	<c:forEach items="${appliedJobs}" var="appliedJob">
	<tr>
		<td>${appliedJob.title}</td>
		<td>${appliedJob.company}</td>
		<td><c:forEach items="${appliedJob.skills}" var="skill">
		<dd><c:out value="${skill}"/></dd>
	</c:forEach></td>
		<td>${appliedJob.address}</td>
		<td>${appliedJob.distanceText}</td>
	</tr>

	</c:forEach>
</table>

<hr>
