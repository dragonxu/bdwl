<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/22 0022
  Time: 下午 1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${pageResult.totalPageCount > 1}">
  <div class="pagination" style="text-align: center">
    <div>
      <a class="prev" href="${pageResult.firstPageUrl}">共${pageResult.totalRecord}条</a>
      <a class="prev" href="${pageResult.firstPageUrl}">首页</a>
      <a class="prev" href="${pageResult.beforePageUrl}">上一页</a>
      <c:forEach items="${pageResult.pageNumList}" var="page">
        <c:if test="${pageResult.currentPage == page}">
          <span class="current">${page}</span>
        </c:if>
        <c:if test="${pageResult.currentPage != page}">
          <a href="${pageResult.searchPageUrl}&p=${page}">${page}</a>
        </c:if>
      </c:forEach>
      <a class="num" href="${pageResult.nextPageUrl}">下一页</a>
      <a class="next" href="${pageResult.endPageUrl}">尾页</a>
    </div>
  </div>
</c:if>
