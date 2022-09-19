<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Area"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/14 17:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
<script type="text/javascript">
    $(function () {
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

        // 先获取默认地区的省市县ID值
        let provinceId = "${empty provinceId? -1 : provinceId}";
        let cityId = "${empty cityId? -1 : cityId}";
        let countyId = "${empty countyId? -1 : countyId}";

        // 然后从area.json文件中加载areaJson（非异步）
        let areaJson;
        $.ajax({
            async: false, url: getProjUrl() + "/static/json/area.json", success: function (data) {
                $("#areaId").val(-1);
                let proSb = new StringBulider();
                let defaultProIdx = -1;
                $.each(data.provinces, function (i, proVal) {
                    proSb.append("<option value='" + i + "'>" + proVal.name + "</option>");
					// 省值符合
                    if (provinceId == proVal.id) {
						$("#areaId").val(provinceId);
						defaultProIdx = i;
                        let citySb = new StringBulider();
                        let defaultCityIdx = -1;
                        $.each(data.provinces[i].citys, function (j, cityVal) {
                            citySb.append("<option value='" + j + "'>" + cityVal.name + "</option>");
							// 市值符合
                            if (cityId == cityVal.id) {
								$("#areaId").val(cityId);
                                defaultCityIdx = j;
                                let countySb = new StringBulider();
                                let defaultCountyIdx = -1;
                                $.each(data.provinces[i].citys[j].countys, function (k, countyVal) {
                                    countySb.append("<option value='" + k + "'>" + countyVal.name + "</option>");
									//县值符合
                                    if (countyId == countyVal.id) {
										$("#areaId").val(countyId);
                                        defaultCountyIdx = k;
                                    }
                                });
                                // 加载相对应县值列表并赋初始值
                                $("#chooseCounty").after(countySb.toString());
                                $("#county").val(defaultCountyIdx);
                            }
                        });
                        // 加载相对应市值列表并赋初始值
                        $("#chooseCity").after(citySb.toString());
                        $("#city").val(defaultCityIdx);
                    }
                });
                // 加载所有省份并赋初始值
                $("#choosePro").after(proSb.toString());
                $("#province").val(defaultProIdx);
                areaJson = data;
            }
        });

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
            city.val(-1);
            county.val(-1);
            relateAreaId();
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
            let county = $("#county");
            // 清空区/县值列表
            if (county.children().length > 1) {
                county.empty();
            }
            if ($("#chooseCounty").length === 0) {
                county.append("<option id='chooseCounty' value='-1'>请选择区/县</option>");
            }
            county.val(-1);
            relateAreaId();
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
    <input type="hidden" name="areaId" id="areaId" />
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
</div>