<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/17 12:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.doType}订单 | 住房交易系统</title>
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
            $("#btn-sub").click(function () {
                return confirm("确定${requestScope.doType}订单？");
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

            <form action="${requestScope.action}" method="post">
                <table>
                    <tr><th>相关ID：</th><td>${requestScope.id}</td></tr>
                    <%-- 静态包含 验证码 的tr输入部分 --%>
                    <%@include file="/pages/div/trInput/checkCode.jsp"%>

                    <tr>
                        <th><label class="imp">*</label><label for="txPass">交易密码：</label></th>
                        <td><input type="password" class="icon-base icon-txPass" required
                                   autocomplete="off" tabindex="1" id="txPass" name="txPass"
                                   placeholder="请输入交易密码" value=""/>
                            <span id="txPass_yn"></span>
                            <!--  使交易密码可见或隐藏（默认） -->
                            <button type="button" class="ovalArea btn-visible" id="visTxPass"></button>
                            <label for="visTxPass" id="visTxPassTag"> 显示</label><br/>
                            <span id="txPass_tip"></span>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" class="td-btn">
                            <input type="submit" id="btn-sub" class="btn-common" value="提交"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div class="rightcolumn">
        <div class="card">
            &emsp;
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>
