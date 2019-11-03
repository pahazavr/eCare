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
                <a class="nav-link" title="See all clients" href="#"
                   onclick="document.forms['clientsForm'].submit()">Clients</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" title="See all tariffs" href="#"
                   onclick="document.forms['tariffForm'].submit()">Tariffs</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" title="See all contracts" href="#"
                   onclick="document.forms['contractsForm'].submit()">Contracts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" title="Edit profile" href="#"
                   onclick="document.forms['profileForm'].submit()">${client.getEmail()}</a>
            </li>
            <li class="nav-item">
                <button class="btn btn-outline-primary btn-rounded waves-effect" type="submit"
                        onclick="document.forms['logoutForm'].submit()">Logout</button></a>
            </li>
        </ul>
    </div>
</nav>

<form:form id="profileForm" method="POST"
           action="${contextPath}/client/profile"
           enctype="application/x-www-form-urlencoded">
</form:form>

<form:form id="contractsForm" method="GET"
           action="${contextPath}/client/viewAllContractsForClient"
           enctype="application/x-www-form-urlencoded">
</form:form>

<form:form id="tariffForm" method="POST"
           action="${contextPath}/operator/viewAllTariffs"
           enctype="application/x-www-form-urlencoded">
</form:form>

<form:form id="clientsForm" method="POST"
           action="${contextPath}/operator/viewAllClients"
           enctype="application/x-www-form-urlencoded">
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
