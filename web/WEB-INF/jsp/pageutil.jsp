<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
    <title>数据分页测试</title>
</head>

    <label>第${page.currentPage}/${page.totalPage}页 共${page.totalRows}条</label>
    <a href="historyByCondition.do?currentPage=0">首页</a>
    <a href="historyByCondition.do?currentPage=${page.currentPage-1}" onclick="return checkFirst()">上一页</a>
    <a href="historyByCondition.do?currentPage=${page.currentPage+1}" onclick="return checkNext()">下一页</a>
    <a href="historyByCondition.do?currentPage=${page.totalPage}">尾页</a> 跳转到:
    <input type="number" style="width:50px" id="turnPage" />页
    <input type="button" onclick="startTurn()" value="跳转" />

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
            if(turnPage > ${page.totalPage}){
                alert("就算服务器使上劲浑身解数，也不能超过最大页数啊！");
                return false;
            }

            if (turnPage == null || "" == turnPage) {
                alert("服务器差点就崩溃了，页数不能为空呀！")
                return false;
            }

            var shref="historyByCondition.do?currentPage="+turnPage;
            window.location.href=shref;
        }
    </script>

</body>
</html>
