<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/27 22:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人资料</title>
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
            <fieldset><table>
                <tr><th>用户ID：</th><td>${user.uid}</td></tr>
                <tr><th>用户名：</th><td>${user.username}</td></tr>
                <tr><th>手机号：</th><td>${user.phone}</td></tr>
                <tr><th>电子邮箱：</th><td>${user.email}</td></tr>
                <tr><th>简介：</th>
                    <td><textarea class="descTextArea" rows="3" readonly>${user.desc}</textarea></td></tr>
                <tr><td colspan="2" style="text-align: center">
                    <a href="user/toUpd">修改用户资料</a></td></tr>
            </table></fieldset>
        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于用户信息</h3>
            <p>&emsp;&emsp;本页面不展示登录密码及支付密码信息。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>