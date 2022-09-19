<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Area"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Date: 2021/4/14
  Time: 17:33
  To change this template use File | Settings | File Templates.
  从应用作用域中动态读取省市区信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">

    $(function () {
        let areaJson = {};
        areaJson.provinces = [];
        let provinceIdx;        //省级下标
        let cityIdx;            //市级下标
        let countyIdx;          //县/区级下标
        <c:forEach items="${applicationScope.areaMap}" var="area">
        <c:if test="${Area.PROVINCE_TYPE.equals(area.value.type)}">
        <%-- 省级 --%>
        provinceIdx = ${applicationScope.provinceMap[area.key]};
        areaJson.provinces[provinceIdx] = {};
        areaJson.provinces[provinceIdx].name = "${area.value.name}";
        areaJson.provinces[provinceIdx].id = ${area.value.id};
        areaJson.provinces[provinceIdx].citys = [];
        </c:if>
        <c:if test="${Area.CITY_TYPE.equals(area.value.type)}">
        <%-- 地级、市 --%>
        provinceIdx = ${applicationScope.provinceMap[area.value.pid]};
        cityIdx = ${applicationScope.cityMap[area.key]};
        areaJson.provinces[provinceIdx].citys[cityIdx] = {};
        areaJson.provinces[provinceIdx].citys[cityIdx].name = "${area.value.name}";
        areaJson.provinces[provinceIdx].citys[cityIdx].id = ${area.value.id};
        areaJson.provinces[provinceIdx].citys[cityIdx].countys = [];
        </c:if>
        <c:if test="${Area.COUNTY_TYPE.equals(area.value.type)}">
        <%-- 县/区 --%>
        <%--  根据父（地）ID找出省区域ID  --%>
        provinceIdx = ${applicationScope.provinceMap[applicationScope.areaMap[area.value.pid].pid]};
        cityIdx = ${applicationScope.cityMap[area.value.pid]};
        countyIdx = areaJson.provinces[provinceIdx].citys[cityIdx].countys.length;
        areaJson.provinces[provinceIdx].citys[cityIdx].countys[countyIdx] = {};
        areaJson.provinces[provinceIdx].citys[cityIdx].countys[countyIdx].name = "${area.value.name}";
        areaJson.provinces[provinceIdx].citys[cityIdx].countys[countyIdx].id = ${area.value.id};
        </c:if>
        </c:forEach>

        // 伪StringBulider类
        function StringBulider(str) {
            let arr = [];
            str = str || "";
            let size = 0;   // 存放数组大小
            arr.push(str);
            // 追加字符串
            this.append = function(str1) {
                arr.push(str1);
                return this;
            };
            // 返回字符串
            this.toString = function() {
                return arr.join("");
            };
            // 清空
            this.clear = function(key) {
                size = 0;
                arr = [];
            };
            // 返回数组大小
            this.size = function() {
                return size;
            };
            // 返回数组
            this.toArray = function() {
                return buffer;
            };
            // 倒序返回字符串
            this.doReverse = function() {
                var str = buffer.join('');
                str = str.split('');
                return str.reverse().join('');
            };
        }

        // 先加载所有省份
        let sb = new StringBulider();
        $.each(areaJson.provinces, function (i, val) {
            sb.append("<option value='" + i + "'>" + val.name + "</option>");
        });
        $("#choosePro").after(sb.toString());

        // 省值、市值、区值变化后，改变区域编号
        relateAreaId = function() {
            let provinceVal = $("#province").val();
            let cityVal = $("#city").val();
            let countyVal = $("#county").val();
            if (provinceVal < 0) {
                $("#areaId").val(-1);
            } else if (cityVal < 0){
                $("#areaId").val(areaJson.provinces[provinceVal].id);
            } else if (countyVal < 0) {
                $("#areaId").val(areaJson.provinces[provinceVal].citys[cityVal].id);
            } else {
                $("#areaId").val(areaJson.provinces[provinceVal].citys[cityVal].countys[countyVal].id);
            }
        };

        // 省值变化时：市值和区值列表也变化
        relateCity = function() {
            relateAreaId();
            let city = $("#city");
            let county = $("#county");
            // 清空市值和区值旧列表
            if (city.children().length > 1) {
                city.empty();
            }
            if (county.children().length > 1) {
                county.empty();
            }
            if ($("#chooseCity").length === 0) {
                city.append("<option id='chooseCity' value='-1'>请选择城市</option>");
            }
            if ($("#chooseCounty").length === 0) {
                county.append("<option id='chooseCounty' value='-1'>请选择区/县</option>");
            }
            let provinceVal = $("#province").val();
            if (provinceVal < 0) {
                // 省值为默认值不作加载
                return;
            }
            let sb = new StringBulider();
            $.each(areaJson.provinces[provinceVal].citys, function (i, val) {
                sb.append("<option value='" + i + "'>" + val.name + "</option>");
            });
            // 加载相对应市值列表
            $("#chooseCity").after(sb.toString());
        };

        // 市值变化时：区/县值列表也发生变化
        relateCounty = function() {
            relateAreaId();
            let county = $("#county");
            // 清空区/县值列表
            if (county.children().length > 1) {
                county.empty();
            }
            if ($("#chooseCounty").length === 0) {
                county.append("<option id='chooseCounty' value='-1'>请选择区/县</option>");
            }
            let provinceVal = $("#province").val();
            let cityVal = $("#city").val();
            if (cityVal < 0) {
                // 市值为默认值不作加载
                return;
            }
            let sb = new StringBulider();
            $.each(areaJson.provinces[provinceVal].citys[cityVal].countys, function(i, val) {
                sb.append("<option value='" + i + "'>" + val.name + "</option>");
            });
            // 加载相对应的区/县值列表
            $("#chooseCounty").after(sb.toString());
        };

    });
</script>

<%-- 省市区选择 --%>
<input type="hidden" name="areaId" id="areaId" value="-1" />
<%-- id值为areaJson中对应数组的下标值 --%>
省：
<select id="province" name="province" onchange="relateCity();"> 　　　　　　　　
    <option id="choosePro" value="-1">请选择省份</option>
</select>
&emsp;市：
<select id="city" name="city" onchange="relateCounty();"> 　　　　　　　　
    <option id="chooseCity" value="-1">请选择城市</option>
</select>
&emsp;区/县：
<select id="county" name="county" onchange="relateAreaId();"> 　　　　　　　　
    <option id="chooseCounty" value="-1">请选择区/县</option>
</select>