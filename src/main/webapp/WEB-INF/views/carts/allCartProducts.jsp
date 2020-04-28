<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<h3>List of all products</h3>
<table style="border: 2px solid slategray">
    <tr>
        <th>ID</th>
        <th>TITLE</th>
        <th>PRICE</th>
        <th>USER_ID</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td><c:out value="${product.id}" /></td>
            <td><c:out value="${product.name}" /></td>
            <td><c:out value="${product.price}" /></td>
            <td><c:out value="${userId}" /></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
