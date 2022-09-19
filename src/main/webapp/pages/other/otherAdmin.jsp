<%@ page import="pers.ho.bean.Admin" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/3 16:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员信息 | 住房交易系统</title>
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
            <%@include file="/pages/div/warnTips.jsp"%>

            <fieldset><table>
                <tr><th>管理员ID：</th><td>${otherAdmin.id}</td></tr>
                <tr><th>管理员名：</th><td>${otherAdmin.adminName}</td></tr>
                <tr><th>联系固话号：</th><td>${otherAdmin.tel}</td></tr>
                <tr><th>电子邮箱：</th><td>${otherAdmin.email}</td></tr>
                <tr><th>简介：</th>
                    <td><textarea class="descTextArea" rows="3" readonly>${otherAdmin.desc}</textarea></td></tr>
            </table></fieldset>
        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>联系管理员</h3>
            <p>&emsp;&emsp;请根据固话号或电子邮箱进行联系。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>