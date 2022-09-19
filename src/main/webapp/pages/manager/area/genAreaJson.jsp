<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Area"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/15 1:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>生成areaJson | 住房交易系统</title>

    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp" %>

    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            //设置文本区域大小
            $("#areaJsonStr").css("width",$("#infoDiv").width()*0.95);

            //当调整浏览器窗口大小时，发生 resize 事件
            $(window).resize(function(){
                //调整文本区域大小
                $("#areaJsonStr").css("width",$("#infoDiv").width()*0.95);
            });
        });
    </script>
</head>

<body>
<%--静态包含主菜单 --%>
<%@ include file="/pages/common/menu.jsp" %>

<div class="row">
    <div class="leftcolumn">
        &nbsp;
    </div>
    <div class="midcolumn">
        <script type="text/javascript">
            $(function () {
                $("#btn-gen").click(function () {
                    let areaJson = {};
                    areaJson.provinces = [];
                    let provinceIdx;        //省级下标
                    let cityIdx;            //市级下标
                    let countyIdx;          //县/区级下标
                    <c:forEach items="${requestScope.areaMap}" var="area">
                    <c:if test="${Area.PROVINCE_TYPE.equals(area.value.type)}">
                    <%-- 省级 --%>
                    provinceIdx = ${requestScope.provinceMap[area.key]};
                    areaJson.provinces[provinceIdx] = {};
                    areaJson.provinces[provinceIdx].name = "${area.value.name}";
                    areaJson.provinces[provinceIdx].id = ${area.value.id};
                    areaJson.provinces[provinceIdx].citys = [];
                    </c:if>
                    <c:if test="${Area.CITY_TYPE.equals(area.value.type)}">
                    <%-- 地级、市 --%>
                    provinceIdx = ${requestScope.provinceMap[area.value.pid]};
                    cityIdx = ${requestScope.cityMap[area.key]};
                    areaJson.provinces[provinceIdx].citys[cityIdx] = {};
                    areaJson.provinces[provinceIdx].citys[cityIdx].name = "${area.value.name}";
                    areaJson.provinces[provinceIdx].citys[cityIdx].id = ${area.value.id};
                    areaJson.provinces[provinceIdx].citys[cityIdx].countys = [];
                    </c:if>
                    <c:if test="${Area.COUNTY_TYPE.equals(area.value.type)}">
                    <%-- 县/区 --%>
                    <%--  根据父（地）ID找出省区域ID  --%>
                    provinceIdx = ${requestScope.provinceMap[requestScope.areaMap[area.value.pid].pid]};
                    cityIdx = ${requestScope.cityMap[area.value.pid]};
                    countyIdx = areaJson.provinces[provinceIdx].citys[cityIdx].countys.length;
                    areaJson.provinces[provinceIdx].citys[cityIdx].countys[countyIdx] = {};
                    areaJson.provinces[provinceIdx].citys[cityIdx].countys[countyIdx].name = "${area.value.name}";
                    areaJson.provinces[provinceIdx].citys[cityIdx].countys[countyIdx].id = ${area.value.id};
                    </c:if>
                    </c:forEach>
                    $("#areaJsonStr").text(JSON.stringify(areaJson));
                });

            });
        </script>
        <div class="card">
            <button class="btn-common" id="btn-gen">生成areaJson字符串</button>
        </div>
        <div class="card" id="infoDiv">
            <h2>areaJson字符串</h2>
            <textarea rows="30" wrap="soft" readonly tabindex="1" id="areaJsonStr" name="areaJsonStr"
                      placeholder="等待生成……"></textarea>
        </div>
    </div>
</div>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp" %>
</body>
</html>
