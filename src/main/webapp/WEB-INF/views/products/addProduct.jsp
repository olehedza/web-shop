<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h3>Add product to cart</h3>
<h4 style="color: red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    <label for="title">TITLE</label><br>
    <input type="text" name="title" id="title">
    <br><br>
    <label for="price">PRICE</label><br>
    <input type="text" name="price" id="price"><br><br>
    <button type="submit">Add</button>
</form>
</body>
</html>
