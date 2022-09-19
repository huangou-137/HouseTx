<%@ page import="pers.ho.bean.Admin" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/3 13:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人资料——管理员</title>
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
            <h2 class="iconH-base iconH2-base iconH-user">管理员信息</h2>
            <fieldset><table>
                <tr><th>管理员ID：</th><td>${admin.id}</td></tr>
                <tr><th>管理员名：</th><td>${admin.adminName}</td></tr>
                <tr><th>固话号：</th><td>${admin.tel}</td></tr>
                <tr><th>电子邮箱：</th><td>${admin.email}</td></tr>
                <tr><th>简介：</th>
                    <td><textarea class="descTextArea" rows="3" readonly>${admin.desc}</textarea></td></tr>
                <tr><td colspan="2" style="text-align: center">
                    <a href="admin/toUpd">修改管理员资料</a></td></tr>
            </table></fieldset>
        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于管理员资料</h3>
            <p>&emsp;&emsp;该页面不显示密码信息。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>