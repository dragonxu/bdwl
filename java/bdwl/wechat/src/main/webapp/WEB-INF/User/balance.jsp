<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="shortcut icon" href="/gp/favicon.ico">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<title>我的余额</title>
	<link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
	<link rel="stylesheet" type="text/css" href="/assets/css/swiper.min.css" />
	<link rel="stylesheet" type="text/css" href="/assets/css/balance.css" />
	<script type="text/javascript" src="/assets/js/vendor/jquery.js"></script>
	<script type="text/javascript" src="/assets/js/vendor/swiper.min.js"></script>
	<script type="text/javascript" src="/assets/js/common.js"></script>
	<script type="text/javascript" src="/assets/js/constant.js"></script>
	<script type="text/javascript" src="/assets/js/user/balance.js"></script>
	<script type="text/javascript" src="/assets/js/jquery.js"></script>
	<script type="text/javascript" src="/assets/js/layer2.2/layer.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
        _SG.loadSiteSetting();
	</script>
</head>
<body class="body-white balance-page">
<div class="app-wrapper">
	<div class="balance-head-box">
		<div class="main-box">
			<span class="txt">账户余额(元)</span>
			<span class="num">${weiXinUser.balance}</span>
		</div>
	</div>
	<div class="recharge-box">
		<span class="rec-title">充值金额</span>
		<div class="rec-list-box">
			<c:forEach items="${rechargeAmounts}" var="rechargeAmount">
				<div class="one-item" value="${rechargeAmount.id}">
					<span class="money">${rechargeAmount.money}元</span>
					<span class="tip">充值优惠<font class="stress">${rechargeAmount.discount}元</font></span>
				</div>
			</c:forEach>
		</div>
		<input type="hidden" id="rechargeAmountId" value="">
		<a onclick="recharge('<%=request.getContextPath()%>/User/balance')" class="recharge-btn">立即充值</a>
	</div>
</div>
</body>
</html>