<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <link rel="icon" href="../images/qx.jpg" type="image/x-icon">
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
    <title>管理员权限主界面</title>
</head>

<style type="text/css">

    .openAutoUpdateTask {
        background-color: #f81e10;
        border: 2px;
        color: #070707;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        font-size: 16px;
        border-radius: 12px;
        box-shadow: 0 8px 16px 0 rgba(248, 30, 16, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
    }

    .autoUrlContent {
        background-color: #ee1d10;
        border: 2px;
        color: #070707;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        font-size: 16px;
        border-radius: 12px;
        box-shadow: 0 8px 16px 0 rgba(248, 30, 16, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
    }

    .autoHistory {
        background-color: #f81e10;
        border: 2px;
        color: #070707;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        font-size: 16px;
        border-radius: 12px;
        box-shadow: 0 8px 16px 0 rgba(248, 30, 16, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
    }

    .queryRechargeInfo {
        background-color: #7b7bff;
        border: 2px;
        color: #070707;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        font-size: 16px;
        border-radius: 12px;
        box-shadow: 0 8px 16px 0 rgba(123, 123, 255, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
    }

    .updateRecharge {
        background-color: #fdfb6d;
        border: 2px;
        color: #070707;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        font-size: 16px;
        border-radius: 12px;
        box-shadow: 0 8px 16px 0 rgba(253, 251, 109, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
    }

    .queryGuestInfo {
        background-color: #7b7bff;
        border: 2px;
        color: #070707;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        font-size: 16px;
        border-radius: 12px;
        box-shadow: 0 8px 16px 0 rgba(62, 47, 255, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
    }

    .updateLinuxInfo {
        background-color: #fdfb6d;
        border: 2px;
        color: #070707;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        font-size: 16px;
        border-radius: 12px;
        box-shadow: 0 8px 16px 0 rgba(231, 237, 81, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
    }
</style>

<body style="background: url(../images/qx_5.png) no-repeat; background-size: cover">
<br>
<h2 style="color: #2d57e5 " align="center">《拳皇世界》服务器管理员权限</h2>
<div id="all" style="margin-left: 15%; margin-top: 5%">
    <input class="autoUrlContent" type="button" value="自动更新网站内容网页"
           onclick="javasript:window.open('autoUpdateUrlContent.do')"/> &nbsp; &nbsp;
    <input class="autoHistory" type="button" value="自动更新数据网页"
           onclick="javasript:window.open('autoInsertOnlineServerInfo.do')"/> &nbsp; &nbsp;

    <c:if test="${!isOpenedTask}">
        <input class="openAutoUpdateTask" type="button" value="开启自动更新定时任务" method="post" onclick="openAutoUpdateTask()"/>
    </c:if>

    <br><br><br><br>
    <input class="queryRechargeInfo" type="button" value="点击查询充值信息" method="post" onclick="queryRechargeInfo()"/> &nbsp; &nbsp;
    <input class="updateRecharge" type="button" value="更新服务器充值数据" method="post" onclick="updateRechargeInfo()"/> &nbsp;&nbsp;
    充值信息一共 ${rechargeSum} 条 &nbsp; &nbsp;
    <c:if test="${not empty updateRechargeInfoDate}">
        上次充值信息更新时间：${updateRechargeInfoDate}
    </c:if>
    <br><br><br><br>
    <input class="queryGuestInfo" type="button" value="点击查询访客信息" onclick="javasript:window.open('guestInfo.do')"/> &nbsp; &nbsp;
    <input class="updateLinuxInfo" type="button" value="更新Linux服务器信息" onclick="updateLinuxServerInfo()"/> &nbsp; &nbsp;
    Linux服务器信息一共 ${linuxServerSum} 条 &nbsp; &nbsp;
    <c:if test="${conflictSum == 0}">
        更新中冲突数据 ${conflictSum} 条
    </c:if>
    <br><br>
    <c:if test="${conflictSum > 0}">
        <font color="red" size="5px">更新中 ${conflictSum} 条数据有冲突（有新服），冲突内容请查看console控制台日志信息！</font>
    </c:if>
    <c:if test="${not empty updateLinuxServerInfoDate}">
        上次Linux服务器更新时间：${updateLinuxServerInfoDate}
    </c:if>

</div>
<script type="text/javascript">
    function openAutoUpdateTask() {
        alert("还需：${waitingTime} 后才能开启线程，请耐心等待......");
        var shref = "${pageContext.request.contextPath}/admin/adminShow.do?isNowStartTask=true";
        window.location.href = shref;
    }

    function updateLinuxServerInfo() {
        var shref = "${pageContext.request.contextPath}/admin/adminShow.do?isUpdateLinuxServerInfo=true";
        window.location.href = shref;
    }

    function queryRechargeInfo() {
        var shref = "${pageContext.request.contextPath}/recharge/rechargeInfo.do";
        window.location.href = shref;
    }

    function updateRechargeInfo() {
        var shref = "${pageContext.request.contextPath}/admin/adminShow.do?isUpdateRechargeInfo=true";
        window.location.href = shref;
    }
</script>

</body>
</html>
