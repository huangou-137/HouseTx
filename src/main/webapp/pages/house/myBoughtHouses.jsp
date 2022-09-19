<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Order"%>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/22 11:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的已购买房产 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            $("#li-myBoughtHouses").addClass("active");
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
        <h2 class="card-h2">房产信息列表</h2>

        <c:forEach items="${requestScope.list}" var="order">
            <div class="card">
                <fieldset><table>
                    <tr><th>编号：</th><td>${order.house.id}</td></tr>
                    <tr><th>类别：</th><td>${applicationScope.houseKindMap[order.house.kindId]}</td></tr>
                    <tr><th>户型：</th><td>${applicationScope.houseTypeMap[order.house.typeId]}</td></tr>
                    <tr><th>面积：</th><td>${order.house.size}(m<sup>2</sup>)</td></tr>
                    <tr><th>所属地区：</th><td>${applicationScope.areaMap[order.house.areaId]}</td></tr>
                    <tr><th>详细地址：</th><td>${order.house.address}</td></tr>
                    <tr><th>价格：</th><td>${order.house.price}(元)</td></tr>
                </table></fieldset>
                <fieldset>
                    <legend>相关订单信息</legend>
                    <table>
                        <tr><th>订单编号：</th><td>${order.id}</td></tr>
                        <tr><th>卖方ID：</th><td>${order.sellerId} &emsp;&emsp;
                            <a href="other/user/${order.sellerId}" target="_blank">查看卖方信息</a></td></tr>
                    </table>
                </fieldset>
                <div class="btn-center">
                    <a href="house/myBoughtHouse/${order.house.id}" target="_blank">查看详情</a>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于本页列表信息</h3>
            <p>&emsp;&emsp;本页面只显示房产及其相对应订单的<span style="color: orangered">简略信息</span>，
                可以点击&nbsp;<span style="color: green">查看详情</span>&nbsp;查看该房产及其相对应订单全部详情信息。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>