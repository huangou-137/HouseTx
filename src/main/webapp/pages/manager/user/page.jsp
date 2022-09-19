<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.User"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/13 15:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            $("#li-users").addClass("active");
        });
    </script>
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>

<div class="row">
    <div class="leftcolumn">
        &nbsp;
    </div>

    <div class="midcolumn">
        <div class="card">
            <h3><a href="pages/manager/user/add.jsp" target="_blank">添加新用户</a></h3>
            <h2>用户信息列表</h2>
            <table class="tb-list">
                <tr>
                    <th>编号</th>
                    <th>用户名</th>
                    <th>登录密码</th>
                    <th>手机号</th>
                    <th>电子邮箱</th>
                    <th>交易密码</th>
                    <th>查看详情</th>
                </tr>
                <c:forEach items="${pageInfo.list}" var="user">
                    <tr>
                        <td>${user.uid}</td>
                        <td>${user.username}</td>
                        <td>${user.loginPass}</td>
                        <td>${user.phone}</td>
                        <td>${user.email}</td>
                        <td>${user.txPass}</td>
                        <td><a href="manager/user/${user.uid}" target="_blank">
                            <button class="btn-details"></button></a></td>
                    </tr>
                </c:forEach>
            </table>
            <br/>
            <%--静态包含分页条--%>
            <%@include file="/pages/common/page_nav.jsp"%>
        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于用户信息</h3>
            <p>&emsp;&emsp;本页面<span style="color: red">不显示</span>用户的<span style="color: red">简介信息</span>，
                可以点击&nbsp;<img style="width: 20px;" src="static/img/btn/details.png"/>
                <span style="color: green">按钮</span>&nbsp;查看相对应用户的详情信息，
                便可进一步对用户实行删除、修改等操作。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>