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

    <form:form method="POST"
               modelAttribute="editClient"
               action="${contextPath}/client/updateProfile"
               class="form-signin text-center border border-light p-5"
               enctype="application/x-www-form-urlencoded">
        <p class="h4 mb-4">Edit profile of ${client.getFullName()}</p>
        <div class="form-row mb-4">
            <div class="col">
                <!--First name-->
                <spring:bind path="name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:label path="name">First name:</form:label>
                        <form:input type="text" id="defaultRegisterFormFirstName"
                                    class="form-control" placeholder="First name"
                                    autofocus="true" value="${client.getName()}" path="name"/>
                        <form:errors path="name" cssClass="error" element="div"/>
                    </div>
                </spring:bind>
            </div>
            <div class="col">
                <!-- Surname -->
                <spring:bind path="surname">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:label path="surname">Surname:</form:label>
                        <form:input type="text" id="defaultRegisterFormLastName"
                                    class="form-control" placeholder="Surname"
                                    value="${client.getSurname()}" path="surname"/>
                        <form:errors path="surname" cssClass="error" element="div"/>
                    </div>
                </spring:bind>
            </div>
        </div>

        <!-- BirthDate -->
        <spring:bind path="birthDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="birthDate">Birth Date:</form:label>
                <form:input type="text" class="form-control"
                            placeholder="Your bithdate" value="${client.getBirthDate()}" path="birthDate"/>
                <form:errors path="birthDate" cssClass="error" element="div"/>
            </div>
        </spring:bind>

        <!-- Passport -->
        <spring:bind path="passport">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="passport">Passport:</form:label>
                <form:input type="text"
                            class="form-control" placeholder="Your passport"
                            value="${client.getPassport()}" path="passport"/>
                <form:errors path="passport" cssClass="error" element="div"/>
            </div>
        </spring:bind>

        <!-- Address -->
        <spring:bind path="address">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="address">Address:</form:label>
                <form:input type="text" class="form-control" placeholder="Your address"
                            value="${client.getAddress()}" path="address"/>
                <form:errors path="address" cssClass="error" element="div"/>
            </div>
        </spring:bind>

        <!-- Hidden parameters -->
        <input type="hidden" name="sessionRole" value=${role}>

        <!-- Save button -->
        <button class="btn btn-info my-4 btn-block" type="submit">Save</button>
    </form:form>

    <jsp:include page="../fragments/footer.jsp"/>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
</body>
</html>
