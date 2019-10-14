<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../resources/css/style.css">
    <title>Login</title>
</head>
<body>

<div class="inner-wrapper" style="padding-left: 100px">
    <c:url value="/tariffs" var="tariffs"/>
    <form name='loginForm' action="${tariffs}" method='POST'>
        <p>
            Login:
            &emsp;
            &emsp;
            <input type="text" placeholder="login" class="simple-input" name="username" size=20 value="">
        </p>
        <br>
        <p>
            Password:
            &nbsp;
            <input type="password" placeholder="password" class="simple-input" name="password" size=20 value="">
        </p>
        <br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit" class="modern">Enter</button>
        <a href="#" onclick="document.getElementById('formId1').submit()" class="inline-link">Registration</a>
    </form>

    <form:form id="formId1" method="post" action="registration" enctype="application/x-www-form-urlencoded">
    </form:form>

</div>

<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
</html>