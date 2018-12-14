<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../jsp/header.jsp"></jsp:include>
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
		<li><a href="<%=request.getContextPath()%>/PushUser/index">消息通知管理员</a></li>
		<li class="active"><a href="#">添加通知管理员</a></li>
	</ul>
	<form class="well form-search" action="<%=request.getContextPath()%>/PushUser/users">
		关键字：
		<input type="text" name="keyword" style="width: 200px;" value="${keyword}" placeholder="请输入关键字...">
		<input type="submit" class="btn btn-primary" value="搜索" />
	</form>
	<form class="js-ajax-form" action="#" method="post">
		<%--<div class="table-actions">--%>
		<%--<button class="btn btn-primary btn-small js-ajax-submit" type="submit" data-action="<%=request.getContextPath()%>/Role/listorders">排序</button>--%>
		<%--</div>--%>
		<table class="table table-hover table-bordered table-list" id="menus-table">
			<thead>
			<tr>
				<th width="50">序号</th>
				<th>名称</th>
				<th>头像</th>
				<th>余额</th>
				<th width="180">操作</th>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${pageResult.list}" var="list" varStatus="index">
				<tr>
					<td>${index.index+1}</td>
					<td>${Base64.decryptBASE64(list.nickname)}</td>
					<td>
						<img src="${list.headimgurl}" style="height: 80px;width: 80px;">
					</td>
					<td>${list.balance}</td>
					<td>
						<a href="<%=request.getContextPath()%>/PushUser/add?id=${list.id}" data-msg="确定要添加吗？" class="js-ajax-delete">添加</a>
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