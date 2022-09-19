<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/18 19:37
  通用的操作成功页面，必需请求域对象有：
  doName（操作名称，如更新房产） ，countDownTime（倒计时/s），
  href（自动跳转的地址），hrefDesc（地址描述）
  可选请求域对象有：
  pojoId（操作实体的ID），news（操作成功其它提示信息）
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>操作成功 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp"%>
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }
        h1 a {
            color:red;
        }
        #pojoId {
            color: darkorange;
        }
        #Time {
            color: blue;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            countDown("${countDownTime}","Time");
        });
    </script>
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>

<h1>${doName}
    ${empty pojoId?"":"（编号："}
    <span id="pojoId">${pojoId}</span>
    ${empty pojoId?"":"）"}
    成功！
    ${news}<br/>
    <span id="Time">${countDownTime}</span>秒后自动转到
    <a href="${href}">${hrefDesc}&gt;&gt;&gt;</a><br/>
    也可以<a href="index.jsp">点击此处</a>前往首页</h1>

<%
    String url = request.getContextPath() + "/" + request.getAttribute("href");
    //自动跳转语句
    response.setHeader("Refresh", request.getAttribute("countDownTime") + ";URL="+url);
%>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>