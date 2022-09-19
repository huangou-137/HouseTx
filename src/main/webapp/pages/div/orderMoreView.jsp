<%@ page import="pers.ho.bean.Order"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/22 19:13
  部分订单信息视图：【状态、买方发起时间、卖方接受时间、结束时间】
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<tr><th>状态：</th><td>${Order.getStateDesc(order.state)}</td></tr>
<tr><th>买方发起时间：</th><td>${order.launchTime}</td></tr>
<tr><th>卖方接受时间：</th><td>${order.acceptTime}</td></tr>
<tr><th>结束时间：</th><td>${order.endTime}</td></tr>