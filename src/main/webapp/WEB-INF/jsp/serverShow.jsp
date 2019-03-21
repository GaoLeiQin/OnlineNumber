<%--<%@ include file="queryData.jsp" %>--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <link rel="icon" href="../images/kof_world.png" type="image/x-icon">
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
    <title>服务器当前信息</title>
    <script type="text/javascript" src="../chartJS/echarts.js"></script>
</head>

<style type="text/css">

    .history{
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
        box-shadow: 0 8px 16px 0 rgba(76, 78, 237, 0.2), 0 6px 20px 0 rgba(76, 78, 237, 0.19);
    }

    .history{
        padding: 10px 12px;
    }

    .history{
        color: #fff;
        text-shadow: 0 1px 0 rgba(0,0,0,.2);
    }

    .history{
        background-color: #3e2fff;
        border-color: #4540c4;
    }

    .history:hover{
        background-color: #5a55fe;
    }

    .history:active{
        background: #8376c4;
    }

    .linux{
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
        -webkit-box-shadow: 0 1px 0 rgba(0, 0, 0, .3), 0 2px 2px -1px rgba(0, 0, 0, .5), 0 1px 0 rgba(255, 255, 255, .3) inset;
    }

    .linux{
        padding: 10px 12px;
    }

    .linux{
        color: #fff;
        text-shadow: 0 1px 0 rgba(0,0,0,.2);
    }

    .linux{
        background-color: #f94500;
        border-color: #c43c35;
    }

    .linux:hover{
        background-color: #ee2248;
    }

    .linux:active{
        background: #ff1d98;
    }

    td {
        text-align: center;
    }

    div {
        align-items: center;
    }

    #div1{
        float: left;
    }
    #div2{
        float: right;
    }

    div4{
        float: bottom;
    }

    #showCharts{
        float: bottom;
        width: 99%;
        height: 85%;
    }
</style>

<body style="background: url(../images/kof_world_5.jpg); background-size: cover">

<%--网页自动刷新的间隔时间--%>
<%--<% response.setIntHeader("Refresh", 60 * 2); %>--%>
<br>
<h2 style="color: #2d57e5 " align="center">
        《拳皇世界》服务器 <fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd HH:mm:ss"/> 在线信息</h2>

<font color="#00008b" style="margin-left: 45%"> （建议刷新的间隔时间 > 2分钟） </font>

<div style="opacity: 0.8 ">
    <input class="history" type="button" value="在线历史信息"  onclick="javasript:window.open('historyByCondition.do')"/> &nbsp; &nbsp;
    <input class="linux" type="button" value="服务器详细信息" onclick="javasript:window.open('/KofWorld/allServer/queryServerInfoByCondition.do')" /> &nbsp; &nbsp;
</div>

<div id="showCharts"></div>

<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('showCharts'));

    var option = {
        tooltip : {
            trigger: 'axis'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: true},
                magicType : {show: true, type: ['bar', 'line']},
                saveAsImage : {show: true}
            }
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : true,
                data:[<c:forEach items="${allUrlContents}" var="urlContent"> "<c:out value="${urlContent.channel}"/> <c:out value="${urlContent.serverName}"/> " , </c:forEach>]
            }
        ],
        yAxis : [
            {
                type : 'value',
                name : '当前在线人数'
            }
        ],
        series : [
            {
                name:'在线人数',
                type:'bar',
                itemStyle: {
                    normal:{
/*                        // 定制颜色
                        color: function (params){
                            var colorList = [
                                '#C33531','#EFE42A','#64BD3D','#EE9201','#29AAE3',
                                '#B74AE5','#0AAF9F','#E89589'
                            ];
                            return colorList[params.dataIndex];
                        },*/
                        // 随机显示
                        color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
                    },
                    //鼠标悬停时：
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: '#303030',
                        opacity : 0.5
                    }
                },
                data:[<c:forEach items="${allUrlContents}" var="urlContent"><c:out value="${urlContent.onlineNum}"/>, </c:forEach>]
            }
        ]
    };

    myChart.setOption(option);
</script>

<br>
<div id="div1">
    <table border="1" width="100%">
        <tr>
            <th>官服</th>
            <th>ZoneId</th>
            <th>服务器名称</th>
            <th>当前在线人数</th>
            <th>最大在线人数</th>
        </tr>
        <c:forEach items="${officialContents}" var="urlContent">
            <tr>
                <td><c:out value="${urlContent.channel}"/></td>
                <td><c:out value="${urlContent.zoneId}"/></td>
                <td><c:out value="${urlContent.serverName}"/></td>
                <td><c:out value="${urlContent.onlineNum}"/></td>
                <td><c:out value="${urlContent.maxOnlineNum}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div id="div2">
    <table border="2" width="100%">
        <tr>
            <th>硬核混服</th>
            <th>ZoneId</th>
            <th>服务器名称</th>
            <th>当前在线人数</th>
            <th>最大在线人数</th>
        </tr>
        <c:forEach items="${hardAllianceContents}" var="urlContent">
            <tr>
                <td><c:out value="${urlContent.channel}"/></td>
                <td><c:out value="${urlContent.zoneId}"/></td>
                <td><c:out value="${urlContent.serverName}"/></td>
                <td><c:out value="${urlContent.onlineNum}"/></td>
                <td><c:out value="${urlContent.maxOnlineNum}"/></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <br>
    <br>
</div>

<div id="div3">
    <table border="3" width="50%">
        <tr>
            <th>安卓混服</th>
            <th>ZoneId</th>
            <th>服务器名称</th>
            <th>当前在线人数</th>
            <th>最大在线人数</th>
        </tr>
        <c:forEach items="${androidContents}" var="urlContent">
            <tr>
                <td><c:out value="${urlContent.channel}"/></td>
                <td><c:out value="${urlContent.zoneId}"/></td>
                <td><c:out value="${urlContent.serverName}"/></td>
                <td><c:out value="${urlContent.onlineNum}"/></td>
                <td><c:out value="${urlContent.maxOnlineNum}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<br>
<div id="div4">
    <table border="4" width="50%">
        <tr>
            <th>港澳台</th>
            <th>ZoneId</th>
            <th>服务器名称</th>
            <th>当前在线人数</th>
            <th>最大在线人数</th>
        </tr>
        <c:forEach items="${gatContents}" var="urlContent">
            <tr>
                <td><c:out value="${urlContent.channel}"/></td>
                <td><c:out value="${urlContent.zoneId}"/></td>
                <td><c:out value="${urlContent.serverName}"/></td>
                <td><c:out value="${urlContent.onlineNum}"/></td>
                <td><c:out value="${urlContent.maxOnlineNum}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<br>

<div id="fontInfo"><font color="#3538F2" style="margin-left:15%; font-weight:bold" size="5"> 线上正在运行服务器数量：${onlineServerSum} 个 </font></div>

</body>
</html>
