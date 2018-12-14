<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>后台管理中心</title>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="robots" content="noindex,nofollow">
    <link href="<%=request.getContextPath()%>/assets/css/admin_login.css" rel="stylesheet" />
    <style>
        #login_btn_wraper{
            text-align: center;
        }
        #login_btn_wraper .tips_success{
            color:#fff;
        }
        #login_btn_wraper .tips_error{
            color:#DFC05D;
        }
        .tips_error{
            display: block;
            text-align: center;
            color: #fff;
        }
        .tips_success{
            display: block;
            text-align: center;
            color: #fff;
        }
        #login_btn_wraper button:focus{outline:none;}
    </style>
    <script>
        if (window.parent !== window.self) {
            document.write = '';
            window.parent.location.href = window.self.location.href;
            setTimeout(function () {
                document.body.innerHTML = '';
            }, 0);
        }

        function formSubmit(){
            $("#loginForm").fireEvent("onsubmit");
            $("#loginForm").submit();
        }
    </script>
    <style type="text/css">
        <!--
        body, td, th {
            font-family: 微软雅黑;
            font-size: 12px;
            color: #333333;
        }
        body {
            margin: 0 auto;
            background-image: url(<%=request.getContextPath()%>/images/login/bg-1.jpg);
            background-repeat: no-repeat;
            background-position: center top;
            background-color: #F2F2F2;
            width: 1000px;
        }
        a:link {
            color: #333333;
            text-decoration: none;
        }
        a:visited {
            text-decoration: none;
            color: #333333;
        }
        a:hover {
            text-decoration: none;
            color: #01A2AA;
        }
        a:active {
            text-decoration: none;
            color: #01A2AA;
        }
        .login2016 {
            width: auto;
            height: 455px;
            padding-top: 305px;
            padding-left: 362px;
            padding-right: 343px;
        }
        #logtab {
        }
        #logtab td {
            height: 55px;
        }
        .reglink {
            color: #FFFFFF;
            font-weight: bold;
            font-size: 14px;
        }
        .reglink a {
            color: #FFFFFF!important;
        }
        #logtab input {
            width: 224px;
            height: 38px;
            line-height: 38px;
            background-color: #FFFFFF;
            padding-left: 50px;
            border: 0px #fff solid;
            color: #666666;
            font-size: 14px;
            border-radius: 3px;
        }
        #logtab .input1 {
            background-image: url(<%=request.getContextPath()%>/images/login/login_3.jpg);
            background-repeat: no-repeat;
            background-position: left center;
        }
        #logtab .input2 {
            background-image: url(<%=request.getContextPath()%>/images/login/login_6.jpg);
            background-repeat: no-repeat;
            background-position: left center;
        }
        #logtab .input3 {
            width: 75px!important;
            background-image: url(<%=request.getContextPath()%>/images/login/login_11.jpg);
            background-repeat: no-repeat;
            background-position: left center;
        }
        .bottom2016 {
            width: 1000px;
            height: 105px;
            padding-top: 35px;
            line-height: 35px;
            text-align: center;
            color: #878787;
        }
        .bottom2016 a {
            color: #878787!important;
        }
        -->
    </style>
</head>
<body>
<div class="login2016">
    <form method="post" name="login" action="<%=request.getContextPath()%>/Login/doLogin" autoComplete="off" class="js-ajax-form" id="loginForm">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="logtab">
            <tr>
                <td><input type="text" name="phone" id="js-admin-name" class="input1" placeholder="手机号"/></td>
            </tr>
            <tr>
                <td><input type="password" name="password"  class="input2" placeholder="密码"/></td>
            </tr>
            <tr>
                <td valign="middle">
                    <button type="submit" class="js-ajax-submit" style="background-image: url(<%=request.getContextPath()%>/images/login/login_14.jpg);width: 274px;height: 40px;letter-spacing: 10px;font-size: 18px;color: #fff;">登录</button></td>
            </tr>
        </table>
    </form>
</div>
<div class="bottom2016">重庆市了赢科技有限公司<br />
    Copyrigth@重庆市了赢科技有限公司 技术支持：<a href="http://www.liaoin.com" target="_blank">重庆市了赢科技有限公司</a> 服务电话：13983144613
</div>
<script>
    var GV = {
        DIMAUB: "",
        JS_ROOT: "__PUBLIC__/js/",//js版本号
        TOKEN : ''	//token ajax全局
    };
</script>
<script src="<%=request.getContextPath()%>/js/wind.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script>
    ;(function(){
        document.getElementById('js-admin-name').focus();
    })();
</script>
</body>
</html>
