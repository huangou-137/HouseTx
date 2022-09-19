<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/21 21:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>服务器错误 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <style type="text/css">
        body {
            background-image: url('static/img/other/500error.jpg');
            background-position: center;
            background-repeat: no-repeat;
        }
        div {
            text-align: center;
        }
        h3 {
            color: green;
        }
    </style>
</head>
<body>
<div>
    <%@ include file="/pages/common/menu.jsp" %>
</div>

<div>
    <h3>很抱歉，您访问的后台程序出现了错误，程序猿小哥，正在努力的为您抢修！！！  </h3><br>
    <h2><a href="index.jsp">返回首页</a></h2>
</div>
</body>
</html>
