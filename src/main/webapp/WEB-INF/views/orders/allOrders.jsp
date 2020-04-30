<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>List of all products</h3>
<table style="border: 2px solid slategray">
    <tr>
        <th>ID</th>
        <th>PRODUCT COUNT</th>
        <th>COMMON PRICE</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td><c:out value="${order.id}" /></td>
            <td><c:out value="${order.products.size()}" /></td>
            <td><c:out value="${order.getCommonPrice()}" /></td>
            <td>
                <form action="${pageContext.request.contextPath}/users/orders" method="get" >
                    <input type="hidden" name="orderId" value="${order.id}">
                    <button type="submit">View</button>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/users/orders/delete" method="get" >
                    <input type="hidden" name="orderId" value="${order.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
