<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2018/9/21
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        function register() {
            window.location.href="register.jsp"

        }
    </script>
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script>
        var pageNum;
        //文档预加载
        $(function(){
            $(".find").click(function(){
                alert("aa");
                var searchName = $(".goods").val();
                var startPage = 1;
            var table = "<table class='goodsInfo'>" +
                "<th>商品名称</th>"+
                "<th>商品价格</th>"+
                "<th>商品地址</th>"+
                "<th>评分</th>"+
                    "</table>"
                var button ="<button onclick='getPageInfo()'> 下一页</button>"
                var button2 ="<button onclick='lastpage()'> 上一页</button>"

                var nowPageHtml = "<span class='nowPage'>" +1+ "</span>"
                $("#goods").find("button").remove();
                $("#goods").append(button2);
                $("#goods").append(nowPageHtml)
                $("#goods").append(button);
                $("#goods").append(table);

                var param ={};
                param.searchName = searchName;
                param.startPage = startPage;

            $.ajax({
                type: "POST",
                url: "/found",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data:JSON.stringify(param),
                success :function (result) {
                    var obj = result;
                    var goods = obj.goodsPageInfo.list;
                    console.log(obj);
                    pageNum = obj.goodsPageInfo.pages;
                    $.each(goods,function (index,element) {
                        var theInfo = "<tr>"+"<td>"+element.goodsName+"</td>"+"<td>"+element.goodsPrice+"</td>"
                            +"<td >"+"<a href = "+element.goodsUrl+ ">"+element.goodsUrl+"</a>"+"</td>"+"<td>"+element.goodsIndex+"</td>"+"</tr>"
                        $(".goodsInfo").append(theInfo);
                    })
                }
            })



        })
            });
        function getPageInfo() {
            var nowPageIndex = $(".nowPage").html();
            if(nowPageIndex==pageNum){
                alert("这是最后一页！")
            }

            else {
                var param = {};
                $(".goodsInfo").find("tr").remove();

                ++nowPageIndex;
                $(".nowPage").html(nowPageIndex);
                param.searchName = $(".goods").val();
                param.startPage = nowPageIndex;

                $.ajax({
                    type: "POST",
                    url: "/found",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(param),
                    success: function (result) {
                        var obj = result;
                        var goods = obj.goodsPageInfo.list;
                        console.log(obj);

                        $.each(goods, function (index, element) {
                            var theInfo = "<tr>"+"<td>"+element.goodsName+"</td>"+"<td>"+element.goodsPrice+"</td>"
                                +"<td >"+"<a href = "+element.goodsUrl+ ">"+element.goodsUrl+"</a>"+"</td>"+"<td>"+element.goodsIndex+"</td>"+"</tr>"
                            $(".goodsInfo").append(theInfo);
                        })
                    }
                })
            }
        }
        function lastpage() {
            var nowPageIndex = $(".nowPage").html();
            if(nowPageIndex==1){
                alert("这是第一页！！");
            }
            else {
                var param = {};
                $(".goodsInfo").find("tr").remove();

                --nowPageIndex;
                $(".nowPage").html(nowPageIndex);

                param.searchName = $(".goods").val();
                param.startPage = nowPageIndex;

                $.ajax({
                    type: "POST",
                    url: "/found",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(param),
                    success: function (result) {
                        var obj = result;
                        var goods = obj.goodsPageInfo.list;
                        console.log(obj);
                        $.each(goods, function (index, element) {
                            var theInfo = "<tr>"+"<td>"+element.goodsName+"</td>"+"<td>"+element.goodsPrice+"</td>"
                                +"<td >"+"<a href = "+element.goodsUrl+ ">"+element.goodsUrl+"</a>"+"</td>"+"<td>"+element.goodsIndex+"</td>"+"</tr>"
                            $(".goodsInfo").append(theInfo);
                        })
                    }
                })
            }
        }
        function tuijian() {
            
        }

    </script>

</head>
<body>
<h1>Hello! ${UserCode}</h1>
<div STYLE="text-align: center">
    <p> your information:</p>
    <li>${Password}</li>
    <li>${Gender}</li>
    <li>${Status}</li>
    <li>${Birthday}</li>
</div>

<div STYLE="text-align: center">
        <input type="text" placeholder="请输入商品名称" class="goods" name="Goods">
    <button  class="find" >查询</button>
    
    <%--<button onclick="register()">注册</button>--%>
    <div id="goods"></div>

    <%--<button onclick="getPageInfo()"> 下一页</button>--%>

</div>
</body>
</html>
