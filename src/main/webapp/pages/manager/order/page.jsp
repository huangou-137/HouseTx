<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Order"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/10 10:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单管理 | 住房交易系统</title>
    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp"%>

    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            $("#li-orders").addClass("active");
        });
    </script>
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>
<c:set var="actionUrl" value="manager/orders"/>
<%-- 状态导航条 --%>
<div class="topnav">
    <a href="${actionUrl}" id="state_all">所有状态</a>
    <c:forEach items="${Order.genStateList()}" var="orderState">
        <a href="${actionUrl}?state=${orderState}" id="state_${orderState}">${Order.getStateSke(orderState)}</a>
    </c:forEach>
    <script>
        $(function () {
            <c:if test="${empty requestScope.state}">
            $("#state_all").addClass("active");
            </c:if>
            <c:if test="${not empty requestScope.state}">
            $("#state_${requestScope.state}").addClass("active");
            </c:if>
        });
    </script>
</div>

<div class="row">
    <div class="leftcolumn">
        &nbsp;
    </div>

    <div class="midcolumn">
        <div class="card">
            <h3><a href="pages/manager/order/add.jsp" target="_blank">添加新订单</a></h3>
            <h2>订单信息列表</h2>
            <table class="tb-list">
                <tr>
                    <th>订单编号</th>
                    <th>房屋编号</th>
                    <th>买方ID</th>
                    <th>卖方ID</th>
                    <th>状态</th>
                    <th>查看详情</th>
                </tr>
                <c:forEach items="${pageInfo.list}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.houseId}</td>
                        <td>${order.buyerId}</td>
                        <td>${order.sellerId}</td>
                        <td>${Order.getStateDesc(order.state)}</td>
                        <td><a href="manager/order/${order.id}" target="_blank">
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
            <h3>关于订单信息</h3>
            <p>&emsp;&emsp;本页面只显示订单的<span style="color: red">简略信息</span>，
                可以点击&nbsp;<img style="width: 20px;" src="static/img/btn/details.png"/>
                <span style="color: green">按钮</span>&nbsp;查看相对应订单的详情信息，
                便可进一步对订单实行删除、修改等操作。</p>
            <h3>关于订单确认</h3>
            <p>&emsp;&emsp;住房交易流程的最后一步便是管理员对订单进行确认，确认本次交易是否成功完成或失败。
                <i>只有状态为“<span style="color: orangered">接受</span>”的订单才能北确认</i>
            </p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>