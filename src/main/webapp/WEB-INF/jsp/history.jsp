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
    <title>历史在线数据</title>
    <link rel="icon" href="../images/ad.jpg" type="image/x-icon">
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">

    <script type="text/javascript" src="../chartJS/echarts.js"></script>
    <script type="text/javascript" src="../chartJS/wdatepicker/WdatePicker.js"></script>

    <link rel="stylesheet" type="text/css" href="../chartJS/datatables-1.10.13/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="../chartJS/bootstrap-4.0.0/bootstrap.min.css">
    <script type="text/javascript" charset="utf8" src="../chartJS/datatables-1.10.13/js/jquery.js"></script>
    <script type="text/javascript" charset="utf8" src="../chartJS/datatables-1.10.13/js/jquery.dataTables.js"></script>

    <link rel="stylesheet" type="text/css" href="../chartJS/pace-master/themes/blue/pace-theme-loading-bar.css">
    <script type="text/javascript" charset="utf8" src="../chartJS/pace-master/pace.js"></script>

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

    /*    .query {
            background-color: #fdfb6d;
            border: 2px;
            color: #070707;
            padding: 8px 18px;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            border-radius: 12px;
            box-shadow: 0 8px 16px 0 rgba(48, 253, 29, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
        }*/

    #historyData{
        float: left;
        width: 800px;
    }
    #showCharts{
        float: right;
        width: 1000px;
        height: 770px;
    }
</style>

<body style="background: url(../images/ad_6.png); background-size: cover">
<h1 align="center" style="color: #4c4eed">《拳皇世界》服务器历史在线人数</h1>
<div id="tips" style="margin-right: 20px; opacity: 0.4;"><font size="2px">&nbsp; 注：查询在线人数时，正数表示 > 负数表示 < </font></div>
<form action="historyByCondition.do" method="post" style="opacity: 0.85">&nbsp;
    <input type="text" id="queryDate" name="date" class="Wdate" autocomplete="off" style="height:25px; width:260px" placeholder="支持日期串模糊查询" onFocus="WdatePicker({lang:'zh-cn',skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',hmsMenuCfg: {H: [1, 6], m: [10, 6]},minDate: '2018-09-30 18:12'})"/>
    <input type="text" id="queryOfficialNum" name="officialNum" value="${historyInfo.officialNum}" style="height:25px; width:140px" placeholder="官服">
    <input type="text" id="queryMixNum" name="mixNum" value="${historyInfo.mixNum}" style="height:25px; width:140px" placeholder="混服">
    <input type="text" id="queryGatNum" name="gatNum" value="${historyInfo.gatNum}" style="height:25px; width:120px" placeholder="港澳台">
    <input type="text" id="queryTotalNum" name="totalNum" value="${historyInfo.totalNum}" style="height:25px; width:100px" placeholder="全部"> &nbsp;
    <input class="query" type="submit" value="查 &nbsp; 询" />
</form>

<div id="historyData">
    <table id="serverHistoryInfo" class="table table-striped table-bordered table-hover" width="100%" >
        <thead>
        <tr>
            <th>日期</th>
            <th>大陆官服</th>
            <th>大陆混服</th>
            <th>港澳台</th>
            <th>全部</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${serverHistoryInfos}" var="query">
            <tr>
                <td><c:out value="${query.date}"/></td>
                <td><c:out value="${query.officialNum}"/></td>
                <td><c:out value="${query.mixNum}"/></td>
                <td><c:out value="${query.gatNum}"/></td>
                <td><c:out value="${query.totalNum}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div id="page" style="margin-left: 13%">
        <label>第${page.currentPage}/${page.totalPage}页 共${page.totalRows}条</label>
        <a href="historyByCondition.do?currentPage=0&date=${historyInfo.date}&officialNum=${historyInfo.officialNum}&mixNum=${historyInfo.mixNum}&gatNum=${historyInfo.gatNum}&totalNum=${historyInfo.totalNum}">首页</a>
        <a href="historyByCondition.do?currentPage=${page.currentPage-1}&date=${historyInfo.date}&officialNum=${historyInfo.officialNum}&mixNum=${historyInfo.mixNum}&gatNum=${historyInfo.gatNum}&totalNum=${historyInfo.totalNum}"
           onclick="return checkFirst()">上一页</a>
        <a href="historyByCondition.do?currentPage=${page.currentPage+1}&date=${historyInfo.date}&officialNum=${historyInfo.officialNum}&mixNum=${historyInfo.mixNum}&gatNum=${historyInfo.gatNum}&totalNum=${historyInfo.totalNum}"
           onclick="return checkNext()">下一页</a>
        <a href="historyByCondition.do?currentPage=${page.totalPage}&date=${historyInfo.date}&officialNum=${historyInfo.officialNum}&mixNum=${historyInfo.mixNum}&gatNum=${historyInfo.gatNum}&totalNum=${historyInfo.totalNum}">尾页</a>
        跳转到:
        <input type="number" style="width:50px" id="turnPage"/>页
        <input class="skip" type="button" onclick="startTurn()" value="跳转"/>
    </div>
