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
		<li class="active"><a href="<%=request.getContextPath()%>/Goods/index">商品管理</a></li>
	</ul>
	<form class="well form-search js-ajax-form" action="<%=request.getContextPath()%>/Goods/index">
		关键字：
		<input type="text" name="keyword" style="width: 200px;" value="${keyword}" placeholder="请输入关键字...">
		<input type="submit" class="btn btn-primary" value="搜索" />
		<button class="btn btn-primary btn-small js-ajax-submit" style="height: 35px;border-radius: 4px;" type="submit" data-action="<%=request.getContextPath()%>/Goods/synchronization">商品同步</button>
	</form>
	<form class="js-ajax-form" action="#" method="post">
		<table class="table table-hover table-bordered table-list" id="menus-table">
			<thead>
			<tr>
				<th width="50">序号</th>
				<th>名称</th>
				<th>缩略图</th>
				<th>价格/元</th>
				<th>商品编号</th>
				<th>折扣</th>
				<th>折扣开始时间</th>
				<th>折扣结束时间</th>
				<th width="180">操作</th>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${pageResult.list}" var="list" varStatus="index">
				<tr>
					<td>${index.index+1}</td>
					<td>${list.name}</td>
					<td>
						<img src="${list.picture}" style="height: 80px;width: 80px;">
					</td>
					<td>${list.price}元</td>
					<td>${list.number}</td>
					<td>${list.discount}</td>
					<td>
						<fmt:formatDate value="${list.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<fmt:formatDate value="${list.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<a href="<%=request.getContextPath()%>/Goods/update?id=${list.id}">活动管理</a>
					</td>
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