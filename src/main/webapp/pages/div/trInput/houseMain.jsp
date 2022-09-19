<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/1 14:39
  修改房屋信息的主体：【地区、详细地址、类别、户型、面积、价格、描述】
  需要的额外文件：form.css
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tr>
    <th><label class="imp">*</label>地区：</th>
    <td><%--  静态包含地区选择  --%>
        <%@ include file="/pages/div/area_select.jsp" %>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="address">详细地址：</label></th>
    <td><textarea class="icon-base icon-address descTextArea" required tabindex="1" id="address" name="address"
                  placeholder="请输入房屋的详细地址……">${house.address}</textarea>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label>类别：</th>
    <td><%--  房子类别选择  --%>
        <select name="kindId" id="kindId">
            <c:forEach items="${applicationScope.houseKindMap}" var="houseKind">
                <option value="${houseKind.key}">${houseKind.value}</option>
            </c:forEach>
        </select>
        <script>
            $("#kindId").val("${house.kindId}");
        </script>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label>户型：</th>
    <td><%--  房子户型选择  --%>
        <select name="typeId" id="typeId">
            <c:forEach items="${applicationScope.houseTypeMap}" var="houseType">
                <option value="${houseType.key}">${houseType.value}</option>
            </c:forEach>
        </select>
        <script>
            $("#typeId").val("${house.typeId}");
        </script>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="size">面积：</label></th>
    <td><input type="number" class="verifying icon-base icon-size" min="1" max="32767"
               autocomplete="off" tabindex="1" id="size" name="size" required
               placeholder="请输入面积……" value="${house.size}"/>m<sup>2</sup>
        <span id="size_yn"></span><br/>
        <span id="size_tip"></span>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="price">价格：</label></th>
    <td><input type="number" class="verifying icon-base icon-price" min="0" step="0.01"
               autocomplete="off" tabindex="1" id="price" name="price" required
               placeholder="请输入价格……" value="${house.price}"/>元
        <span id="price_yn"></span><br/>
        <span id="price_tip"></span>
    </td>
</tr>

<tr>
    <th><label for="desc">描述：</label></th>
    <td><textarea class="icon-base icon-desc descTextArea" tabindex="1" id="desc" name="desc"
                  placeholder="请输入对房屋的描述……">${house.desc}</textarea>
    </td>
</tr>
