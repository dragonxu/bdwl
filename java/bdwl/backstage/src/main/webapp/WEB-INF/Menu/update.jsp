<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../jsp/header.jsp"></jsp:include>
</head>
<body>
<div class="wrap">
	<ul class="nav nav-tabs">
		<li ><a href="<%=request.getContextPath()%>/Menu/index">菜单管理</a></li>
		<li class="active"><a href="#">编辑菜单</a></li>
	</ul>
	<form method="post" class="form-horizontal js-ajax-forms" action="<%=request.getContextPath()%>/Menu/post">
		<fieldset>
			<div class="control-group">
				<label class="control-label">上级:</label>
				<div class="controls">
					<select name="parentId">
						<option value="">作为一级菜单</option>
						${menuString}
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">名称:</label>
				<div class="controls">
					<input type="text" name="name" value="${menu.name}">
					<input type="hidden" name="id" value="${menu.id}">
					<span class="form-required">*</span>
				</div>
			</div>
			<div class="control-group" style="display: none">
				<label class="control-label">应用:</label>
				<div class="controls">
					<input type="text" name="app" id="app" value="${menu.app}">
					<span class="form-required">*</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">控制器:</label>
				<div class="controls">
					<input type="text" name="model" id="model" value="${menu.model}">
					<span class="form-required">*</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">方法:</label>
				<div class="controls">
					<input type="text" name="action" id="action" value="${menu.action}">
					<span class="form-required">*</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数:</label>
				<div class="controls">
					<input type="text" name="params" value="${menu.params}">
					例:id=3&amp;p=3
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">图标:</label>
				<div class="controls">
					<input type="text" name="icon" id="icon" value="${menu.icon}">
					<a href="http://www.thinkcmf.com/font/icons" target="_blank">选择图标</a> 不带前缀fa-，如fa-user => user
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">备注:</label>
				<div class="controls">
					<textarea name="remark" rows="5" cols="57" style="width: 500px;">${menu.remark}</textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">状态:</label>
				<div class="controls">
					<select name="status">
						<option value="BLOCK" <c:if test="${menu.status == 'BLOCK'}">selected</c:if>>显示</option>
						<option value="NONE" <c:if test="${menu.status == 'NONE'}">selected</c:if>>隐藏</option>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="form-actions">
			<button type="submit" class="btn btn-primary js-ajax-submit">提交</button>
			<a class="btn" href="<%=request.getContextPath()%>/Menu/index">返回</a>
		</div>
	</form>
</div>
<script src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/content_addtop.js"></script>
<script type="text/javascript">
	$(function() {
		$(".js-ajax-close-btn").on('click', function(e) {
			e.preventDefault();
			Wind.use("artDialog", function() {
				art.dialog({
					id : "question",
					icon : "question",
					fixed : true,
					lock : true,
					background : "#CCCCCC",
					opacity : 0,
					content : "您确定需要关闭当前页面嘛？",
					ok : function() {
						setCookie("refersh_time", 1);
						window.close();
						return true;
					}
				});
			});
		});
		/////---------------------
		Wind.use('validate', 'ajaxForm', 'artDialog', function() {
			//javascript

			//编辑器
//			editorcontent = new baidu.editor.ui.Editor();
//			editorcontent.render('content');
//			try {
//				editorcontent.sync();
//			} catch (err) {
//			}
			//增加编辑器验证规则
			jQuery.validator.addMethod('editorcontent', function() {
				try {
					editorcontent.sync();
				} catch (err) {
				}
				return editorcontent.hasContents();
			});
			var form = $('form.js-ajax-forms');
			//ie处理placeholder提交问题
			if ($.browser.msie) {
				form.find('[placeholder]').each(function() {
					var input = $(this);
					if (input.val() == input.attr('placeholder')) {
						input.val('');
					}
				});
			}

			var formloading = false;
			//表单验证开始
			form.validate({
				//是否在获取焦点时验证
				onfocusout : false,
				//是否在敲击键盘时验证
				onkeyup : false,
				//当鼠标掉级时验证
				onclick : false,
				//验证错误
				showErrors : function(errorMap, errorArr) {
					//errorMap {'name':'错误信息'}
					//errorArr [{'message':'错误信息',element:({})}]
					try {
						$(errorArr[0].element).focus();
						art.dialog({
							id : 'error',
							icon : 'error',
							lock : true,
							fixed : true,
							background : "#CCCCCC",
							opacity : 0,
							content : errorArr[0].message,
							cancelVal : '确定',
							cancel : function() {
								$(errorArr[0].element).focus();
							}
						});
					} catch (err) {
					}
				},
				//验证规则
				rules : {
					'post[post_title]' : {
						required : 1
					},
					'post[post_content]' : {
						editorcontent : true
					}
				},
				//验证未通过提示消息
				messages : {
					'post[post_title]' : {
						required : '请输入标题'
					},
					'post[post_content]' : {
						editorcontent : '内容不能为空'
					}
				},
				//给未通过验证的元素加效果,闪烁等
				highlight : false,
				//是否在获取焦点时验证
				onfocusout : false,
				//验证通过，提交表单
				submitHandler : function(forms) {
					if (formloading)
						return;
					$(forms).ajaxSubmit({
						url : form.attr('action'), //按钮上是否自定义提交地址(多按钮情况)
						dataType : 'json',
						beforeSubmit : function(arr, $form, options) {
							formloading = true;
						},
						success : function(data, statusText, xhr, $form) {
							formloading = false;
							if (data.code == 0) {
								setCookie("refersh_time", 1);
								//添加成功
								Wind.use("artDialog", function() {
									art.dialog({
										id : "succeed",
										icon : "succeed",
										fixed : true,
										lock : true,
										background : "#CCCCCC",
										opacity : 0,
										content : data.msg,
										button : [ {
											name : '继续编辑？',
											callback : function() {
												reloadPage(window);
												return true;
											},
											focus : true
										}, {
											name : '返回列表页',
											callback : function() {
												location = "<%=request.getContextPath()%>/Menu/index";
												return true;
											}
										} ]
									});
								});
							} else {
								isalert(data.msg);
							}
						}
					});
				}
			});
		});
		////-------------------------
	});
</script>
</body>
</html>