<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/8/25 22:46
  部分房产信息视图：【类别、户型、面积、所属地区、详细地址、价格、上架时间、状态、描述】
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<tr><th>类别:</th><td>${applicationScope.houseKindMap[house.kindId]}</td></tr>
<tr><th>户型:</th><td>${applicationScope.houseTypeMap[house.typeId]}</td></tr>
<tr><th>面积:</th><td>${house.size}(m<sup>2</sup>)</td></tr>
<tr><th>所属地区:</th><td>${applicationScope.areaMap[house.areaId]}</td></tr>
<tr><th>详细地址:</th><td>${house.address}</td></tr>
<tr><th>价格:</th><td>${house.price}(元)</td></tr>
<tr><th>上架时间:</th><td>${house.time}</td></tr>
<tr><th>状态:</th><td>${House.getStateSke(house.state)}</td></tr>
<tr><th>描述:</th>
    <td><textarea class="descTextArea" rows="4" readonly>${house.desc}</textarea></td>
</tr>

<script type="text/javascript">
    $(function () {
        setTextareasWidth("infoDiv", 0.75, 0.75);
    });
</script>