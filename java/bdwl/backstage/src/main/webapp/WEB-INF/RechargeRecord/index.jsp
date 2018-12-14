<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@   page   import= "com.liaoin.util.Base64"%>
<jsp:include page="../jsp/header.jsp"></jsp:include>
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
		<li class="active"><a href="<%=request.getContextPath()%>/RechargeRecord/index">充值记录</a></li>
	</ul>
	<form class="well form-search js-ajax-form" action="<%=request.getContextPath()%>/RechargeRecord/index">
		交易时间：
		<input type="text" name="beginDate" class="js-datetime" style="width: 200px;" value="${beginDate}" placeholder="开始时间..."> -
		<input type="text" name="endDate" class="js-datetime" style="width: 200px;" value="${endDate}" placeholder="结束时间...">
		<input type="submit" class="btn btn-primary" value="搜索" />
	</form>
	<form class="js-ajax-form" action="#" method="post">
		<%--<div class="table-actions">--%>
		<%--<button class="btn btn-primary btn-small js-ajax-submit" type="submit" data-action="<%=request.getContextPath()%>/Role/listorders">排序</button>--%>
		<%--</div>--%>
		<table class="table table-hover table-bordered table-list" id="menus-table">
			<thead>
			<tr>
				<th width="50">会员总数</th>
				<th width="50">充值金额</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>${count}</td>
				<td>${totalMoney}</td>
			</tr>
			</tbody>
		</table>
		<br>
		<br>
		<table class="table table-hover table-bordered table-list" id="menus-table">
			<thead>
			<tr>
				<th width="50">序号</th>
				<th>充值用户</th>
				<th>充值金额</th>
				<th>赠送金额</th>
				<th>充值时间</th>
				<th>微信交易号</th>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${pageResult.list}" var="list" varStatus="index">
				<tr>
					<td>${index.index+1}</td>
					<td>${Base64.decryptBASE64(list.weiXinUser.nickname)}</td>
					<td>${list.money}</td>
					<td>${list.discount}</td>
					<td>${list.updateTime}</td>
					<td>${list.transactionId}</td>
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