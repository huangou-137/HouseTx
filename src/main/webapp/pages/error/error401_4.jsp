<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/24 16:52
  由Filter拦截返回未授权的401错误页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>未授权访问 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <style type="text/css">
        body {
            background-image: url('static/img/other/401_4error.jpg');
            background-position: center;
            background-repeat: no-repeat;
        }
        div {
            text-align: center;
        }
        h3 {
            color: red;
        }
    </style>
</head>
<body>
<div>
    <%@ include file="/pages/common/menu.jsp" %>
</div>

<div>
    <h3>很抱歉。您权限不足或未被授权，无法访问该页面！！！ </h3><br>
    <h2><a href="index.jsp">返回首页</a></h2>
</div>

</body>
</html>
