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
    <title>充值信息</title>
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

<body style="background: url(../images/bqbh_3.png) ; background-size: cover ">

<br>

<h1 align="center" style="color: #4c4eed">《拳皇世界》部分充值信息查询</h1>

<form action="rechargeInfo.do" method="post" style="opacity: 0.85">
    <input type="text" id="queryZoneId" name="zoneId" value="${rechargeInfo.zoneId}" style="height:25px; width:165px" placeholder="zoneId">
    <input type="text" id="queryDate" name="date" value="${rechargeInfo.date}" style="height:25px; width:165px" placeholder="日期 (支持日期串模糊查询)">
    <input type="text" id="queryUserId" name="userId" value="${rechargeInfo.userId}" style="height:25px; width:150px" placeholder="账号">
    <input type="text" id="queryRoleId" name="roleId" value="${rechargeInfo.roleId}" style="height:25px; width:150px" placeholder="roleId">
    <input type="text" id="queryRmbNum" name="rmbNum" value="${rechargeInfo.rmbNum}" style="height:25px; width:150px" placeholder="充值金额">
    <input type="text" id="queryPlatId" name="platId" value="${rechargeInfo.platId}" style="height:25px; width:150px" placeholder="平台Id">
    <input type="text" id="queryPlatName" name="platName" value="${rechargeInfo.platName}" style="height:25px; width:150px" placeholder="平台名称">
    <input type="text" id="queryChargePlatSn" name="chargePlatSn" value="${rechargeInfo.chargePlatSn}" style="height:25px; width:150px" placeholder="平台订单号"> &nbsp;
    <input type="text" id="queryChargeGameSn" name="chargeGameSn" value="${rechargeInfo.chargeGameSn}" style="height:25px; width:150px" placeholder="游戏订单号"> &nbsp;
    <input class="query" type="submit" value="查 &nbsp; 询" />
</form>

<table border="1" width="90%">
    <tr>
        <th>ZoneId</th>
        <th>日期</th>
        <th>账号</th>
        <th>roleId</th>
        <th>充值金额</th>
        <th>平台Id</th>
        <th>平台名称</th>
        <th>平台订单号</th>
        <th>游戏订单号</th>
    </tr>

    <c:forEach items="${rechargeInfos}" var="recharge">
        <tr>
            <td><c:out value="${recharge.zoneId}"/></td>
            <td><c:out value="${recharge.date}"/></td>
            <td><c:out value="${recharge.userId}"/></td>
            <td><c:out value="${recharge.roleId}"/></td>
            <td><c:out value="${recharge.rmbNum}"/></td>
            <td><c:out value="${recharge.platId}"/></td>
            <td><c:out value="${recharge.platName}"/></td>
            <td><c:out value="${recharge.chargePlatSn}"/></td>
            <td><c:out value="${recharge.chargeGameSn}"/></td>
        </tr>
    </c:forEach>
</table>

<br>
<div style="margin-left: 30%">
    <label>第${page.currentPage}/${page.totalPage}页 共${page.totalRows}条</label>
    <a href="rechargeInfo.do?currentPage=0&zoneId=${rechargeInfo.zoneId}&date=${rechargeInfo.date}&userId=${rechargeInfo.userId}&roleId=${rechargeInfo.roleId}&rmbNum=${rechargeInfo.rmbNum}&platId=${rechargeInfo.platId}&platName=${rechargeInfo.platName}&chargePlatSn=${rechargeInfo.chargePlatSn}&chargeGameSn=${rechargeInfo.chargeGameSn}">首页</a>
    <a href="rechargeInfo.do?currentPage=${page.currentPage-1}&zoneId=${rechargeInfo.zoneId}&date=${rechargeInfo.date}&userId=${rechargeInfo.userId}&roleId=${rechargeInfo.roleId}&rmbNum=${rechargeInfo.rmbNum}&platId=${rechargeInfo.platId}&platName=${rechargeInfo.platName}&chargePlatSn=${rechargeInfo.chargePlatSn}&chargeGameSn=${rechargeInfo.chargeGameSn}" onclick="return checkFirst()">上一页</a>
    <a href="rechargeInfo.do?currentPage=${page.currentPage+1}&zoneId=${rechargeInfo.zoneId}&date=${rechargeInfo.date}&userId=${rechargeInfo.userId}&roleId=${rechargeInfo.roleId}&rmbNum=${rechargeInfo.rmbNum}&platId=${rechargeInfo.platId}&platName=${rechargeInfo.platName}&chargePlatSn=${rechargeInfo.chargePlatSn}&chargeGameSn=${rechargeInfo.chargeGameSn}" onclick="return checkNext()">下一页</a>
    <a href="rechargeInfo.do?currentPage=${page.totalPage}&zoneId=${rechargeInfo.zoneId}&date=${rechargeInfo.date}&userId=${rechargeInfo.userId}&roleId=${rechargeInfo.roleId}&rmbNum=${rechargeInfo.rmbNum}&platId=${rechargeInfo.platId}&platName=${rechargeInfo.platName}&chargePlatSn=${rechargeInfo.chargePlatSn}&chargeGameSn=${rechargeInfo.chargeGameSn}">尾页</a> 跳转到:
    <input type="number" style="width:50px" id="turnPage"/>页
    <input class="skip" type="button" onclick="startTurn()" value="跳转"/>
</div>

<script type="text/javascript">
    function checkFirst(){
        if(${page.currentPage>1}){
            return true;
        }
        alert("已到页首, 不能预知未来！");
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
        var turnPage=document.getElementById("turnPage").value;
        var zoneId = document.getElementById("queryZoneId").value;
        var date = document.getElementById("queryDate").value;
        var userId = document.getElementById("queryUserId").value;
        var roleId = document.getElementById("queryRoleId").value;
        var rmbNum = document.getElementById("queryRmbNum").value;
        var platId = document.getElementById("queryPlatId").value;
        var platName = document.getElementById("queryPlatName").value;
        var chargePlatSn = document.getElementById("queryChargePlatSn").value;
        var chargeGameSn = document.getElementById("queryChargeGameSn").value;

        if(turnPage > ${page.totalPage}){
            alert("就算服务器使上劲浑身解数，也不能超过最大页数啊！");
            return false;
        }

        if (turnPage == null || "" == turnPage) {
            alert("服务器差点就崩溃了，页数不能为空呀！")
            return false;
        }

        var shref="rechargeInfo.do?currentPage=" + turnPage + "&zoneId=" + zoneId +  "&date=" + date + "&userId=" + userId + "&roleId=" + roleId
            + "&rmbNum=" + rmbNum + "&platId=" + platId + "&platName=" + platName + "&chargePlatSn=" + chargePlatSn + "&chargeGameSn=" + chargeGameSn;
        window.location.href=shref;
    }
</script>

</body>
</html>
