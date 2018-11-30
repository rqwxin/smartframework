<%--
  Created by IntelliJ IDEA.
  User: HUAWEI
  Date: 2018/11/30
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>

    <table border="1">
        <thead>
            <th>ID</th>
            <th>账号</th>
            <th>昵称</th>
            <th>性别</th>
        </thead>
        <tbody>
            <c:forEach var="customer" items="${list}">
                <tr>
                    <td>${customer.id}</td>
                    <td>${customer.account}</td>
                    <td>${customer.name}</td>
                    <td>${customer.sex}</td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</head>
<body>

</body>
</html>
