<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="/gp/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>支付失败</title>
<link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/swiper.min.css" />
<script type="text/javascript" src="/assets/js/vendor/jquery.js"></script>
<script type="text/javascript" src="/assets/js/vendor/swiper.min.js"></script>
<script type="text/javascript" src="/assets/js/common.js"></script>
<script type="text/javascript">
	_SG.loadSiteSetting();
</script>
</head>
<body class="body-white pay-result-page">
<div class="app-wrapper">
	<div class="main-box">
		<div class="mark-box">
			<i class="fail"></i>
		</div>
		<div class="result-box">
			<span class="status">支付失败</span>
			<span class="tip">感谢您的支持，期待您的下次光临</span>
		</div>
		<a href="javascript:;" class="act-btn">重新支付</a>
	</div>
</div>
</body>
</html>