<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="fragments/header.jspf"%>

<a href="/solutionAdd">Dodaj rozwiązanie</a><br><br>

    <table border="1">
        <tr>
            <th>Created</th>
            <th>Updated</th>
            <th>Description</th>
            <th>User</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${solutions}" var="solution">
            <tr>
                <td>${solution.created}</td>
                <td>${solution.updated}</td>
                <td>${solution.description}</td>
                <td>${solution.user.username}</td>
                <td>
                    <a href="/solutionUpdate?id=${solution.id}">update</a>
                    <a href="/solutionDelete?id=${solution.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

<%@ include file="fragments/footer.jspf"%>
</body>
</html>
