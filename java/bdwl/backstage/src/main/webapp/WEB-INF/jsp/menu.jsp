<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ul id="bootNavList" class="nav nav-list">
    <style>
        .nav-list>li{
            border-bottom: 0;
            border-top:0;
            background-color: #fff;
        }
        .nav-list>li.open>a{
            background-color: #2c3e50;
            color: #fff;
            border-bottom: 0;
            border-top: 0;
            height: 50px;
            line-height: 50px;
        }
        .nav-list>li .submenu{
            border-top: 0;
            border-bottom: 1px solid #fff;
        }
        .nav-list>li .submenu>li>a{
            background-color: #2c3e50;
            color: #fff;
            border-bottom: 0;
            border-top: 0;
            height: 40px;
            line-height: 40px;;
        }
        .nav-list>li>a{
            height: 50px;
            line-height: 50px;;
        }
        .nav-list>li a>.arrow{
            top: 20px;;
        }
        .nav-list>li .submenu>li a>[class*="fa-"]:first-child{
            top: 21px;;
        }
        .nav-list>li>a:focus{
            color: #fff;
            background-color:#2c3e50;
        }
        .macro-component-tabitem.current{
            background-color: #fff;
            color: black;
        }
        .macro-tabs-item-text{
            color: #000;
        }
        .breadcrumbs{
            background-color: #ecf0f1;
        }
        .macro-component-tabclose-icon{
            color: #000;
        }
        .task-changebt{
            color: #000;
        }
        .nav-list .navChecked{
            color: #1dccaa;
            background: #fff;
        }
        .nav-list .submenu .navChecked{
            color: #1dccaa;
            background: #fff;
        }
    </style>

    <c:forEach items="${menus}" var="menu">
        <li>
            <c:if test="${fn:length(menu.menus) > 0}">
                <a href="#" class="dropdown-toggle">
                    <i class="fa fa-${menu.icon} normal"></i>
								<span class="menu-text normal">
                                        ${menu.name}
                                </span>
                    <b class="arrow fa fa-angle-right normal"></b>
                    <i class="fa fa-reply back"></i>
                    <span class="menu-text back">返回</span>
                </a>
                <ul class="submenu">
                    <c:forEach items="${menu.menus}" var="secondMenu">
                        <li>
                            <c:if test="${fn:length(secondMenu.menus) > 0}">
                                <a href="#" class="dropdown-toggle">
                                    <i class="fa fa-caret-right"></i>
								<span class="menu-text">
                                        ${secondMenu.name}
                                </span>
                                    <b class="arrow fa fa-angle-right"></b>
                                </a>
                                <ul class="submenu">
                                    <c:forEach items="${secondMenu.menus}" var="thirdMenu">
                                        <li>
                                            <c:if test="${fn:length(thirdMenu.menus) == 0}">
                                                <a href="javascript:openapp('<%=request.getContextPath()%>/${thirdMenu.model}/${thirdMenu.action}?${thirdMenu.params}','${thirdMenu.id}','${thirdMenu.name}',true);">
                                                    &nbsp;<i class="fa fa-angle-double-right"></i>
                                                    <span class="menu-text">
                                                            ${thirdMenu.name}
                                                    </span>
                                                </a>
                                            </c:if>
                                            <c:if test="${fn:length(thirdMenu.menus) > 0}">
                                                <a href="#" class="dropdown-toggle">
                                                    &nbsp;<i class="fa fa-caret-right"></i>
                                                        <span class="menu-text">
                                                                ${thirdMenu.name}
                                                        </span>
                                                    <b class="arrow fa fa-angle-right"></b>
                                                </a>
                                                <ul class="submenu" style="display: none;">

                                                </ul>
                                            </c:if>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            <c:if test="${fn:length(secondMenu.menus) == 0}">
                                <a href="javascript:openapp('<%=request.getContextPath()%>/${secondMenu.model}/${secondMenu.action}?${secondMenu.params}','${secondMenu.id}','${secondMenu.name}',true);">
                                    <i class="fa fa-caret-right"></i>
								<span class="menu-text">
                                        ${secondMenu.name}
                                </span>
                                </a>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${fn:length(menu.menus) == 0}">
                <a href="javascript:openapp('<%=request.getContextPath()%>/${menu.model}/${menu.action}?${menu.params}','${menu.id}','${menu.name}',true);">
                    <i class="fa fa-${menu.icon} normal"></i>
                                        <span class="menu-text">
                                                ${menu.name}
                                        </span>
                    <b class="arrow fa fa-angle-right normal"></b>
                    <i class="fa fa-reply back"></i>
                    <span class="menu-text back">返回</span>
                </a>
            </c:if>
        </li>
    </c:forEach>
</ul>