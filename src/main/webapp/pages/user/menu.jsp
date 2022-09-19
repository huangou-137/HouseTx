<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/05 19:10
  用户登录后的菜单
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<span>欢迎用户
        <span style="color: red;font-size: 25px;margin: 10px;">
            ${sessionScope.loginUsername}
        </span>
        (uid:${sessionScope.loginUid})光临住房交易系统</span>
<br /><br />
<ul class="ul_H">
    <li><a href="index.jsp" id="li-index">首页</a></li>
    <li class="dropdown">
        <a class="dropdown-base" id="li-myHouses"
           href="house/myHouses">我的登记房产</a>
        <div class="dropdown-content">
            <a href="pages/house/enlist.jsp">登记房产</a>
        </div>
    </li>
    <li><a href="order/myOrders" id="li-myOrders">订单</a></li>
    <li><a href="house/myBoughtHouses" id="li-myBoughtHouses">已购买房产</a></li>
    <li style="float:right"><a href="logout">登出</a></li>
    <li style="float:right"><a href="user/myInfo">个人信息</a></li>
</ul>