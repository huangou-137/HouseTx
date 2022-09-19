<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Order"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/21 12:47
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的订单 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <style>
        /*为选中的按钮设置CSS样式（使其变大显眼）*/
        input:checked {
            height: 25px;
            width: 25px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $("#li-myOrders").addClass("active");

            // 身份选择初始化
            <c:choose>
                <c:when test="${'buyer'.equals(requestScope.identity)}">
                    $("#pot-buyer").attr("checked","checked");
                </c:when>
                <c:when test="${'seller'.equals(requestScope.identity)}">
                    $("#pot-seller").attr("checked","checked");
                </c:when>
                <c:otherwise>
                    $("#pot-all").attr("checked","checked");
                </c:otherwise>
            </c:choose>

            // 身份选择提交事件
            $("#pot_btn").click(function () {
                return confirm("确定提交身份选择刷新本页面？");
            });
        });
    </script>
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>
<c:set var = "actionUrl" value = "order/myOrders" />
<%-- 状态导航条 --%>
<div class="topnav">
    <a href="${actionUrl}" id="state_all">所有状态</a>
    <c:forEach items="${Order.genStateList()}" var="orderState">
        <a href="${actionUrl}?state=${orderState}&identity=${requestScope.identity}"
           id="state_${orderState}">${Order.getStateSke(orderState)}</a>
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
            <form action="order/myOrders" method="post">
                <input type="hidden" name="state" value="${requestScope.state}">
                <div class="radio-center">
                    身份选择：
                    <input type="radio" name="identity" id="pot-all" value="all">
                    <label for="pot-all">买家/卖家</label>&emsp;&emsp;
                    <input type="radio" name="identity" id="pot-buyer" value="${Order.BUYER}">
                    <label for="pot-buyer">买家</label>&emsp;&emsp;
                    <input type="radio" name="identity" id="pot-seller" value="${Order.SELLER}">
                    <label for="pot-seller">卖家</label>&emsp;&emsp;
                    <input type="submit" id="pot_btn" class="btn-common" value="提交选择"/>
                </div>
            </form>
        </div>

        <h2 class="card-h2">订单信息列表</h2>
<c:forEach items="${requestScope.orderList}" var="order">
         <div class="card">
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
                         <a href="other/user/${order.buyerId}" target="_blank">查看买方信息</a>
                     </c:if>
                 </td></tr>
                 <tr><th>卖方ID：</th><td>${order.sellerId} &emsp;&emsp;
                     <c:if test="${sessionScope.loginUid.equals(order.sellerId)}">
                         （本登录用户）
                     </c:if>
                     <c:if test="${not sessionScope.loginUid.equals(order.sellerId)}">
                         <a href="other/user/${order.sellerId}" target="_blank">查看卖方信息</a>
                     </c:if>
                 </td></tr>
                 <tr><th>状态：</th><td>${Order.getStateDesc(order.state)}</td></tr>
                 <tr style="text-align: center"><td colspan="2">
                     <a href="order/myOrder/${order.id}" target="_blank">查看订单详情</a></td></tr>
             </table></fieldset>
         </div>
</c:forEach>

    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于状态选择</h3>
            <p>&emsp;&emsp;点击导航栏的状态链接，可以查询相对应状态下或所有状态下的订单，默认显示所有状态下的订单</p>
            <h3>关于身份选择</h3>
            <p>（1）买家/卖家，可以查询用户作为买家或者卖家时的订单，即所有订单。<br/>
                （2）买家，可以查询用户仅作为买家时的订单。<br/>
                （3）卖家，可以查询用户仅作为卖家时的订单。<br/>
                <span style="color: red">tips：</span>身份选择可以与导航栏的状态选择一起起作用。
            </p>
            <h3>关于订单信息</h3>
            <p>&emsp;&emsp;本页面只显示订单的<span style="color: red">简略信息</span>，
                可以点击&nbsp;<span style="color: green">查看订单详情</span>&nbsp;查看该订单全部信息。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>
