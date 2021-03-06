<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h3>List of all products</h3>
<table style="border: 2px solid slategray">
    <tr>
        <th>ID</th>
        <th>TITLE</th>
        <th>PRICE</th>
        <th>Adding to cart</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td><c:out value="${product.id}" /></td>
            <td><c:out value="${product.name}" /></td>
            <td><c:out value="${product.price}" /></td>
            <td>
                <form action="${pageContext.request.contextPath}/cart/add" method="post" >
                    <input type="hidden" name="productId" value="${product.id}">
                    <button type="submit">Add to cart</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
