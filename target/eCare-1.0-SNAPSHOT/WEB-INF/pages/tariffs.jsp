<%--
  Created by IntelliJ IDEA.
  User: Pahazavr
  Date: 09.10.2019
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TARIFFS</title>
</head>
<body>
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
            ${tariffsList.size()}
        </tr>
        <tr>
            <td>${tariff.id}</td>
            <td>${tariff.title}</td>
            <td>${tariff.price}</td>
            <td>
                <a href="/edit/${tariff.id}">edit</a>
                <a href="/delete/${tariff.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h2>Add</h2>
<c:url value="/add" var="add"/>
<a href="${add}">Add new tariff</a>
</body>
</html>