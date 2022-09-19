<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="pers.ho.bean.User" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/1 16:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>其它用户信息 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript" src="static/script/input.js" charset="UTF-8"></script>
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
            <c:if test="${not empty requestScope.msg}">
                <div class="msg_cont">
                        <%-- 警告信息 --%>
                    <span id="warnTips" class="errorMsg">
                            ${requestScope.msg}
                    </span>
                </div>
            </c:if>
            <fieldset><table>
                <tr><th>用户ID：</th><td>${otherUser.uid}</td></tr>
                <tr><th>用户名：</th><td>${otherUser.username}</td></tr>
                <tr><th>手机号：</th><td>${otherUser.phone}</td></tr>
                <tr><th>电子邮箱：</th><td>${otherUser.email}</td></tr>
                <tr><th>简介：</th>
                    <td><textarea class="descTextArea" rows="3" readonly>${otherUser.desc}</textarea></td></tr>
            </table></fieldset>
        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>联系他/她</h3>
            <p>&emsp;&emsp;请根据手机号或电子邮箱联系该用户。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>