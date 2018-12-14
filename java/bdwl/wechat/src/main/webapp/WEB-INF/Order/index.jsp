<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="/gp/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>我的订单</title>
<link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/swiper.min.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/order.css" />
<script type="text/javascript" src="/assets/js/vendor/jquery.js"></script>
<script type="text/javascript" src="/assets/js/vendor/swiper.min.js"></script>
<script type="text/javascript" src="/assets/js/common.js"></script>
	<script type="text/javascript" src="/assets/js/constant.js"></script>
	<script type="text/javascript" src="/assets/js/order/index.js"></script>
	<script type="text/javascript" src="/assets/js/jquery.js"></script>
	<script type="text/javascript" src="/assets/js/layer2.2/layer.js"></script>
<script type="text/javascript">
	_SG.loadSiteSetting();
</script>
</head>
<body>
<div class="app-wrapper">
	<div id="orderStatusTabSwiper" class="status-tab-box">
		<div class="swiper-wrapper">
			<div class="swiper-slide">
				<a href="<%=request.getContextPath()%>/Order/index" class="one-item <c:if test="${empty orderStatus}">active</c:if>">全部</a>
			</div>
			<div class="swiper-slide">
				<a href="<%=request.getContextPath()%>/Order/index?orderStatus=SHIPMENT_SCUEESS" class="one-item <c:if test="${orderStatus == 'SHIPMENT_SCUEESS'}">active</c:if>">出货成功</a>
			</div>
			<div class="swiper-slide">
				<a href="<%=request.getContextPath()%>/Order/index?orderStatus=SHIPMENT_FAIL" class="one-item <c:if test="${orderStatus == 'SHIPMENT_FAIL'}">active</c:if>">出货失败</a>
			</div>
		</div>
	</div>
	<!-- 包裹列表-s  -->
	<c:forEach items="${orders}" var="order">
		<div class="one-package-box">
			<div class="pkg-head">
				<%--<span class="tit-box"><i class="mark"></i>${order.orderNo}</span>--%>
				<span class="tit-box">订单编号：${order.orderNo}</span>
				<i class="status-txt ok">${order.orderStatus.name}</i>
			</div>
			<div class="pkg-goods-box">
				<div class="one-item">
					<img src="${order.goods.picture}" class="pic" />
					<a href="javascript:;" class="name">${order.goods.name}</a>
					<span class="price"><i class="unit">￥</i>${order.price}</span>
					<span class="num">x${order.count}</span>
				</div>
			</div>
			<div class="pkg-summary">
				共1件商品&nbsp;&nbsp;合计:<span class="price"><i class="unit">￥</i>${order.totalMoney}</span><span class="old-price">￥60</span>(已优惠<span class="stress">￥${order.discount}</span>)
			</div>
			<div class="pkg-operate">
				<a onclick="del('${order.id}')" class="order-btn ok">删除订单</a>
			</div>
		</div>
	</c:forEach>
	<!-- 包裹列表-e  -->
	<div class="no-more-data">
		----------&nbsp;&nbsp;我们的底线&nbsp;&nbsp;----------
	</div>
</div>
<script type="text/javascript">
	new Swiper('#orderStatusTabSwiper',{
		freeMode: true,
		slidesPerView: 'auto',
		resistanceRatio: 0,
	});
</script>

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
</body>
</html>