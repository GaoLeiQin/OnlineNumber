<%--<%@ include file="queryData.jsp" %>--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <link rel="icon" href="../images/q.jpg" type="image/x-icon">
    <title>自动更新在线人数信息</title>
</head>

<style type="text/css">
    td {
        text-align: center;
    }
</style>

<body style="background: url(../images/q_5.png) no-repeat; background-size: cover">

<%--网页自动刷新的间隔时间--%>
<% response.setIntHeader("Refresh", 56 * 2); %>
<h2 style="color: #2d57e5 " align="center">
    《拳皇世界》服务器 <fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd HH:mm:ss"/> 在线信息</h2>

<br>
<table border="1" width="50%">
    <tr>
        <th>渠道</th>
        <th>ZoneId</th>
        <th>服务器名称</th>
        <th>当前在线人数</th>
        <th>最大在线人数</th>
    </tr>
    <c:forEach items="${serverInfos}" var="urlContent">
        <tr>
            <td><c:out value="${urlContent.channel}"/></td>
            <td><c:out value="${urlContent.zoneId}"/></td>
            <td><c:out value="${urlContent.serverName}"/></td>
            <td><c:out value="${urlContent.onlineNum}"/></td>
            <td><c:out value="${urlContent.maxOnlineNum}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
