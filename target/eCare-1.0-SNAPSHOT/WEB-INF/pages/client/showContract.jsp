<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Show Contract</title>

    <link href="${contextPath}/res/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="outer-wrapper">

    <jsp:include page="../fragments/header.jsp"/>

    <div align="center">
        <h2>Contract Info</h2>
    </div>
    <table class="table">
        <tr>
            <td>ID</td>
            <td>${contract.id}</td>
        </tr>
        <tr>
            <td>Number</td>
            <td>${contract.number}</td>
        </tr>
        <tr>
            <td>BlockedByClient</td>
            <td>
                <c:choose>
                    <c:when test="${contract.blockedByClient}">Yes</c:when>
                    <c:otherwise>No</c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>BlockedByOperator</td>
            <td>
                <c:choose>
                    <c:when test="${contract.blockedByOperator}">Yes</c:when>
                    <c:otherwise>No</c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>

    <div align="center">
        <h2>Connected options</h2>
    </div>

    <table class="table">
        <tr>
            <th>Title</th>
            <th>Price</th>
            <th>Connection Price</th>
        </tr>
        <c:forEach var="option" items="${optionList}">
        <tr>
            <td>${option.title}</td>
            <td>${option.price}</td>
            <td>${option.connection}</td>
        </tr>
        </c:forEach>
    </table>

    <jsp:include page="../fragments/footer.jsp"></jsp:include>
</div>
</body>
</html>