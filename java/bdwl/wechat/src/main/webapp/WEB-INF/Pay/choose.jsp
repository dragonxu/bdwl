<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="shortcut icon" href="/gp/favicon.ico">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<title>选择优惠券</title>
	<link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
	<link rel="stylesheet" type="text/css" href="/assets/css/swiper.min.css" />
	<link rel="stylesheet" type="text/css" href="/assets/css/coupon.css" />
	<script type="text/javascript" src="/assets/js/vendor/jquery.js"></script>
	<script type="text/javascript" src="/assets/js/vendor/swiper.min.js"></script>
	<script type="text/javascript" src="/assets/js/common.js"></script>
	<script type="text/javascript" src="/assets/js/pay/choose.js"></script>
	<script type="text/javascript" src="/assets/js/jquery.js"></script>
	<script type="text/javascript" src="/assets/js/layer2.2/layer.js"></script>
	<script type="text/javascript">
        _SG.loadSiteSetting();
	</script>
</head>
<body class="choose-coupon-page">
<div class="app-wrapper">
	<div class="coupon-list-box">
		<c:forEach items="${userCoupons}" var="userCoupon">
			<div value="${userCoupon.id}" class="one-item">
				<div class="cp-no-box">
					<span class="tit">优惠券号</span>
					<span class="num">${userCoupon.coupon.number}</span>
				</div>
				<div class="cp-info-box">
				<span class="price-box">
					<i class="unit">￥</i>${userCoupon.coupon.money}
				</span>
					<div class="valid-date">
							${userCoupon.coupon.beginDate}至<br/>${userCoupon.coupon.endDate}使用
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="choose-act-box">
		<a onclick="choose_btn(this,'<%=request.getContextPath()%>/Pay/pay')" value="" class="choose-btn">确定选择</a>
	</div>
</div>
</body>
</html>