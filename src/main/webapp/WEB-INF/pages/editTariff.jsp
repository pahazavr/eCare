<%--
  Created by IntelliJ IDEA.
  User: Pahazavr
  Date: 09.10.2019
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${empty tariff.title}">
        <title>Add</title>
    </c:if>
    <c:if test="${!empty tariff.title}">
        <title>Edit</title>
    </c:if>
</head>
<body>
<c:if test="${empty tariff.title}">
    <c:url value="/add" var="var"/>
</c:if>
<c:if test="${!empty tariff.title}">
    <c:url value="/edit" var="var"/>
</c:if>
<form action="${var}" method="POST">
    <c:if test="${!empty tariff.title}">
        <input type="hidden" name="id" value="${tariff.id}">
    </c:if>
    <label for="title">Title</label>
    <input type="text" name="title" id="title">
    <label for="price">Price</label>
    <input type="text" name="price" id="price">
    <c:if test="${empty tariff.title}">
        <input type="submit" value="Add new tariff">
    </c:if>
    <c:if test="${!empty tariff.title}">
        <input type="submit" value="Edit tariff">
    </c:if>
</form>
</body>
</html>
