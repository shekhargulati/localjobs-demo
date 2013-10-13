<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty message}">
<div class="${message.type.cssClass}">${message.text}</div>
</c:if>

<c:url value="/signup" var="signupUrl" />

<form class="form-signup" action="${signupUrl}" method="POST">
        <h2 class="form-signup-heading">Please sign up</h2>
        <input type="text" class="input-block-level" placeholder="First Name" name="firstName" value="${signupForm.firstName}">
        <input type="text" class="input-block-level" placeholder="Last Name" name="lastName" value="${signupForm.lastName}">
        <input type="text" class="input-block-level" placeholder="User Name" name="username" value="${signupForm.username}">
        <input type="password" class="input-block-level" placeholder="Password" name="password">
        <input type="text" class="input-block-level" placeholder="Comma Seperated list of Skills" name="skills">
        <textarea rows="5" class="input-block-level" placeholder="Address" name="address"></textarea>
        <button class="btn btn-large btn-primary" type="submit">Sign Up</button>
</form>