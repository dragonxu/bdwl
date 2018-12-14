<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="/gp/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>货物详情</title>
<link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/swiper.min.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/goods.css" />
<script type="text/javascript" src="/assets/js/vendor/jquery.js"></script>
<script type="text/javascript" src="/assets/js/vendor/swiper.min.js"></script>
<script type="text/javascript" src="/assets/js/common.js"></script>
<script type="text/javascript">
	_SG.loadSiteSetting();
</script>
</head>
<body class="body-white goods-detail-page">
<div class="app-wrapper">
	<div class="goods-head-box">
		<img src="${goods.picture}" class="pic" />
	</div>
	<div class="goods-info-box">
		<span class="name">${goods.name}<span style="display: inline-block;font-size: 16px;margin-left: 10px;color: red"><c:if test="${goods.discount > 0}">${discount}折</c:if></span></span>
		<%--<span class="summary">已售${goods.sales}</span>--%>
		<span class="price-box"><i class="unit">￥</i>${price}</span>
	</div>
	<div class="go-pay-box">
		<a href="<%=request.getContextPath()%>/User/index" class="uc-btn"></a>
		<a href="<%=request.getContextPath()%>/Pay/pay" class="pay-btn">去结算</a>
	</div>
</div>
</body>
</html>