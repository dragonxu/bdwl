<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../jsp/header.jsp"></jsp:include>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@   page   import= "com.liaoin.util.Base64"%>
<style>
	.table tr td{
		text-align: center;
	}
	.table tr th{
		text-align: center;
	}
</style>
</head>
<body>
<div class="wrap js-check-wrap">
	<ul class="nav nav-tabs">
		<li class="active"><a href="<%=request.getContextPath()%>/Order/index">订单管理</a></li>
	</ul>
	<form class="well form-search js-ajax-form" action="<%=request.getContextPath()%>/Order/index">
		订单状态：
		<select class="select_2" name="orderStatus">
			<option value="">全部</option>
			<c:forEach items="${orderStatusLists}" var="orderStatusList">
				<option value="${orderStatusList}" <c:if test="${orderStatusList == orderStatus}">selected</c:if>>${orderStatusList.name}</option>
			</c:forEach>
		</select> &nbsp;&nbsp;
		售货机：
		<select class="select_2" name="assetId">
			<option value="">全部</option>
			<c:forEach items="${assetInfos}" var="assetInfo">
				<option value="${assetInfo.id}" <c:if test="${assetId == assetInfo.id}">selected</c:if>>${assetInfo.siteName}</option>
			</c:forEach>
		</select> &nbsp;&nbsp;
		交易时间：
		<input type="text" name="beginDate" class="js-datetime" style="width: 200px;" value="${beginDate}" placeholder="开始时间..."> -
		<input type="text" name="endDate" class="js-datetime" style="width: 200px;" value="${endDate}" placeholder="结束时间...">
		<input type="submit" class="btn btn-primary" value="搜索" />
	</form>
	<form class="js-ajax-form" action="#" method="post">
		<table class="table table-hover table-bordered table-list" id="menus-table">
			<thead>
			<tr>
				<th width="50">订单总金额</th>
				<th width="50">订单消费金额</th>
				<th width="50">订单优惠金额</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>${totalPrice}</td>
				<td>${totalMoney}</td>
				<td>${totalDiscount}</td>
			</tr>
			</tbody>
		</table>
		<br>
		<br>
		<table class="table table-hover table-bordered table-list" id="menus-table">
			<thead>
			<tr>
				<th width="50">序号</th>
				<th>订单编号</th>
				<th>下单用户</th>
				<th>售货机</th>
				<th>商品名称</th>
				<th>单价</th>
				<th>数量</th>
				<th>总价</th>
				<th>订单状态</th>
				<th>支付方式</th>
				<th>交易时间</th>
				<%--<th>微信交易号</th>--%>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${pageResult.list}" var="list" varStatus="index">
				<tr>
					<td>${index.index+1}</td>
					<td>${list.orderNo}</td>
					<td>${Base64.decryptBASE64(list.weiXinUser.nickname)}</td>
					<td>${list.assetInfo.siteName}</td>
					<td>${list.goods.name}元</td>
					<td>${list.price}</td>
					<td>${list.count}</td>
					<td>${list.totalMoney}</td>
					<td>${list.orderStatus.name}</td>
					<td>${list.payType.name}</td>
					<td>
						<fmt:formatDate value="${list.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
						<%--<td>${list.transactionId}</td>--%>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<jsp:include page="../jsp/page.jsp"></jsp:include>
	</form>
</div>
<script src="<%=request.getContextPath()%>/js/common.js"></script>
</body>
</html>