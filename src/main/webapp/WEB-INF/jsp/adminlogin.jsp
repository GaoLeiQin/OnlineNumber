<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>登录</title>
    <link rel="icon" href="/KofWorld/images/login.png" type="image/x-icon"/>
    <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
    <script type="text/javascript" src="/KofWorld/chartJS/jquery.min.js"></script>
    <script type="text/javascript" src="/KofWorld/chartJS/jquery.md5.js"></script>
</head>

<style type="text/css">

    .guest{
        display: inline-block;
        *display: inline;
        zoom: 1;
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

    .guest{
        padding: 10px 16px;
    }

    .guest{
        color: #fffcfc;
        text-shadow: 0 1px 0 rgba(0,0,0,.2);
    }

    .guest{
        background-color: #4c55ff;
        border-color: #5b2ed9;
    }

    .guest:hover{
        background-color: #5316ff;
    }

    .guest:active{
        background: #30a2f6;
    }

    .admin{
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
        box-shadow: 0 8px 16px 0 rgba(248, 30, 16, 0.2), 0 6px 20px 0 rgba(248, 30, 16, 0.19);
    }

    .admin{
        padding: 10px 13px;
    }

    .admin{
        color: #edeed7;
        text-shadow: 0 1px 0 rgba(0,0,0,.2);
    }

    .admin{
        background-color: #f81e10;
        border-color: #d91d42;
    }

    .admin:hover{
        background-color: #ff6882;
    }

    .admin:active{
        background: #f68c19;
    }

</style>

<body style="background: url(/KofWorld/images/login.png) no-repeat; background-size: contain">

<div id="user_login" class="easyui-dialog" title="用户登录" closed="false" buttons="#dlg-buttons" style="height: 180px;width:340px;margin-top: 18%;margin-left: 40%">
    <form id="login" method="POST" style="top: 100px; left: 200px;">
        <div id="user_name">
            <label style="width: 500px;height: 300px; color: #86b6e1; font-size: 22px; font-weight: 800">用户名：</label>
            <input  style="width:200px; height: 30px" id = "userName" type="text" name="userName" maxlength="255" />
        </div>

        <div id="user_password">
            <label style="width: 200px; height:100px; color: #86b6e1;font-size: 22px; font-weight: 800">密　码：</label>
            <input  style="width:200px; height: 30px" id="passWord" type="password"  name="passWord" maxlength="255" />
        </div>
        <br>
        <div id="submit" style="margin-left: 120px">
            <input class="guest" type="button" onclick="verify()" value="登录"/> &nbsp;
            <input class="admin" type="button" onclick="adminVerify()" value="管理员登录"/>
        </div>
    </form>
</div>

<script type="text/javascript">
    function verify(){
        var userName = document.getElementById("userName").value;
        var passWord = document.getElementById("passWord").value;
        passWord = $.md5(passWord);
        if(userName === ""){
            alert("用户名不能为空！");
            return false;
        }else if(passWord === ""){
            alert("密码不能为空！");
            return false;
        }

        $.ajax({
            type:'post',
            url:'/KofWorld/verify.do',
            dataType:"json",
            async: true,
            data:{userName:userName,passWord:passWord},
            success:function(data){
                if(data.userName === null){
                    alert("用户不存在！")
                }else if (data.passWord === null) {
                    alert("密码错误！")
                } else{
                    window.location.href = "${pageContext.request.contextPath}/guest/guestShow.do?userName=" + userName;
                }
            }
        });
    }

    function adminVerify() {
        var userName = document.getElementById("userName").value;
        var passWord = document.getElementById("passWord").value;
        passWord = $.md5(passWord);
        if (userName === "") {
            alert("管理员的用户名不能为空！");
            return false;
        } else if (passWord === "") {
            alert("管理员的密码不能为空！");
            return false;
        }

        $.ajax({
            type: 'post',
            url: '/KofWorld/adminVerify.do',
            dataType: "json",//注：使用dataType，而不是Content-Type
            async: true,
            data: {userName: userName, passWord: passWord},
            success: function (data) {
                if (data.userName === null) {
                    alert("真遗憾！你没有管理员权限，建议使用访客登录！")
                } else if (data.passWord === null) {
                    alert("管理员密码错误！")
                } else {
                    window.location.href = "${pageContext.request.contextPath}/admin/adminShow.do?userName=" + userName;
                }
            }
        });
    }

</script>

</body>

</html>