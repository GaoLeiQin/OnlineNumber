<%--
  Created by IntelliJ IDEA.
  User: qgl
  Date: 2018/9/25
  Time: 9:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
    <link rel="icon" href="../images/trbjd.jpg" type="image/x-icon">
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
    <title>所有服务器信息</title>
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
</style>

<body style="background: url(../images/trbjd_3.png) ; background-size: cover">
<br>
<h1 align="center" style="color: #4c4eed">《拳皇世界》服务器信息查询</h1>
&nbsp; &nbsp
<a href="queryServerInfoByCondition.do?isUpdate=true"><font color="#b22222">更新服务器在线人数信息</font></a>  &nbsp; &nbsp;
<c:if test="${not empty date}">
    上次更新时间：${date}
</c:if>
<br>

<form action="queryServerInfoByCondition.do" method="post" style="opacity: 0.88">
    <input type="text" name="channel" value="${serverInfo.channel}" style="height:25px; width:150px" placeholder="渠道名称" >
    <input type="text" name="zoneId" value="${serverInfo.zoneId}" style="height:25px; width:150px" placeholder="ZoneId">
    <input type="text" name="serverName" value="${serverInfo.serverName}" style="height:25px; width:150px" placeholder="服务器名称">
    <input type="text" name="optOrId" value="${serverInfo.optOrId}" style="height:25px; width:150px" placeholder="OptOrId">
    <input type="text" name="ip" value="${serverInfo.ip}" style="height:25px; width:150px" placeholder="IP">
    <input type="text" name="hostName" value="${serverInfo.hostName}" style="height:25px; width:150px" placeholder="主机名">
    <input type="text" name="onlineNum" value="${serverInfo.onlineNum}" style="height:25px; width:150px" placeholder="在线人数:正数 > 负数 < ">
    <input type="text" name="openTime" value="${serverInfo.openTime}" style="height:25px; width:150px" placeholder="开服时间" >
    <input type="text" name="openDays" value="${serverInfo.openDays}" style="height:25px; width:150px" placeholder="开服天数:正数 > 负数 <" >
    <input type="text" name="date" value="${date}" style="display:none;">
    <input class="query" type="submit" value="查 &nbsp; 询" />
</form>

<table border="1" width="88%">
    <tr>
        <th>渠道</th>
        <th>ZoneId</th>
        <th>服务器名称</th>
        <th>Opt Or ID</th>
        <th>IP</th>
        <th>HostName</th>
        <th>在线人数</th>
        <th>开服时间</th>
        <th>开服天数</th>
    </tr>
    <c:forEach items="${serverInfoByCondition}" var="info">
        <tr>
            <td><c:out value="${info.channel}"/></td>
            <td><c:out value="${info.zoneId }"/></td>
            <td><c:out value="${info.serverName }"/></td>
            <td><c:out value="${info.optOrId }"/></td>
            <td><c:out value="${info.ip }"/></td>
            <td><c:out value="${info.hostName }"/></td>
            <td><c:out value="${info.onlineNum }"/></td>
            <td><c:out value="${info.openTime }"/></td>
            <td><c:out value="${info.openDays }"/></td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
