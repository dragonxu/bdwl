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
		<li class="active"><a href="<%=request.getContextPath()%>/Menu/index">菜单管理</a></li>
		<li><a href="<%=request.getContextPath()%>/Menu/add">添加菜单</a></li>
	</ul>
	<form class="js-ajax-form" action="#" method="post">
		<div class="table-actions">
			<button class="btn btn-primary btn-small js-ajax-submit" type="submit" data-action="<%=request.getContextPath()%>/Menu/listorders" data-subcheck="true">排序</button>
		</div>
		<table class="table table-hover table-bordered table-list" id="menus-table">
			<thead>
			<tr>
				<th width="80">排序</th>
				<%--<th width="50">ID</th>--%>
				<th>应用</th>
				<th>名称</th>
				<th width="50">状态</th>
				<th width="180">操作</th>
			</tr>
			</thead>
			<tbody>
			${menuString}
			</tbody>
		</table>
	</form>
</div>
<script src="<%=request.getContextPath()%>/js/common.js"></script>
<script>
	$(document).ready(function() {
		Wind.css('treeTable');
		Wind.use('treeTable', function() {
			$("#menus-table").treeTable({
				indent : 20
			});
		});
	});

	setInterval(function() {
		var refersh_time = getCookie('refersh_time_admin_menu_index');
		if (refersh_time == 1) {
			reloadPage(window);
		}
	}, 1000);
	setCookie('refersh_time_admin_menu_index', 0);
</script>
</body>
</html>