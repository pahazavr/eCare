<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href=".././resources/css/style.css">
    <title>Tariffs</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<h2>Tariffs</h2>
<table>
    <tr>
        <th>id</th>
        <th>title</th>
        <th>price</th>
        <th>action</th>
    </tr>
    <c:forEach var="tariff" items="${tariffsList}">
        <tr>
            <td>${tariff.id}</td>
            <td>${tariff.title}</td>
            <td>${tariff.price}</td>
            <td>
                <c:url value="/edit/${tariff.id}" var="edit"/>
                <a href="${edit}">edit</a>
                <c:url value="/delete/${tariff.id}" var="delete"/>
                <a href="${delete}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h2>Add</h2>
<c:url value="/add" var="add"/>
<a href="${add}">Add new tariff</a>

<jsp:include page="../fragments/footer.jsp"></jsp:include>
</body>
</html>