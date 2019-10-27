<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${contextPath}/res/css/bootstrap.css">
</head>
<body>

<nav class="navbar navbar-expand-lg blue">
    <c:url value="/welcome" var="welcome"/>
    <a class="navbar-brand" href="${welcome}">eCare</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <c:url value="/contracts/all" var="contracts"/>
                <a class="nav-link" href="${contracts}">Contracts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" title="Add new contract" href="#" onclick="document.forms['profileForm'].submit()">Profile</a>
            </li>
            <li class="nav-item">
                <c:url value="#" var="some"/>
                <a class="nav-link" href="${some}">Something</a>
            </li>
            <li class="nav-item">
                <button class="btn btn-outline-primary btn-rounded waves-effect" type="submit" onclick="document.forms['logoutForm'].submit()">Logout</button></a>
            </li>
        </ul>
    </div>
</nav>

<form:form id="profileForm" method="POST" action="${contextPath}/client/profile" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${client.id}">
    <input type="hidden" name="sessionRole" value=${role}>
</form:form>

<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form:form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </c:if>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
</body>
</html>