</div>

<script id="showTables" type="text/javascript">

    $(document).ready( function () {
        $('#serverHistoryInfo').DataTable({
            language: {
                "info": "",
                "infoEmpty": "",
                "lengthMenu": "",
                "emptyTable": "查询无记录",
                "loadingRecords": "加载中...",
                "zeroRecords": "查询无记录",
                "paginate": {
                    "first": "",
                    "previous": "",
                    "next": "",
                    "last": ""
                }
            },

            createdRow: function (row, data, index) {
                if (index == 0) {
                    $('td', row).css('font-weight',"bold").css("color","#ef3b3f");
                    $('td', row).css("background", "#d9dbd3");
                }else if (index % 2 == 0) {
                    $('td', row).css('font-weight',"bold").css("color","#6363ff");
                    $('td', row).css("background", "#bcc4c0");
                }else {
                    $('td', row).css('font-weight',"bold").css("color","#fffcfc");
                    $('td', row).css("background", "#30aa7b");
                }
            },

            // 配置参数
            paging: false,
            serverSide: false,
            autoWidth: true,
            searching: false,
            lengthMenu: [25, 50],
            scrollY: "700px",
            "order": [[0, "desc"]]
        });
    } );

</script>

<script id="pageUtil" type="text/javascript">
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
        var officialNum = document.getElementById("queryOfficialNum").value;
        var mixNum = document.getElementById("queryMixNum").value;
        var gatNum = document.getElementById("queryGatNum").value;
        var totalNum = document.getElementById("queryTotalNum").value;

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
        var shref="historyByCondition.do?currentPage=" + turnPage + "&date=" + date + "&officialNum=" + officialNum + "&mixNum=" + mixNum
            + "&gatNum=" + gatNum + "&totalNum=" + totalNum;
        window.location.href=shref;
    }
</script>

<div id="showCharts"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('showCharts'));

    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '《拳皇世界》历史在线人数图表展示',
            x: 'center',
            y: 'top'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['全部', '混服', '港澳台', '官服'],
            y: 30
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'pie', 'stack', 'tiled']},
                saveAsImage : {show: true}
            }
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data:[<c:forEach items="${serverHistoryInfos}" var="query"> " <c:out value="${query.date}"/> " , </c:forEach>]
            }
        ],
        yAxis : [
            {
                type : 'value',
                name : '在线人数'
            }
        ],
        series : [
            {
                name:'全部',
                type:'line',
                itemStyle:{
                    normal:{
                        color:'#ff0509'
                    }
                },
                data:[<c:forEach items="${serverHistoryInfos}" var="query"><c:out value="${query.totalNum}"/>, </c:forEach>]
            },
            {
                name:'混服',
                type:'line',
                itemStyle:{
                    normal:{
                        color:'#0412ff'
                    }
                },
                data:[<c:forEach items="${serverHistoryInfos}" var="query"><c:out value="${query.mixNum}"/>, </c:forEach>]
            },
            {
                name:'港澳台',
                type:'line',
                itemStyle:{
                    normal:{
                        color:'#06ff24'
                    }
                },
                data:[<c:forEach items="${serverHistoryInfos}" var="query"><c:out value="${query.gatNum}"/>, </c:forEach>]
            },

            {
                name:'官服',
                type:'line',
                itemStyle:{
                    normal:{
                        color:'#ffec06'
                    }
                },
                data:[<c:forEach items="${serverHistoryInfos}" var="query"><c:out value="${query.officialNum}"/>, </c:forEach>]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>

</body>
</html>