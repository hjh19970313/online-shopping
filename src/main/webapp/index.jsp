<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2018/9/21
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎登陆！</title>

</head>
<style>
    #box{position:fixed;left:0px;right:0px;width:706px;margin-left:auto;margin-right:auto;top:100px;}
</style>

<body>
<div style="text-align: center" id="box"   >
<form action="login" method="post">
    <fieldset>
    <label style="font-family: '微软雅黑 Light'" >用户名：</label><input name="UserCode" type="text" style="font-family: '微软雅黑 Light'">
    <br>
    <label style="font-family: '微软雅黑 Light'"> 密码:</label> <input name="Password" type="text">
    <input type="submit">
    </fieldset>
</form>
</div>
</body>
</html>
