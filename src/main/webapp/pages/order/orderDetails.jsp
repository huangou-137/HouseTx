<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Order"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/12 12:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的订单详情 | 住房交易系统</title>
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
        <div class="card" id="infoDiv">
            <h2 class="card-h2">订单详细信息</h2>
            <%--静态包含警告信息栏 --%>
            <%@ include file="/pages/div/warnTips.jsp" %>

            <fieldset><table>
                <tr><th>订单编号：</th><td>${order.id}</td></tr>
                <tr><th>房屋编号：</th><td>${order.houseId} &emsp;&emsp;
                    <c:if test="${sessionScope.loginUid.equals(order.buyerId)}">
                        <a href="house/buy/${order.id}" target="_blank">查看房屋详情</a>
                    </c:if>
                    <c:if test="${sessionScope.loginUid.equals(order.sellerId)}">
                        <a href="house/myHouse/${order.houseId}" target="_blank">查看房屋详情</a>
                    </c:if>
                </td></tr>
                <tr><th>买方ID：</th><td>${order.buyerId} &emsp;&emsp;
                    <c:if test="${sessionScope.loginUid.equals(order.buyerId)}">
                        （本登录用户）
                    </c:if>
                    <c:if test="${not sessionScope.loginUid.equals(order.buyerId)}">
                        <a href="other/user/${order.buyerId}"
                           target="_blank">查看买方信息</a>
                    </c:if>
                </td></tr>
                <tr><th>卖方ID：</th><td>${order.sellerId} &emsp;&emsp;
                    <c:if test="${sessionScope.loginUid.equals(order.sellerId)}">
                        （本登录用户）
                    </c:if>
                    <c:if test="${not sessionScope.loginUid.equals(order.sellerId)}">
                        <a href="other/user/${order.sellerId}"
                           target="_blank">查看卖方信息</a>
                    </c:if>
                </td></tr>
                <%--静态包含订单其它信息 --%>
                <%@ include file="/pages/div/orderMoreView.jsp" %>
            </table></fieldset>
            <br/>

            <%-- 操作 --%>
            <div class="btn-center">
<c:if test="${canCancel}">
                <%-- 买方取消订单 --%>
                <a id="cancel_btn" href="order/toCancel/${order.id}">
                    <button class="btn-common-red">取消订单</button></a>
                &emsp;&emsp;&emsp;
                <script type="text/javascript">
                    $(function () {
                        $("#cancel_btn").click(function () {
                            return confirm('确认取消订单？');
                        });
                    });
                </script>
</c:if>

<c:if test="${canRefuse}">
                <%-- 卖方拒绝订单 --%>
                <a id="refuse_btn" href="order/toRefuse/${order.id}">
                    <button class="btn-common-red">拒绝订单</button></a>
                &emsp;&emsp;&emsp;
                <script type="text/javascript">
                    $(function () {
                        $("#refuse_btn").click(function () {
                            return confirm('确认拒绝订单？');
                        });
                    });
                </script>
</c:if>

<c:if test="${canAccept}">
                <%-- 卖方接受订单 --%>
                <a id="accept_btn" href="order/toAccept/${order.id}">
                    <button class="btn-common">接受订单</button></a>
                <script type="text/javascript">
                    $(function () {
                        $("#accept_btn").click(function () {
                            return confirm('确认接受订单？');
                        });
                    });
                </script>
</c:if>
            </div>

        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于订单结束类型</h3>
            <p>（1）被买方取消； <br/>（2）被卖家拒绝；<br/>
                （3）被管理员确认为交易成功；<br/>（4）被管理员确认为交易失败。
            </p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>
