<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%--<% String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()
            +":"+request.getServerPort()+path+"/"; %>--%>
<!DOCTYPE html>
<html>
<head>
    <title>图表界面</title>
    <%--<base href="<%=basePath%>">--%>
    <%--<script type="text/javascript" src="chartJS/echarts.js"></script>--%>
    <script type="text/javascript" src="../chartJS/echarts.js"></script>
</head>

<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<%--background-image: url(../images/bqbh_3.png)--%>
<div id="main" style="width: 1000px;height:800px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['官服','港澳台','混服','总在线人数']
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
                data : ['周一','周二','周三','周四','周五','周六','周日']
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
                name:'官服',
                type:'line',
                data:[320, 332, 301, 334, 390, 330, 320]
            },
            {
                name:'港澳台',
                type:'line',
                data:[150, 232, 201, 154, 190, 330, 410]
            },
            {
                name:'混服',
                type:'line',
                data:[220, 182, 191, 234, 290, 330, 310]
            },
            {
                name:'总在线人数',
                type:'line',
                data:[820, 732, 501, 634, 890, 730, 910]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>

</body>
</html>