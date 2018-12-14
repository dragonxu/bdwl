<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- saved from url=(0048)http://tsg.com/index.php?g=Admin&m=Index&a=index -->
<html lang="zh_CN" style="overflow: hidden;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Set render engine for 360 browser -->
    <meta name="renderer" content="webkit">

    <title>后台管理中心</title>

    <meta name="description" content="This is page-header (.page-header &gt; h1)">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="<%=request.getContextPath()%>/css/theme.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/simplebootadmin.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!--[if IE 7]>
    <link rel="stylesheet" href="<%=request.getContextPath()%>//font-awesome/4.4.0/css/font-awesome-ie7.min.css">
    <![endif]-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/simplebootadminindex.min.css">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/simplebootadminindex-ie.css?" />
    <![endif]-->
    <style>
        .navbar .nav_shortcuts .btn{margin-top: 5px;}
        .macro-component-tabitem{width:101px;}

        /*-----------------导航hack--------------------*/
        .nav-list>li.open{position: relative;}
        .nav-list>li.open .back {display: none;}
        .nav-list>li.open .normal {display: inline-block !important;}
        .nav-list>li.open a {padding-left: 7px;}
        .nav-list>li .submenu>li>a {background: #fff;}
        .nav-list>li .submenu>li a>[class*="fa-"]:first-child{left:20px;}
        .nav-list>li ul.submenu ul.submenu>li a>[class*="fa-"]:first-child{left:30px;}
        /*----------------导航hack--------------------*/
    </style>

    <script>
        //全局变量
        var GV = {
            HOST:"tsg.com",
            DIMAUB: "",
            JS_ROOT: "/public/js/",
            TOKEN: ""
        };
    </script>





    <style>
        #think_page_trace_open{left: 0 !important;
            right: initial !important;}
    </style>
</head>

<body style="min-width:900px;" screen_capture_injected="true">
<div id="loading" style="display: none;"><i class="loadingicon"></i><span>正在加载...</span></div>
<div id="right_tools_wrapper">
    <!--<span id="right_tools_clearcache" title="清除缓存" onclick="javascript:openapp('/index.php?g=admin&m=setting&a=clearcache','right_tool_clearcache','清除缓存');"><i class="fa fa-trash-o right_tool_icon"></i></span>-->
    <span id="refresh_wrapper" title="刷新"><i class="fa fa-refresh right_tool_icon"></i></span>
