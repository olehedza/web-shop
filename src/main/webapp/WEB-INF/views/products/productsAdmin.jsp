<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of products</title>
</head>
<body>
<h3>List of all products (admin permission)</h3><br>
<a href="${pageContext.request.contextPath}/products/add">Add product</a>
<table style="border: 2px solid slategray">
    <tr>
        <th>ID</th>
        <th>TITLE</th>
        <th>PRICE</th>
        <th></th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td><c:out value="${product.id}" /></td>
            <td><c:out value="${product.name}" /></td>
            <td><c:out value="${product.price}" /></td>
            <td>
                <form action="${pageContext.request.contextPath}/products/delete" method="get" >
                    <input type="hidden" name="productId" value="${product.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
