<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>跳转界面</title>
    <link rel="icon" href="images/xyx.jpg" type="image/x-icon"/>
</head>

<style type="text/css">

    .skip {
        background-color: #7b7bff;
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        border-radius: 12px;
        box-shadow: 0 8px 16px 0 rgba(253, 251, 109, 0.2), 0 6px 20px 0 rgba(231, 237, 81, 0.19);
    }

</style>

<body style="background: url(images/xyx.jpg) no-repeat; background-size: cover">
<div style="margin-left: 30%; margin-top: 20%">
    <form id="login" action="${pageContext.request.contextPath }/login.do" method="POST" style="top: 100px; left: 200px;">
            <input class="skip" type="submit" value="进入登录界面"/>
    </form>
</div>
</body>

</html>