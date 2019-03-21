<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" href="../images/q.jpg" type="image/x-icon">
    <title>测试界面</title>
</head>

<%--获取图片透明度--%>
<%--
<style type="text/css">
    div {
        width: 100%;
        height: 100%;
        background-image: url(../images/q.jpg);
        background-repeat: no-repeat;
        background-size: cover;
        opacity: 0.3;
    }
</style>
--%>

<%--获取图片模糊度--%>
<style type="text/css">
    .user_login{
        width: 100%;
        height: 100%;
    }
    .user_login:before{
        background: url(../images/ad.jpg);
        width: 100%;
        height: 100%;
        content: "";
        position: absolute;
        top: 15%;
        left: 23%;
        z-index: -1;/*-1 可以当背景*/
        -webkit-filter: blur(3px);
        filter: blur(8px);
        opacity: 0.6;
    }
</style>

<body>

<%--<div id="bg" style="position: absolute"></div>--%>

<div class="user_login" ></div>

<%--<script type="text/javascript">
    $(function(){
        // 路径配置
        require.config({
            paths: {
                echarts: 'chartJS/'
            }
        });
        //动态加载饼状图
        require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var pie_data = ec.init(document.getElementById('pie_data'));
                pie_data.showLoading();
                var result=[];
                $.ajax({
                    url:"${pageContext.request.contextPath}/allServer/ajax.do",
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        pie_data.hideLoading();//隐藏加载动画
                        if(data != null && data != ''){
                            $(data).each(function (index){

                                result.push(data[index].cont)

                            });
                            alert(result);
                            var option = {
                                tooltip : {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data:['销售']
                                },


                                toolbox: {
                                    show : true,
                                    feature : {
                                        mark : {show: true},
                                        dataView : {show: true, readOnly: false},
                                        magicType : {show: true, type: ['line', 'bar']},
                                        restore : {show: true},
                                        saveAsImage : {show: true}
                                    }
                                },
                                xAxis: {
                                    type: 'category',
                                    data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月','八月','九月','十月','十一月','十二月']
                                },
                                yAxis: {
                                    type: 'value'
                                },
                                series: [{
                                    data: result,
                                    type: 'bar'
                                }]
                            };

                            // 为echarts对象加载数据
                            pie_data.setOption(option);
                        }
                    },
                    error:function(){
                        alert("数据加载失败");
                    }
                });

            }
        )
    });

</script>
</head>
<body>
<div id="pie_data" style="width: 900px;height:600px;"></div>--%>

// 最初的用户名密码验证

/*function showAdmin(){
var username = document.getElementById("username").value;
var pwd = document.getElementById("pwd").value;

if(username != "${username1}" && username != "${username2}"){
alert("用户名错误！建议使用访客登录");
return false;
}

if (pwd != "${pwd}"){
alert("小样，还想使用管理员权限，但你不知道密码，哈哈哈......");
return false;
}

var shref = "${pageContext.request.contextPath}/admin/adminShow.do";
window.location.href=shref;
}*/

</body>
</html>