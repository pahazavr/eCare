<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Welcome</title>

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/res/css/style.css" rel="stylesheet">
</head>
<body>
<div class="outer-wrapper container">
    <jsp:include page="../fragments/header.jsp"/>
    <h3>Profile info of ${client.getName()}:
        <a href="#" onclick="document.forms['EditProfileForm'].submit()">Edit</a></h3>
    <table class="table">
        <tr>
            <td>Full name</td>
            <td>${client.getFullName()}</td>
        </tr>
        <tr>
            <td>Birth Date</td>
            <td>${client.getBirthDate()}</td>
        </tr>
        <tr>
            <td>Passport</td>
            <td>${client.getPassport()}</td>
        </tr>
        <tr>
            <td>Address</td>
            <td>${client.getAddress()}</td>
        </tr>
        <tr>
            <td>Email</td>
            <td>${client.getEmail()}</td>
        </tr>
    </table>

    <form:form id="EditProfileForm" method="POST" action="${contextPath}/client/editProfile"
               enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${client.id}">
        <input type="hidden" name="sessionRole" value=${role}>
    </form:form>

    <jsp:include page="../fragments/footer.jsp"/>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
</body>
</html>
