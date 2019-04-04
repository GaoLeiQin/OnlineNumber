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
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
    <title>所有服务器信息</title>

    <link rel="stylesheet" type="text/css" href="../chartJS/datatables-1.10.13/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="../chartJS/bootstrap-4.0.0/bootstrap.min.css">
    <script type="text/javascript" charset="utf8" src="../chartJS/datatables-1.10.13/js/jquery.js"></script>
    <script type="text/javascript" charset="utf8" src="../chartJS/datatables-1.10.13/js/jquery.dataTables.js"></script>

</head>

<style type="text/css">
    td {
        text-align: center;
    }

    input{
        border-radius:6px;
        border:2px solid #ccc;
        height:35px;
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

    #allServerInfo {
        width: 98%;
    }
</style>

<body style="background: url(../images/trbjd_3.png) ; background-size: cover">
<br>
<h1 align="center" style="color: #4c4eed">《拳皇世界》服务器信息查询</h1>
<br>

<form action="queryServerInfoByCondition.do" method="post" style="opacity: 0.88"> &nbsp; &nbsp;
    <input type="text" name="channel" value="${serverInfo.channel}" style="height:25px; width:150px" placeholder="渠道名称" >
    <input type="text" name="zoneId" value="${serverInfo.zoneId}" style="height:25px; width:150px" placeholder="ZoneId">
    <input type="text" name="serverName" value="${serverInfo.serverName}" style="height:25px; width:150px" placeholder="服务器名称">
    <input type="text" name="optOrId" value="${serverInfo.optOrId}" style="height:25px; width:150px" placeholder="OptOrId">
    <input type="text" name="innerIp" value="${serverInfo.innerIp}" style="height:25px; width:150px" placeholder="内网IP">
    <input type="text" name="outerIp" value="${serverInfo.outerIp}" style="height:25px; width:150px" placeholder="外网IP">
    <input type="text" name="hostName" value="${serverInfo.hostName}" style="height:25px; width:150px" placeholder="主机名">
    <input type="text" name="openTime" value="${serverInfo.openTime}" style="height:25px; width:150px" placeholder="开服时间" >
    <input type="text" name="openDays" value="${serverInfo.openDays}" style="height:25px; width:200px" placeholder="开服天数:正数 > 负数 <" >
    <input class="query" type="submit" value="查 &nbsp; 询" />
</form>

<table id="allServerInfo" class="table-bordered">
    <thead>
    <tr>
        <th>渠道</th>
        <th>ZoneId</th>
        <th>服务器名称</th>
        <th>Opt Or ID</th>
        <th>内网IP</th>
        <th>外网IP</th>
        <th>HostName</th>
        <th>开服时间</th>
        <th>开服天数</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${serverInfoByCondition}" var="info">
        <tr>
            <td><c:out value="${info.channel}"/></td>
            <td><c:out value="${info.zoneId }"/></td>
            <td><c:out value="${info.serverName }"/></td>
            <td><c:out value="${info.optOrId }"/></td>
            <td><c:out value="${info.innerIp }"/></td>
            <td><c:out value="${info.outerIp }"/></td>
            <td><c:out value="${info.hostName }"/></td>
            <td><c:out value="${info.openTime }"/></td>
            <td><c:out value="${info.openDays }"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script id="showTables" type="text/javascript">

    $(document).ready( function () {
        $('#allServerInfo').DataTable({
            language: {
                "info": " &nbsp; &nbsp; 显示第 _START_ 到 _END_ 条记录 一共 _TOTAL_ 条记录",
                "infoEmpty": "显示从 0 到 0 of 0 条记录",
                "lengthMenu": " &nbsp; &nbsp; 每页显示 _MENU_ 条记录",
                "emptyTable": "查询无记录",
                "loadingRecords": "加载中...",
                "zeroRecords": "查询无记录",
                "paginate": {
                    "first": "首页",
                    "previous": "上一页",
                    "next": "下一页 &nbsp;",
                    "last": "末页"
                }
            },

            createdRow: function (row, data, index) {
                if (index % 2 == 0) {
                    $('td', row).css('font-weight',"bold").css("color","#6363ff");
                    $('td', row).css("background", "#bcc4c0");
                }else {
                    $('td', row).css('font-weight',"bold").css("color","#fffcfc");
                    $('td', row).css("background", "#30aa7b");
                }
            },

            // 配置参数
            paging: true,
            searching: false,
            serverSide: false,
            autoWidth: false,
            lengthMenu: [15, 30, 45, 60],

        });
    } );

</script>


</body>
</html>
