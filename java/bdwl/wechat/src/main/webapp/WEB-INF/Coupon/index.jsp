<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="/gp/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>优惠券</title>
<link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/swiper.min.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/coupon.css" />
<script type="text/javascript" src="/assets/js/vendor/jquery.js"></script>
<script type="text/javascript" src="/assets/js/vendor/swiper.min.js"></script>
<script type="text/javascript" src="/assets/js/common.js"></script>
<script type="text/javascript">
	_SG.loadSiteSetting();
</script>
</head>
<body class="coupon-page">
<div class="app-wrapper">
	<div class="coupon-tab-box">
		<div class="one-item">
			<a href="<%=request.getContextPath()%>/Coupon/index" class="switch-btn <c:if test="${empty couponStatus}">active</c:if>">全部</a>
		</div>
		<div class="one-item">
			<a href="<%=request.getContextPath()%>/Coupon/index?couponStatus=ALREADY_USED" class="switch-btn <c:if test="${couponStatus == 'ALREADY_USED'}">active</c:if>">已使用</a>
		</div>
		<div class="one-item">
			<a href="<%=request.getContextPath()%>/Coupon/index?couponStatus=WAIT" class="switch-btn <c:if test="${couponStatus == 'WAIT'}">active</c:if>">未使用</a>
		</div>
	</div>
	<div class="coupon-list-box">
		<c:forEach items="${userCoupons}" var="userCoupon">
			<div class="one-item">
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
</div>
</body>
</html>

<script>
    jQuery(document).ready(function ($) {
        if (window.history && window.history.pushState) {
            $(window).on('popstate', function () {
                var hashLocation = location.hash;
                var hashSplit = hashLocation.split("#!/");
                var hashName = hashSplit[1];
                if (hashName !== '') {
                    var hash = window.location.hash;
                    if (hash === '') {
                        location.href="<%=request.getContextPath()%>/User/index"
//						alert("Back button isn't supported. You are leaving this application on next clicking the back button");
                    }
                }
            });
            window.history.pushState('forward', null, '');
        }
    });
</script>