<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/11 19:15
  页头内容：base标签、css样式、js和jQuery文件
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme()
            + "://"
            + request.getServerName()
            + ":"
            + request.getServerPort()
            + request.getContextPath()
            + "/";

    pageContext.setAttribute("basePath",basePath);
%>

<!--写base标签，永远固定相对路径跳转的结果-->
<base href="<%=basePath%>">
<link rel="icon" href="static/img/logo_ico.png" type="image/ico"/>
<link rel="stylesheet" type="text/css" href="static/css/common.css" charset="UTF-8">
<script type="text/javascript" src="static/script/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="static/script/common.js" charset="UTF-8"></script>
