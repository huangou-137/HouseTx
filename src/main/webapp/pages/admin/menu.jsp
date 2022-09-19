<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/3 0:35
  管理员登录之后的菜单
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<span>欢迎管理员
    <span style="color: red;font-size: 25px;margin: 10px;">
        ${sessionScope.loginAdminName}
    </span>
        (id:${sessionScope.loginAdminId})光临住房交易系统</span>
<br/><br/>
<ul class="ul_H">
    <li><a href="index.jsp" id="li-index">首页</a></li>
    <li><a href="manager/users" id="li-users">用户管理</a></li>
    <li class="dropdown">
        <a class="dropdown-base" href="manager/houses" id="li-houses">房产管理</a>
        <div class="dropdown-content">
            <a href="manager/houses/auditing">审核房产</a>
        </div>
    </li>
    <li class="dropdown">
        <a class="dropdown-base" href="manager/orders" id="li-orders">订单管理</a>
        <div class="dropdown-content">
            <a href="manager/orders/ensuring">确认订单</a>
        </div>
    </li>
    <li class="dropdown">
        <a class="dropdown-base" href="">其它管理</a>
        <div class="dropdown-content">
            <a href="manager/beforeGenJson">地区管理</a>
        </div>
    </li>
    <li style="float:right"><a href="logout">退出登录</a></li>
    <li style="float:right"><a href="admin/myInfo">个人信息</a></li>
</ul>