<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>Create an account</title>

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/res/css/style.css" rel="stylesheet">
</head>

<body>
<!-- Default form register -->
<form:form method="POST" modelAttribute="newClient" class="form-signin text-center border border-light p-5">
    <p class="h4 mb-4">Sign up</p>
    <div class="form-row mb-4">
        <div class="col">
            <!--Name-->
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:label path="name">Name:</form:label>
                    <form:input type="text" id="defaultRegisterFormFirstName" path="name"
                                class="form-control" placeholder="First name" autofocus="true"/>
                    <form:errors path="name" cssClass="error" element="div"/>
                </div>
            </spring:bind>
        </div>
        <div class="col">
            <!-- Surname -->
            <spring:bind path="surname">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="surname">Surname:</form:label>
                    <form:input type="text" id="defaultRegisterFormLastName" path="surname" class="form-control"
                                placeholder="Last name"/>
                    <form:errors path="surname"/>
                </div>
            </spring:bind>
        </div>
    </div>

    <!-- birthDate -->
    <spring:bind path="birthDate">
        <form:label path="birthDate">Birth date:</form:label>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="birthDate" class="form-control" placeholder="Your bithdate"/>
            <form:errors path="birthDate"/>
        </div>
    </spring:bind>

    <!-- Passport -->
    <spring:bind path="passport">
        <form:label path="passport">Passport:</form:label>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="passport" class="form-control" placeholder="Your passport"/>
            <form:errors path="passport"/>
        </div>
    </spring:bind>

    <!-- Address -->
    <spring:bind path="address">
        <form:label path="address">Address:</form:label>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="address" class="form-control" placeholder="Your address"/>
            <form:errors path="address"/>
        </div>
    </spring:bind>

    <!-- E-mail -->
    <spring:bind path="email">
        <form:label path="email">E-mail:</form:label>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="email" path="email" id="defaultRegisterFormEmail" class="form-control mb-4"
                        placeholder="E-mail"/>
            <form:errors path="email"/>
        </div>
    </spring:bind>

    <!-- Password -->
    <spring:bind path="password">
        <form:label path="password">Password:</form:label>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="password" path="password" id="defaultRegisterFormPassword" class="form-control"
                        placeholder="Password" aria-describedby="defaultRegisterFormPasswordHelpBlock"/>
            <form:errors path="password"/>
        </div>
    </spring:bind>

    <!-- confirmPassword -->
    <spring:bind path="confirmPassword">
        <form:label path="confirmPassword">Confirm password:</form:label>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="password" path="confirmPassword" class="form-control"
                        placeholder="Confirm your password"/>
            <form:errors path="confirmPassword"/>
        </div>
    </spring:bind>

    <!-- Sign up button -->
    <button class="btn btn-info my-4 btn-block" type="submit">Sign up</button>
</form:form>

<!-- Default form register -->
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</body>
</html>