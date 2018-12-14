<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<li class="active"><a href="<%=request.getContextPath()%>/Role/index">角色管理</a></li>
		<li><a href="<%=request.getContextPath()%>/Role/add">添加角色</a></li>
	</ul>
	<form class="js-ajax-form" action="#" method="post">
		<%--<div class="table-actions">--%>
		<%--<button class="btn btn-primary btn-small js-ajax-submit" type="submit" data-action="<%=request.getContextPath()%>/Role/listorders">排序</button>--%>
		<%--</div>--%>
		<table class="table table-hover table-bordered table-list" id="menus-table">
			<thead>
			<tr>
				<th width="50">序号</th>
				<th>名称</th>
				<th>描述</th>
				<th>状态</th>
				<th width="180">操作</th>
			</tr>
			</thead>

			<tbody>
			<c:forEach items="${pageResult.list}" var="list" varStatus="index">
				<tr>
					<td>${index.index+1}</td>
					<td>${list.name}</td>
					<td>${list.introduce}</td>
					<td>${list.roleStatus.name}</td>
					<td>
						<a href="<%=request.getContextPath()%>/Role/power?roleId=${list.id}">权限设置</a> |
						<a href="<%=request.getContextPath()%>/Role/update?id=${list.id}">编辑</a> |
						<a href="<%=request.getContextPath()%>/Role/delete?id=${list.id}" data-msg="确定要删除吗？" class="js-ajax-delete">删除</a>
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