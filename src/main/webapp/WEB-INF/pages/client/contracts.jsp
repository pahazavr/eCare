<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>Contracts</title>

    <link href="${contextPath}/res/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="outer-wrapper">
    <jsp:include page="../fragments/header.jsp"/>
    <div align="center">
        <h2>Contracts</h2>
    </div>

    <table class="table">
        <tr>
            <th>ID</th>
            <th>Number</th>
            <th>BlockedByClient</th>
            <th>BlockedByOperator</th>
        </tr>
        <c:forEach var="contract" items="${client.getContracts()}">
            <tr>
                <td>${contract.id}</td>
                <td>
                    <form:form id="showContractForm${contract.id}" method="POST" action="/contracts/show/"
                               enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="id" value=${contract.id}>
                        <input type="hidden" name="sessionRole" value=${role}>
                        <a class="inline-link" href="#" onclick="document.forms['showContractForm${contract.id}'].submit()">${contract.number}</a>
                    </form:form>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${contract.blockedByClient}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${contract.blockedByOperator}">Yes</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${contract.blockedByClient == true}">
                            <form:form id="unblockByClientForm${contract.id}" method="POST" action="unblockByClient" enctype="application/x-www-form-urlencoded">
                                <input type="hidden" name="id" value=${contract.id}>
                                <input type="hidden" name="sessionRole" value=${role}>
                                <a class="inline-link-unlock" title="Unblock contract" href="#" onclick="document.forms['unblockByClientForm${contract.id}'].submit()">Unblock</a>
                            </form:form>
                        </c:when>
                        <c:otherwise>

                            <form:form id="blockByClientForm${contract.id}" method="POST" action="blockByClient" enctype="application/x-www-form-urlencoded">
                                <input type="hidden" name="id" value=${contract.id}>
                                <input type="hidden" name="sessionRole" value=${role}>
                                <a class="inline-link-lock" title="Block contract" href="#" onclick="document.forms['blockByClientForm${contract.id}'].submit()">Block</a>
                            </form:form>

                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>

    <jsp:include page="../fragments/footer.jsp"/>
    </div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/res/js/bootstrap.js"></script>
</body>
</html>