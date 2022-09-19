<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/10 21:12
  本页面不自动跳转
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录成功 | 住房交易系统</title>

    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp" %>

    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color: red;
        }
    </style>
</head>

<body>
<%--静态包含，登录成功之后的菜单 --%>
<%@ include file="menu.jsp" %>

<h1>管理员登录成功！欢迎回来!!!<br/>
    <a href="index.jsp">转到首页</a></h1>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp" %>
</body>
</html>