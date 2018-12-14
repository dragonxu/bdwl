<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- saved from url=(0047)http://tsg.com/index.php?g=Admin&m=Main&a=index -->
<html>
<head>
    <jsp:include page="../jsp/header.jsp"></jsp:include>

    <style>
        .home_info li em {
            float: left;
            width: 120px;
            font-style: normal;
        }
        li {
            list-style: none;
        }
    </style>
</head>
<body>
<div class="wrap">
    <div id="home_toptip"></div>
    <h4 class="well">开发者信息</h4>
    <div class="">
        <ul class="inline" style="margin-left: 25px;">
            <li><a href="http://www.liaoin.com/" target="_blank">重庆市了赢科技有限公司</a></li>
        </ul>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/common.js"></script>

</body>
</html>