<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="shortcut icon" href="/gp/favicon.ico">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<title>结算</title>
	<link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
	<link rel="stylesheet" type="text/css" href="/assets/css/swiper.min.css" />
	<link rel="stylesheet" type="text/css" href="/assets/css/pay.css" />
	<script type="text/javascript" src="/assets/js/vendor/jquery.js"></script>
	<script type="text/javascript" src="/assets/js/vendor/swiper.min.js"></script>
	<script type="text/javascript" src="/assets/js/common.js"></script>
	<script type="text/javascript" src="/assets/js/constant.js"></script>
	<script type="text/javascript" src="/assets/js/pay/pay.js"></script>
	<script type="text/javascript" src="/assets/js/layer2.2/layer.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
        _SG.loadSiteSetting();
	</script>
	<style type="text/css">

	</style>
</head>
<body class="pay-page">
<div class="app-wrapper">
	<div class="one-package-box">
		<div class="pkg-goods-box">
			<div class="one-item">
				<img src="${goods.picture}" class="pic" />
				<div class="info-box">
					<a href="javascript:;" class="name">${goods.name}</a>
					<span class="order-no">订单号:${order.orderNo}</span>
				</div>
				<span class="price">￥${order.price}</span>
			</div>
		</div>
		<div class="pkg-summary">
			合计:<span class="price"><i class="unit">￥</i>${order.totalMoney}</span><span class="tip">(已优惠<font class="stress">￥${order.discount}</font>)</span>
		</div>
	</div>
	<a href="<%=request.getContextPath()%>/Pay/choose?money=${order.price}" class="coupon-use-box">
		<span class="tit">我的优惠券</span>
		<div class="extra-box">
			<span class="txt">
				<c:if test="${not empty order.userCouponId}">
					已使用
				</c:if>
				<c:if test="${empty order.userCouponId}">
					未使用
				</c:if>
			</span>
			<i class="arrow"></i>
		</div>
	</a>
	<div class="pay-method-box">
		<span class="title">选择支付</span>
		<div class="list-box paytype">
			<div class="one-item active" value="PAY_WITH_BALANCE">
				<i class="way-icon pm-ye-icon"></i><span class="txt">余额支付</span><span class="stress-box">推荐使用</span>
			</div>
			<div class="one-item" value="PAY_WITH_WECHAT" style="display: none">
				<i class="way-icon pm-wx-icon"></i><span class="txt">微信支付</span>
			</div>
		</div>
		<input type="hidden" id="payType" value="PAY_WITH_BALANCE">
		<input type="hidden" id="orderId" value="${order.id}">
		<a onclick="pay('<%=request.getContextPath()%>/Pay/ok');" class="pay-btn">确认支付<font class="stress">￥${order.totalMoney}</font></a>
	</div>
</div>
</body>
</html>