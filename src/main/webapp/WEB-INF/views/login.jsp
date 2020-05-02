<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login page</h1>
    <h3 style="color: red">${errorMsg}</h3>
    <form action="${pageContext.request.contextPath}/auth/login" method="post">
        <label for="login">LOGIN:</label>
        <br>
        <br>
        <input type="text" name="login" id="login">
        <br>
        <br>
        <label for="pwd">PASSWORD</label>
        <br>
        <br>
        <input type="password" name="pwd" id="pwd">
        <br>
        <br>
        <button type="submit">Login</button>
    </form>
</body>
</html>
