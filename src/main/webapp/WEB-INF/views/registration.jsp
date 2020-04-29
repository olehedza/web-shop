<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Signup</title>
</head>
<body>
<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/auth/signup">
    <label for="username">USERNAME:</label>
    <br>
    <input type="text" name="username" id="username" value="olehedza">
    <br>
    <label for="email">EMAIL:</label>
    <br>
    <input type="email" name="email" id="email" value="olehedza@gmail.com">
    <br>
    <label for="fname">FIRST NAME:</label>
    <br>
    <input type="text" id="fname" name="fname" value="Oleksii">
    <br>
    <label for="lname">LAST NAME:</label>
    <br>
    <input type="text" id="lname" name="lname" value="Lehedza">
    <br>
    <label for="pwd">PASSWORD</label>
    <br>
    <input type="password" name="pwd" id="pwd">
    <br>
    <label for="pwd-confirm">CONFIRM PASSWORD</label>
    <br>
    <input type="password" name="pwd-confirm" id="pwd-confirm">
    <br>
    <br>
    <button type="submit">Submit</button>
</form>
</body>
</html>
