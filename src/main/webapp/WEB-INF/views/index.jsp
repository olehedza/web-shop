<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>CONSUMER PARADISE</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
        }
        .topnav {
            overflow: hidden;
            background-color: #333;
        }
        .topnav a {
            float: left;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }
        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }
        .topnav a.active {
            background-color: #4c8caf;
            color: #d9caca;
        }
    </style>
</head>
<body>
<div class="topnav">
    <a class="active" href="">Consumer paradise</a>
    <a href="${pageContext.request.contextPath}/auth/signup">Signup</a>
    <a href="${pageContext.request.contextPath}/admin/products/all">View products (admin permissions)</a>
    <a href="${pageContext.request.contextPath}/users/all">View users</a>
    <a href="${pageContext.request.contextPath}/products/all">View products</a>
    <a href="${pageContext.request.contextPath}/users/cart">Cart</a>
    <a href="${pageContext.request.contextPath}/injectData">Add test users</a>
    <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
</div>
</body>
</html>