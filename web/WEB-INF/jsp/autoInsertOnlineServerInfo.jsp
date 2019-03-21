<%--<%@ include file="queryData.jsp" %>--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <link rel="icon" href="../images/ejthw.jpg" type="image/x-icon">
    <title>自动添加服务器历史信息</title>
</head>

<style type="text/css">
    td {
        text-align: center;
    }
</style>

<body style="background: url(../images/ejthw_5.png) no-repeat; background-size: cover">

<%--网页自动刷新的间隔时间--%>
<% response.setIntHeader("Refresh", 10 * 60); %>

<h2 style="color: #2d57e5 " align="center">
        《拳皇世界》服务器历史信息 上次更新时间：<fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd HH:mm:ss"/></h2>

<table border="1" width="70%">
    <tr>
        <th>日期</th>
        <th>大陆官服</th>
        <th>大陆混服</th>
        <th>港澳台</th>
        <th>《拳皇世界》</th>
    </tr>
    <c:forEach items="${serverHistoryInfoByLimit}" var="history">
        <tr>
            <td><c:out value="${history.date}"/></td>
            <td><c:out value="${history.officialNum}"/></td>
            <td><c:out value="${history.mixNum}"/></td>
            <td><c:out value="${history.gatNum}"/></td>
            <td><c:out value="${history.totalNum}"/></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
