<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/2 12:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的登记房产 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            $("#li-myHouses").addClass("active");
        });
    </script>
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>
<%-- 导航条 --%>
<div class="topnav">
    <c:set var = "actionUrl" value = "house/myHouses" />
    <a href="${actionUrl}" id="state_all">所有状态</a>
    <c:forEach items="${House.genStateList()}" var="houseState">
        <a href="${actionUrl}?state=${houseState}" id="state_${houseState}">${House.getStateSke(houseState)}</a>
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
            <h3><a href="pages/house/enlist.jsp">登记新房产</a></h3>
        </div>

        <h2 class="card-h2">房屋信息列表</h2>

<c:forEach items="${requestScope.houseList}" var="house">
        <div class="card">
            <fieldset><table>
                <tr><th>编号：</th><td>${house.id}</td></tr>
                <tr><th>类别：</th><td>${applicationScope.houseKindMap[house.kindId]}</td></tr>
                <tr><th>户型：</th><td>${applicationScope.houseTypeMap[house.typeId]}</td></tr>
                <tr><th>面积：</th><td>${house.size}(m<sup>2</sup>)</td></tr>
                <tr><th>所属地区：</th><td>${applicationScope.areaMap[house.areaId]}</td></tr>
                <tr><th>详细地址：</th><td>${house.address}</td></tr>
                <tr><th>价格：</th><td>${house.price}(元)</td></tr>
                <tr style="text-align: center"><td colspan="2">
                    <a href="house/myHouse/${house.id}" target="_blank">查看房屋详情</a></td></tr>
            </table></fieldset>
        </div>
</c:forEach>

    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于状态选择</h3>
            <p>&emsp;&emsp;点击导航栏的状态链接，可以查询相对应状态下或所有状态下的房屋信息，默认显示所有状态下的房屋</p>
            <h3>关于房屋信息</h3>
            <p>&emsp;&emsp;本页面只显示房屋的<span style="color: red">简略信息</span>，
                可以点击&nbsp;<span style="color: green">查看房屋详情</span>&nbsp;查看该房屋全部信息。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>