<%--
  Created by IntelliJ IDEA.
  User: qgl
  Date: 2018/9/25
  Time: 9:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
    <link rel="icon" href="../images/bqbh.jpg" type="image/x-icon">
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
    <title>访客信息</title>
    <script type="text/javascript" src="../chartJS/echarts.js"></script>
</head>

<style type="text/css">
    td {
        text-align: center;
    }

    .query{
        display: inline-block;
        *display: inline;
        zoom: 1;
        padding: 6px 20px;
        margin: 0;
        cursor: pointer;
        border: 1px solid #bbb;
        overflow: visible;
        font: bold 13px arial, helvetica, sans-serif;
        text-decoration: none;
        white-space: nowrap;
        color: #555;
        background-clip: padding-box;
        -moz-border-radius: 3px;
        -webkit-border-radius: 3px;
        border-radius: 3px;
        box-shadow: 0 8px 16px 0 rgba(204, 196, 14, 0.2), 0 6px 20px 0 rgba(204, 196, 14, 0.19);
    }

    .query{
        padding: 8px 12px;
    }

    .query{
        color: #68563d;
        text-shadow: 0 1px 0 rgba(0,0,0,.2);
    }

    .query{
        background-color: #f6ea08;
        border-color: #d9d00f;
    }

    .query:hover{
        background-color: #f8ff4e;
    }

    .query:active{
        background: #f6a017;
    }

    .skip{
        display: inline-block;
        *display: inline;
        zoom: 1;
        padding: 6px 20px;
        margin: 0;
        cursor: pointer;
        border: 1px solid #bbb;
        overflow: visible;
        font: bold 13px arial, helvetica, sans-serif;
        text-decoration: none;
        white-space: nowrap;
        color: #555;
        background-clip: padding-box;
        -moz-border-radius: 3px;
        -webkit-border-radius: 3px;
        border-radius: 3px;
        box-shadow: 0 8px 16px 0 rgba(48, 253, 29, 0.2), 0 6px 20px 0 rgba(69, 248, 78, 0.19);
    }

    .skip{
        padding: 5px 10px;
    }

    .skip{
        color: #68563d;
        text-shadow: 0 1px 0 rgba(0,0,0,.2);
    }

    .skip{
        background-color: #50e3af;
        border-color: #87d975;
    }

    .skip:hover{
        background-color: #57ff65;
    }

    .skip:active{
        background: #9bec36;
    }
</style>

<body style="background: url(../images/bqbh_5.png); background-size: cover">
<h1 align="center" style="color: #4c4eed">《拳皇世界》访客信息</h1>
<form action="guestInfo.do" method="post" style="opacity: 0.85">
    <input type="text" id="queryDate" name="date" value="${guest.date}" style="height:25px; width:200px" placeholder="日期">
    <input type="text" id="queryUserName" name="userName" value="${guest.userName}" style="height:25px; width:200px" placeholder="用户名">
    <input type="text" id="queryType" name="type" value="${guest.type}" style="height:25px; width:200px" placeholder="访问类型">
    <input type="text" id="queryIp" name="ip" value="${guest.ip}" style="height:25px; width:200px" placeholder="IP">
    <input type="text" id="queryUserAgent" name="userAgent" value="${guest.userAgent}" style="height:25px; width:200px" placeholder="访客浏览器版本"> &nbsp;
    <input class="query" type="submit" value="查 &nbsp; 询" />
</form>

<div id="guestInfo">
<table border="3" width="80%">
    <tr>
        <th>日期</th>
        <th>用户名</th>
        <th>访问类型</th>
        <th>访客IP</th>
        <th>访客浏览器版本</th>
    </tr>
    <c:forEach items="${guestInfos}" var="history">
        <tr>
            <td><c:out value="${history.date}"/></td>
            <td><c:out value="${history.userName}"/></td>
            <td><c:out value="${history.type}"/></td>
            <td><c:out value="${history.ip}"/></td>
            <td><c:out value="${history.userAgent}"/></td>
        </tr>
    </c:forEach>
</table>

<br>
    <div id="page" style="margin-left: 20%">
        <label>第${page.currentPage}/${page.totalPage}页 共${page.totalRows}条</label>
        <a href="guestInfo.do?currentPage=0&date=${guest.date}&userName=${guest.userName}&type=${guest.type}&ip=${guest.ip}&userAgent=${guest.userAgent}">首页</a>
        <a href="guestInfo.do?currentPage=${page.currentPage-1}&date=${guest.date}&userName=${guest.userName}&type=${guest.type}&ip=${guest.ip}&userAgent=${guest.userAgent}"
           onclick="return checkFirst()">上一页</a>
        <a href="guestInfo.do?currentPage=${page.currentPage+1}&date=${guest.date}&userName=${guest.userName}&type=${guest.type}&ip=${guest.ip}&userAgent=${guest.userAgent}"
           onclick="return checkNext()">下一页</a>
        <a href="guestInfo.do?currentPage=${page.totalPage}&date=${guest.date}&userName=${guest.userName}&type=${guest.type}&ip=${guest.ip}&userAgent=${guest.userAgent}">尾页</a>
        跳转到:
        <input type="number" style="width:50px" id="turnPage"/>页
        <input class="skip" type="button" onclick="startTurn()" value="跳转"/>
    </div>
</div>

<script type="text/javascript">
    function checkFirst(){
        if(${page.currentPage>1}){
            return true;
        }
        alert("已到页首, 往事不堪回首！");
        return false;
    }

    function checkNext(){
        if(${page.currentPage < page.totalPage}){
            return true;
        }
        alert("已到页尾，山穷水尽！");
        return false;
    }

    function startTurn(){
        var turnPage = document.getElementById("turnPage").value;
        var date = document.getElementById("queryDate").value;
        var queryUserName = document.getElementById("queryUserName").value;
        var queryType = document.getElementById("queryType").value;
        var queryIp = document.getElementById("queryIp").value;
        var userAgent = document.getElementById("queryUserAgent").value;

        if(turnPage > ${page.totalPage}){
            alert("就算服务器使上劲浑身解数，也不能超过最大页数啊！");
            return false;
        }

        if(turnPage < 0){
            alert("服务器差点就崩溃了，页数不能为负数呀！");
            return false;
        }

        if (turnPage == null || "" == turnPage) {
            alert("服务器差点就崩溃了，页数不能为空呀！");
            return false;
        }
        var shref="guestInfo.do?currentPage=" + turnPage + "&type=" + "&date=" + date + "&queryUserName=" + queryUserName + queryType + "&ip=" + queryIp + "&userAgent=" + userAgent;
        window.location.href=shref;
    }
</script>
</body>
</html>