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

    <form:form method="POST" action="${contextPath}/client/updateProfile"
               class="form-signin text-center border border-light p-5"
               enctype="application/x-www-form-urlencoded">
        <p class="h4 mb-4">Edit profile of ${client.getFullName()}</p>
        <div class="form-row mb-4">
            <div class="col">
                <!--First name-->
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label>First name:</label>
                    <input type="text" id="defaultRegisterFormFirstName" name="name"
                                class="form-control" placeholder="First name" autofocus="true" value="${client.getName()}"/>
                </div>
            </div>
            <div class="col">
                <!-- Surname -->
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label>Surname:</label>
                    <input type="text" id="defaultRegisterFormLastName" name="surname" class="form-control"
                                placeholder="Suraname" value="${client.getSurname()}"/>
                </div>
            </div>
        </div>

        <!-- BirthDate -->
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label>Birth Date:</label>
            <input type="text" name="birthDate" class="form-control" placeholder="Your bithdate" value="${client.getBirthDate()}"/>
        </div>

        <!-- Passport -->
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label>Passport:</label>
            <input type="text" name="passport" class="form-control" placeholder="Your passport" value="${client.getPassport()}"/>
        </div>

        <!-- Address -->
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label>Address:</label>
            <input type="text" name="address" class="form-control" placeholder="Your address" value="${client.getAddress()}"/>
        </div>

        <!-- Hidden parameters -->
        <input type="hidden" name="id" value="${client.id}">
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
