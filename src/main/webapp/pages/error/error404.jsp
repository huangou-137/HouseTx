<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/21 21:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>未找到页面 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <style type="text/css">
        body {
            background-image: url('static/img/other/404error.jpg');
            background-position: center;
            background-repeat: no-repeat;
        }
        div {
            text-align: center;
        }
        h3 {
            color: purple;
        }
    </style>
</head>
<body>
<div>
    <%@ include file="/pages/common/menu.jsp" %>
</div>

<div>
    <h3>很抱歉。您访问的页面不存在，或已经被删除！！！ </h3><br>
    <h2><a href="index.jsp">返回首页</a></h2>
</div>

</body>
</html>
