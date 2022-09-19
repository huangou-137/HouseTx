<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Order"%>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/22 11:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的已购买房产详情 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>

<div class="row">
    <div class="leftcolumn">
        &nbsp;
    </div>

    <div class="midcolumn">
        <div class="card">
            <%--静态包含警告信息栏 --%>
            <%@ include file="/pages/div/warnTips.jsp" %>
        </div>

        <div class="card" id="infoDiv">
            <h2>房屋详细信息</h2>
            <fieldset><table>
                <tr><th>编号:</th><td>${requestScope.house.id}</td></tr>
                <tr><th>登记者ID:</th><td>${house.uid}&emsp;&emsp;
                    <a href="other/user/${house.uid}" target="_blank">查看登记人信息</a></td></tr>
                <%--静态包含房产其它信息 --%>
                <%@include file="/pages/div/houseMoreView.jsp"%>
            </table></fieldset>
        </div>

        <div class="card">
            <h2 class="card-h2">相关订单详细信息</h2>
            <fieldset><table>
                <tr><th>订单编号：</th><td>${order.id}</td></tr>
                <%--静态包含订单其它信息 --%>
                <%@ include file="/pages/div/orderMoreView.jsp" %>
            </table></fieldset>
        </div>
    </div>

    <div class="rightcolumn">
        &emsp;
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>