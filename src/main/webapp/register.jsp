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
    <title></title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

</head>
<style>
    #box{position:fixed;left:0px;right:0px;width:706px;margin-left:auto;margin-right:auto;top:100px;}
    .label{font-family: "YouYuan ", "幼圆";  font-size: 30px; color: slategrey ;margin-left: 100px}
    .select { width:170px; height:34px;  }

    .login-button { /* 按钮美化 */
        width: 270px; /* 宽度 */
        height: 40px; /* 高度 */
        border-width: 0px; /* 边框宽度 */
        border-radius: 3px; /* 边框半径 */
        background: #1E90FF; /* 背景颜色 */
        cursor: pointer; /* 鼠标移入按钮范围时出现手势 */
        outline: none; /* 不显示轮廓线 */
        font-family: Microsoft YaHei; /* 设置字体 */
        color: white; /* 字体颜色 */
        font-size: 17px; /* 字体大小 */
    }
    .login-button:hover { /* 鼠标移入按钮范围时改变颜色 */
        background: #5599FF;
    }

</style>

<body style="background-color:#9d9d9d">

<div style="height: 600px;width: 350px;margin-left:38%;margin-top:37px;background-color: lightcyan" >
    <div style="margin-left: 37px;margin-bottom: 80px ;margin-top: 2px">
        <label class="label" >注册</label>
    </div>
    <form action="register" method="get" class="form-horizontal">
        <div class="form-group" style="margin-bottom: 50px;">
            <div style="float: left; margin-top: 6px; margin-left: 15px;">
                <label for="inputEmail3" class="label" style="margin-left: 20px;font-size: 18px">用户名 ：</label>
            </div>
            <div class="col-sm-10" style="width: 200px">
                <input type="text" class="form-control" id="inputEmail3" placeholder="" name="UserCode">
            </div>
        </div>
        <div class="form-group" style="margin-bottom: 50px;">
            <div style="float: left; margin-top: 6px; margin-left: 30px;">
                <label for="inputPassword3" class="label" style="margin-left: 14px;font-size: 18px">密  码 ：</label>
            </div>
            <div class="col-sm-10" style="width: 200px;margin-left: 0px" >
                <input type="password" class="form-control" id="inputPassword3"  name="Password">
            </div>
        </div>
        <div class="form-group" style="margin-bottom: 50px;">
            <div style="float: left; margin-top: 6px; margin-left: 30px;">
                <label for="inputPassword3" class="label" style="margin-left: 14px;font-size: 18px">性  别 ：</label>
            </div>
            <div class="col-sm-10" style="width: 200px;margin-left: 0px" >
                <select class="select" name="gender">
                    <option value="男">男</option>
                    <option value="女">女</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <div style="float: left; margin-top: 7px; margin-left: 16px;">
                <label for="inputPassword3" class="label" style="margin-left: 0px;font-size: 18px" >出生日期 ：</label>
            </div>
            <div class="col-sm-10" style="width: 200px;margin-left: 0px" >
                <input type="date" class="form-control" id="2"  name="birthday">
            </div>
        </div>
        <div style="margin-left: 40px ;margin-top: 40px">
            <input type="submit" class="login-button" style="margin-top: 40px;margin-left: 4px">
        </div>

    </form>
</div>
</body>
</html>
