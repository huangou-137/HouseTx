<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/17 13:58
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单操作成功 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }
        h1 a {
            color:red;
        }
        #Time {
            color: blue;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            countDown(6,"Time");
        });
    </script>
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>

<h1>${requestScope.news}<br/>
    <span id="Time">6</span>秒后自动转到该
    <a href="order/myOrder/${requestScope.orderId}">订单详情页面&gt;&gt;&gt;</a><br/>
    也可以<a href="index.jsp">点击此处</a>前往首页</h1>

<%
    String url = request.getContextPath() + "/order/myOrder/" + request.getAttribute("orderId");
    //自动跳转语句
    response.setHeader("Refresh", "6;URL="+url);
%>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>