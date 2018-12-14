<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="/gp/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>个人中心</title>
<link rel="stylesheet" type="text/css" href="/assets/css/common.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/swiper.min.css" />
<script type="text/javascript" src="/assets/js/vendor/jquery.js"></script>
<script type="text/javascript" src="/assets/js/vendor/swiper.min.js"></script>
<script type="text/javascript" src="/assets/js/common.js"></script>
<script type="text/javascript">
	_SG.loadSiteSetting();
</script>
</head>
<body class="user-center-page">
<div class="app-wrapper">
	<div class="uc-head-box">
		<img src="${weiXinUser.headimgurl}" class="uicon" />
		<div class="info-box">
			<span class="name">${weiXinUser.nickname}</span>
		</div>
	</div>
	<div class="uc-nav-box">
		<a href="<%=request.getContextPath()%>/User/balance" class="one-item">
			<div class="tit-box"><i class="mark uc-money-icon"></i>余额/充值</div>
			<div class="extra-box"><span class="txt">￥${weiXinUser.balance}</span><i class="arrow"></i></div>
		</a>
		<%--<a href="<%=request.getContextPath()%>/Coupon/index" class="one-item">--%>
			<%--<div class="tit-box"><i class="mark uc-card-icon"></i>我的优惠券</div>			--%>
			<%--<div class="extra-box"><i class="arrow"></i></div>--%>
		<%--</a>--%>
		<a href="<%=request.getContextPath()%>/Order/index" class="one-item">
			<div class="tit-box"><i class="mark uc-note-icon"></i>我的订单</div>
			<div class="extra-box"><i class="arrow"></i></div>
		</a>
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
                        location.reload();
//						alert("Back button isn't supported. You are leaving this application on next clicking the back button");
                    }
                }
            });
            window.history.pushState('forward', null, '');
        }
    });
</script>