<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../jsp/header.jsp"></jsp:include>
<body>
<div class="wrap">
  <ul class="nav nav-tabs">
    <li class="active"><a href="<%=request.getContextPath()%>/Admin/password">修改信息</a></li>
  </ul>
  <form class="form-horizontal js-ajax-forms" method="post" action="<%=request.getContextPath()%>/Admin/postPassword">
    <fieldset>
      <div class="control-group">
        <label class="control-label" for="input-old-password">原始密码</label>
        <div class="controls">
          <input type="password" class="input-xlarge" required id="input-old-password" name="oldPassword">
        </div>
      </div>
      <div class="control-group">
        <label class="control-label" for="input-password">新密码</label>
        <div class="controls">
          <input type="password" class="input-xlarge" required id="input-password" name="password">
        </div>
      </div>
      <div class="control-group">
        <label class="control-label" for="input-repassword">重复新密码</label>
        <div class="controls">
          <input type="password" class="input-xlarge" required id="input-repassword" name="repassword">
        </div>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary  js-ajax-submit">保存</button>
      </div>
    </fieldset>
  </form>
</div>
<script src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/content_addtop.js"></script>
<script>
  /////---------------------
  Wind.use('validate', 'ajaxForm', 'artDialog', function() {
    //javascript
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
            cancelVal : "{:L('OK')}",
            cancel : function() {
              $(errorArr[0].element).focus();
            }
          });
        } catch (err) {
        }
      },
      //验证规则
      rules : {
        'options[site_name]' : {
          required : 1
        },
        'options[site_host]' : {
          required : 1
        },
        'options[site_root]' : {
          required : 1
        }
      },
      //验证未通过提示消息
      messages : {
        'options[site_name]' : {
          required : "{:L('WEBSITE_SITE_NAME_REQUIRED_MESSAGE')}"
        },
        'options[site_host]' : {
          required : "{:L('WEBSITE_SITE_HOST_REQUIRED_MESSAGE')}"
        }
      },
      //给未通过验证的元素加效果,闪烁等
      highlight : false,
      //是否在获取焦点时验证
      onfocusout : false,
      //验证通过，提交表单
      submitHandler : function(forms) {
        $(forms).ajaxSubmit({
          url : form.attr('action'), //按钮上是否自定义提交地址(多按钮情况)
          dataType : 'json',
          beforeSubmit : function(arr, $form, options) {

          },
          success : function(data, statusText, xhr, $form) {
            if (data.code == 0) {
              var message = data.msg;
              //添加成功
              Wind.use("artDialog", function() {
                art.dialog({
                  id : "succeed",
                  icon : "succeed",
                  fixed : true,
                  lock : true,
                  background : "#CCCCCC",
                  opacity : 0,
                  content : message,
                  button : [ {
                    name : "确定",
                    callback : function() {
                      reloadPage(window);
                      return true;
                    },
                    focus : true
                  }, {
                    name : "关闭",
                    callback : function() {
                      reloadPage(window);
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
</script>
</body>
</html>