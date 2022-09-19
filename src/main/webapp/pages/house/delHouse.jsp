<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/13 12:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>删除房产 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/form.css">
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            //点击可见/隐藏交易密码按钮时
            $("#visTxPass").click(function () {
                visInput("visTxPass","txPass","visTxPassTag");
            });

            // 提交事件
            $("#btn-subDel").click(function () {
                return confirm("确认删除该房产(id:${requestScope.houseId})？");
            });
        });
    </script>
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>

<div class="row">
    <div class="leftcolumn">
        &nbsp;
    </div>
    <div class="midcolumn">
        <div id="infoDiv" class="card">
            <%--静态包含警告信息栏 --%>
            <%@ include file="/pages/div/warnTips.jsp" %>

            <form action="loggedIn/houseServlet" method="post">
                <input type="hidden" name="action" value="delHouse" />
                <input type="hidden" name="id" value="${requestScope.houseId}" />
                <table>
                    <tr><th>房产ID：</th><td>${requestScope.houseId}</td></tr>
                    <%-- 静态包含 验证码 的tr输入部分 --%>
                    <%@include file="/pages/div/trInput/checkCode.jsp"%>

                    <tr>
                        <th><label class="imp">*</label><label for="txPass">交易密码：</label></th>
                        <td><input type="password" class="icon-base icon-txPass" required
                                   autocomplete="off" tabindex="1" id="txPass" name="txPass"
                                   placeholder="请输入交易密码" value=""/>
                            <!--  使交易密码可见或隐藏（默认） -->
                            <button type="button" class="ovalArea btn-visible" id="visTxPass"></button>
                            <label for="visTxPass" id="visTxPassTag"> 显示</label><br/>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" class="td-btn">
                            <input type="submit" id="btn-subDel" class="btn-common" value="提交"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rightcolumn">
        &emsp;
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>
