<%@ page import="pers.ho.bean.Order"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/5/12 21:01
  通用的订单信息 选择或输入 主体，即添加订单和修改订单通用部分，包含：
  房屋ID，买卖方ID，买方发起时间，卖方接受时间，订单结束时间，订单状态（需要用到JSTL的c:foreach）
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tr>
    <th><label class="imp">*</label><label for="houseId">房屋编号：</label></th>
    <td><input type="number" autocomplete="off" tabindex="1" id="houseId" name="houseId" required
               placeholder="请输入房屋编号……" value="${order.houseId}"/>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="sellerId">卖方ID：</label></th>
    <td><input type="number" autocomplete="off" tabindex="1" id="sellerId" name="sellerId" required
               placeholder="请输入卖方编号……" value="${order.sellerId}"/>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="buyerId">买方ID：</label></th>
    <td><input type="number" autocomplete="off" tabindex="1" id="buyerId" name="buyerId" required
               placeholder="请输入买方编号……" value="${order.buyerId}"/>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="launchTime">买方发起时间：</label></th>
    <td><input type="datetime-local" autocomplete="off" tabindex="1" required id="launchTime"
               name="launchTime" value="${order.launchTime.toLocalDateTime()}"/>
    </td>
</tr>

<tr>
    <th><label for="acceptTime">卖方接受时间：</label></th>
    <td><input type="datetime-local" autocomplete="off" tabindex="1" id="acceptTime"
               name="acceptTime" value="${order.acceptTime.toLocalDateTime()}"/>
    </td>
</tr>

<tr>
    <th><label for="endTime">订单结束时间：</label></th>
    <td><input type="datetime-local" autocomplete="off" tabindex="1" id="endTime"
               name="endTime" value="${order.endTime.toLocalDateTime()}"/>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label>状态：</th>
    <td><%--  订单状态选择  --%>
        <select name="state" id="state">
            <c:forEach items="${Order.genStateList()}" var="stateValue">
                <option value="${stateValue}">${Order.getStateDesc(stateValue)}</option>
            </c:forEach>
        </select>
        <script>
            $("#state").val("${order.state}");
        </script>
    </td>
</tr>