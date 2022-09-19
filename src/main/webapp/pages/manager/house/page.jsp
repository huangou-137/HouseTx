<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/10 20:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>房产管理 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            $("#li-houses").addClass("active");
        });
    </script>
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>
<c:set var="actionUrl" value="manager/houses"/>
<%-- 状态导航条 --%>
<div class="topnav">
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
            <h3><a href="pages/manager/house/add.jsp" target="_blank">添加新房产</a></h3>
            <h2>房产信息列表</h2>
            <table class="tb-list">
                <tr>
                    <th>编号</th>
                    <th>类别</th>
                    <th>户型</th>
                    <th>面积(m<sup>2</sup>)</th>
                    <th>所属地区</th>
                    <th>详细地址</th>
                    <th>价格(元)</th>
                    <th>查看详情</th>
                </tr>
                <c:forEach items="${pageInfo.list}" var="house">
                    <tr>
                        <td>${house.id}</td>
                        <td>${applicationScope.houseKindMap[house.kindId]}</td>
                        <td>${applicationScope.houseTypeMap[house.typeId]}</td>
                        <td>${house.size}</td>
                        <td>${applicationScope.areaMap[house.areaId]}</td>
                        <td>${house.address}</td>
                        <td>${house.price}</td>
                        <td><a href="manager/house/${house.id}" target="_blank">
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
            <h3>关于房产信息</h3>
            <p>&emsp;&emsp;本页面只显示房产的<span style="color: red">简略信息</span>，
                可以点击&nbsp;<img style="width: 20px;" src="static/img/btn/details.png"/>
                <span style="color: green">按钮</span>&nbsp;查看相对应房产的详情信息，
                便可进一步对房产实行删除、修改等操作。</p>
            <h3>关于房产审核</h3>
            <p>&emsp;&emsp;房产状态为“审核中”需要管理员审核通过后才能被其它用户所购买。</p>
        </div>
    </div>
</div>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp" %>
</body>
</html>