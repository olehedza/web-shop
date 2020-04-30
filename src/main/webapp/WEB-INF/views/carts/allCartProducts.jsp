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
        <th></th>
    </tr>
    <c:forEach var="product" items="${products}">

     <td><c:out value="${product.name}" /></td>
     <td><c:out value="${product.price}" /></td>
     <td>
         <form action="${pageContext.request.contextPath}/users/cart/product/delete" method="get">
              <input type="hidden" name="productId" value="${product.id}">
              <button type="submit">Delete</button>
         </form>
     </td>
      <form action="${pageContext.request.contextPath}/orders/checkout" method="get">
          <input type="hidden" name="cart" value="${cartId}">
          <button type="submit">Checkout</button>
      </form>

    </c:forEach>
</table>
</body>
</html>
