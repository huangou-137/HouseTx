<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.User"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/06 11:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>用户详情 | 住房交易系统（管理员）</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            setTextareasWidth("infoDiv", 0.75, 0.75);
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
        <div class="card" id="infoDiv">
            <h2 class="iconH-base iconH2-base iconH-user">用户信息</h2>
            <%--静态包含警告信息栏 --%>
            <%@ include file="/pages/div/warnTips.jsp" %>

            <fieldset><table>
                <tr><th>用户ID：</th><td>${user.uid}</td></tr>
                <tr><th>用户名：</th><td>${user.username}</td></tr>
                <tr><th>登录密码：</th><td>${user.loginPass}</td></tr>
                <tr><th>交易密码：</th><td>${user.txPass}</td></tr>
                <tr><th>手机号：</th><td>${user.phone}</td></tr>
                <tr><th>电子邮箱：</th><td>${user.email}</td></tr>
                <tr><th>简介：</th>
                    <td><textarea class="descTextArea" rows="3" readonly>${user.desc}</textarea></td></tr>
            </table></fieldset>
            <br/>

            <%-- 管理用户操作 --%>
            <div class="btn-center">
                <form action="manager/user/${user.uid}" method="post">
                    <input type="hidden" name="_method" value="delete"/>
                    <input id="del_btn" type="submit" class="btn-common-red" value="删除用户"/>
                </form>
                &emsp;&emsp;&emsp;
                <a href="manager/user/toUpd/${user.uid}"><button class="btn-common">修改用户信息</button></a>
            </div>
            <script type="text/javascript">
                $(function () {
                    // 点击删除用户
                    $("#del_btn").click(function () {
                        return confirm('确认删除该用户？');
                    });
                });
            </script>

        </div>
    </div>

    <div class="rightcolumn">
        &emsp;
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>