</div>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="brand">
                <small>
                    <!--<img src="/admin/themes/simplebootx/Public/assets/images/logo-18.png">-->
                    后台管理中心				</small>
            </a>
            <div class="pull-left nav_shortcuts">

                <%--<a class="btn btn-small btn-warning" href="http://tsg.com/" title="网站首页" target="_blank">--%>
                    <%--<i class="fa fa-home"></i>--%>
                <%--</a>--%>

                <!---->
                <!--<a class="btn btn-small btn-success" href="javascript:openapp('/index.php?g=portal&m=AdminTerm&a=index','index_termlist','分类管理');" title="分类管理">-->
                <!--<i class="fa fa-th"></i>-->
                <!--</a>-->
                <!---->

                <!---->
                <!--<a class="btn btn-small btn-info" href="javascript:openapp('/index.php?g=portal&m=AdminPost&a=index','index_postlist','文章管理');" title="文章管理">-->
                <!--<i class="fa fa-pencil"></i>-->
                <!--</a>-->
                <!---->

                <%--<a class="btn btn-small btn-danger" href="javascript:openapp(&#39;/index.php?g=admin&amp;m=setting&amp;a=clearcache&#39;,&#39;index_clearcache&#39;,&#39;清除缓存&#39;);" title="清除缓存">--%>
                    <%--<i class="fa fa-trash-o"></i>--%>
                <%--</a>		--%>
            </div>
            <ul class="nav simplewind-nav pull-right">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="http://tsg.com/index.php?g=Admin&amp;m=Index&amp;a=index#" class="dropdown-toggle">
                        <!--							<img class="nav-user-photo" width="30" height="30" src="/admin/themes/simplebootx/Public/assets/images/logo-18.png" alt="admin">-->
							<span class="user-info">
								欢迎, ${admin.username}
                            </span>
                        <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
                        <!---->
                        <!--<li><a href="javascript:openapp('/index.php?g=Admin&m=setting&a=site','index_site','网站信息');"><i class="fa fa-cog"></i> 网站信息</a></li>-->
                        <!---->
                        <li>
                            <a href="javascript:openapp('<%=request.getContextPath()%>/Admin/password','password','修改密码');">
                                <i class="fa fa-user"></i> 修改信息
                            </a>
                        </li>
                        <li>
                            <a href="<%=request.getContextPath()%>/Login/loginOut">
                                <i class="fa fa-sign-out"></i> 退出登录
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="main-container container-fluid" style="background-color: #2c3e50">

    <div class="sidebar" id="sidebar" style="background-color: #2c3e50">
        <!-- <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        </div> -->
        <div id="nav_wraper" style="background-color: rgb(44, 62, 80); height: 856px; overflow: auto;">
            <style>
                .nav-list>li>a{
                    background-color: #2c3e50;
                    color: #fff;
                }
                .sidebar:before{
                    background-color: #2c3e50;
                }
            </style>
            <jsp:include page="../jsp/menu.jsp"></jsp:include>
        </div>

    </div>

    <div class="main-content">
        <div class="breadcrumbs" id="breadcrumbs">
            <a id="task-pre" class="task-changebt" style="display: none;">←</a>
            <div id="task-content" style="width: 118px;">
                <ul class="macro-component-tab" id="task-content-inner" style="width: 118px;">
                    <li class="macro-component-tabitem noclose" app-id="0" app-url="/index.php?g=Admin&amp;m=main&amp;a=index" app-name="首页">
                        <span class="macro-tabs-item-text">首页</span>
                    </li>
                </ul>
                <div style="clear:both;"></div>
            </div>
            <a id="task-next" class="task-changebt" style="display: none;">→</a>
        </div>

        <div class="page-content" id="content" style="height: 815px;">
            <iframe src="<%=request.getContextPath()%>/Main/main" style="width:100%;height: 100%;" frameborder="0" id="appiframe-0" class="appiframe"></iframe>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script>
    var ismenumin = $("#sidebar").hasClass("menu-min");
    $(".nav-list").on( "click",function(event) {
        var closest_a = $(event.target).closest("a");
        if (!closest_a || closest_a.length == 0) {
            return
        }
        if (!closest_a.hasClass("dropdown-toggle")) {
            if (ismenumin && "click" == "tap" && closest_a.get(0).parentNode.parentNode == this) {
                var closest_a_menu_text = closest_a.find(".menu-text").get(0);
                if (event.target != closest_a_menu_text && !$.contains(closest_a_menu_text, event.target)) {
                    return false
                }
            }
            return
        }
        var closest_a_next = closest_a.next().get(0);
        if (!$(closest_a_next).is(":visible")) {
            var closest_ul = $(closest_a_next.parentNode).closest("ul");
            if (ismenumin && closest_ul.hasClass("nav-list")) {
                return
            }
            closest_ul.find("> .open > .submenu").each(function() {
                if (this != closest_a_next && !$(this.parentNode).hasClass("active")) {
                    $(this).slideUp(150).parent().removeClass("open")
                }
            });
        }
        if (ismenumin && $(closest_a_next.parentNode.parentNode).hasClass("nav-list")) {
            return false;
        }
        $(closest_a_next).slideToggle(150).parent().toggleClass("open");
        return false;
    });

    $(function(){
        $('.nav-list li a').click(function(){
            $('.nav-list li a').each(function(){
                $(this).removeClass("navChecked");
            })
            $(this).addClass("navChecked");
        })
        $('.submenu li a').click(function(){
            $('.submenu li a').each(function(){
                $(this).removeClass("navChecked");
            })
            $(this).addClass("navChecked");
        })
    })
</script>
<script src="<%=request.getContextPath()%>/js/index.js"></script>

</body>
</html>