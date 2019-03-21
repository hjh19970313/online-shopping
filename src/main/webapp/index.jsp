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
    <script>
        function register() {
            window.location.href="register.jsp"

        }

    </script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

</head>
<style>
    #box{position:fixed;left:0px;right:0px;width:706px;margin-left:auto;margin-right:auto;top:100px;}
    .input{height: 20px ; width: 100px;}
    .label{font-family: "YouYuan ", "幼圆";  font-size: 30px; color: blue ;}
    label1 {
        position:relative;
        width:400px;
        margin:10px auto;
        display:inline-block;
    }
    label1:after {
        content:"";
        display:inline-block;
        width:0;
        height:2px;
        background:red;
        transition:width 1s;
        position:absolute;
        bottom:1px;
        left:1px;
    }
</style>

<body>

<div style="margin:0 auto;width:490px;height: 400px">

<div style="margin-left: 150px;margin-top:100px;margin-bottom: 80px ">
    <label class="label" >请登录</label>
</div>


<form class="form-horizontal" action="login" method="post" style="">
    <div class="form-group">
        <label for="inputEmail3" class="col-sm-2 control-label">用户名：</label>
        <div class="col-sm-10" style="width: 300px">
            <input type="text" class="form-control" id="inputEmail3" placeholder="UserCode" name="UserCode">
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">密  码：</label>
        <div class="col-sm-10" style="width: 300px" >
            <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="Password">
        </div>
    </div>
    <div >
        <div style="float: left;margin-right: 50px;margin-left: 60px" >
         <div class="col-sm-offset-2 col-sm-10" >
                <button type="submit" class="btn btn-default">Sign in</button>
         </div>
         </div>
        <div  style="float: left">
            <div class="col-sm-offset-2 col-sm-10">
                <span onclick="register()"  class="btn btn-default">Register</span>
            </div>
         </div>
    </div>

</form>
</div>

</body>
</html>
