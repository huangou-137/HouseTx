<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/30 13:20
  首页，显示房屋简略信息（房子类别、户型、面积、所属地区、详细地址、预期价格）列表
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            $("#li-index").addClass("active");

            /**
             * 检查最小值或最大值价格搜索栏的值情况
             * @param {boolean} isMaxPrice 是否是最大值价格搜索栏
             */
            priceFun = function(isMaxPrice) {
                let priceElem = $("#minPrice");
                if (isMaxPrice === true) {
                    priceElem = $("#maxPrice");
                }
                let min = parseFloat(priceElem.attr("min"));
                let value = parseFloat(priceElem.val());
                if (isNaN(value) || value < min) {
                    priceElem.val(priceElem.attr("min"));
                } else {
                    let minPrice = parseFloat($("#minPrice").val());
                    let maxPrice = parseFloat($("#maxPrice").val());
                    if (isNaN(minPrice) || isNaN(maxPrice)) {
                        return;
                    } else if (minPrice > maxPrice) {
                        // 交换最大值与最小值
                        $("#minPrice").val(maxPrice);
                        $("#maxPrice").val(minPrice);
                    }
                }
            };

            // 价格查询最小值输入栏失去焦点事件
            $("#minPrice").blur(function () {
                priceFun(false);
            });

            // 价格查询最大值输入栏失去焦点事件
            $("#maxPrice").blur(function () {
                priceFun(true);
            });

            /**
             * 检查最小值或最大值面积搜索栏的值情况
             * @param {boolean} isMaxSize 是否是最大值面积搜索栏
             */
            sizeFun = function(isMaxSize) {
                let sizeElem = $("#minSize");
                if (isMaxSize === true) {
                    sizeElem = $("#maxSize");
                }
                // 限制面积值
                let value = parseInt(sizeElem.val());
                let min = parseInt(sizeElem.attr("min"));
                let max = parseInt(sizeElem.attr("max"));
                if (isNaN(value) || value < min) {
                    sizeElem.val(min);
                } else if (value > max) {
                    sizeElem.val(max);
                } else {
                    let minSize = parseInt($("#minSize").val());
                    let maxSize = parseInt($("#maxSize").val());
                    if (isNaN(minSize) || isNaN(maxSize)) {
                        return;
                    } else if (minSize > maxSize) {
                        // 交换最小值与最大值
                        $("#minSize").val(maxSize);
                        $("#maxSize").val(minSize);
                    }
                }
            };

            // 面积查询最小值输入栏失去焦点事件
            $("#minSize").blur(function () {
                sizeFun(false);
            });

            // 面积查询最大值输入栏失去焦点事件
            $("#maxSize").blur(function () {
                sizeFun(true);
            });

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
            <div class="card">
                <form action="client/houses" method="get">
                    价格：<input id="minPrice" type="number" min="0" step="0.01"
                              name="minPrice" value="${param.minPrice}"> 元&nbsp; -
                    <input id="maxPrice" type="number" min="0"
                           name="maxPrice" value="${param.maxPrice}"> 元&nbsp;
                    <input type="submit" class="btn-common" style="height: 30px;" value="查询" />
                </form>
                <form action="client/houses" method="get">
                    面积：<input id="minSize" type="number" min="1" max=" 32767"
                              name="minSize" value="${param.minSize}"> m<sup>2</sup> -
                    <input id="maxSize" type="number" min="1" max=" 32767"
                           name="maxSize" value="${param.maxSize}"> m<sup>2</sup>
                    <input type="submit" class="btn-common" style="height: 30px;" value="查询" />
                </form>

                <h2>房屋信息列表</h2>
                <table class="tb-list">
                    <tr>
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
                        <td>${applicationScope.houseKindMap[house.kindId]}</td>
                        <td>${applicationScope.houseTypeMap[house.typeId]}</td>
                        <td>${house.size}</td>
                        <td>${applicationScope.areaMap[house.areaId]}</td>
                        <td>${house.address}</td>
                        <td>${house.price}</td>
                        <td><a href="client/house/${house.id}" target="_blank">
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
                <h3>关于房屋信息</h3>
                <p>&emsp;&emsp;本页面只显示房屋的<span style="color: red">简略信息</span>，
                    可以点击&nbsp;<img style="width: 20px;" src="static/img/btn/details.png"/>
                    <span style="color: green">按钮</span>&nbsp;查看相对应房屋的详情信息。</p>
            </div>
        </div>
    </div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>