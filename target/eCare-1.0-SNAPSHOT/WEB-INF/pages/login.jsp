<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>Log in</title>

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${contextPath}/res/css/style.css" rel="stylesheet">
</head>

<body>
<!-- Default form login -->
<c:url value="/login" var="login"/>
    <form class="form-signin text-center border border-light p-5" method="POST" action="${login}">
        <p class="h4 mb-4">Log in</p>

        <div class="form-group ${error != null ? 'has-error' : ''}">

            <span>
                <c:if test="${message != null}">
                    <div class="inner-wrapper-success" id="success">
                        <p>
                            Success: ${message}
                        </p>
                    </div>

                    <SCRIPT language="javascript">
                        setInterval(function() {
                            $("#success").attr("hidden", true);
                        }, 5000);
                    </SCRIPT>

                </c:if>
            </span>
            <!-- Email -->
            <input name="email" type="email" id="defaultLoginFormEmail" class="form-control mb-4" placeholder="E-mail"
                   autofocus="true">
            <!-- Password -->
            <input name = "password" type="password" id="defaultLoginFormPassword" class="form-control mb-4" placeholder="Password">

            <span>
                <c:if test="${error != null}">
                    <div class="inner-wrapper-error" id="error">
                        <p>
                            Error: ${error}
                        </p>
                    </div>

                    <SCRIPT language="javascript">
                        setInterval(function() {
                            $("#error").attr("hidden", true);
                        }, 5000);
                    </SCRIPT>
                </c:if>
            </span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <!-- Sign in button -->
            <button class="btn btn-info btn-block my-4" type="submit">Sign in</button>
        </div>
        <!-- Register -->
        <p>Not a member?
            <c:url value="/registration" var="registration"/>
            <a href="${registration}">Register</a>
        </p>
    </form>
<!-- Default form login -->
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</body>
</html>