<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Order list</title>
</head>
<body>
<table style="border: 2px solid slategray">
    <tr>
        <th>ID</th>
        <th>TITLE</th>
        <th>PRICE</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td><c:out value="${product.id}" /></td>
            <td><c:out value="${product.name}" /></td>
            <td><c:out value="${product.price}" /></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
