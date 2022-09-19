<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/12 19:56
  通用内容 + 菜单选择
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <img class="logo_img" alt="住房交易系统" src="static/img/logo.jpg">

    <c:if test="${empty sessionScope.loginType}">
        <%--静态包含未登录菜单 --%>
        <%@ include file="/pages/client/menu_unlogin.jsp" %>
    </c:if>

    <c:if test="${'user'.equals(sessionScope.loginType)}">
        <%--静态包含用户总菜单 --%>
        <%@ include file="/pages/user/menu.jsp" %>
    </c:if>

    <c:if test="${'admin'.equals(sessionScope.loginType)}">
        <%--静态包含管理员总菜单 --%>
        <%@ include file="/pages/admin/menu.jsp" %>
    </c:if>
</div>
