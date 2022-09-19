<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/20 11:52
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>修改订单信息 | 住房交易系统（管理员）</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript">
        $(function () {
            //提交修改事件
            $("#sub_btn").click(function () {
                return confirm('确认提交订单信息进行修改？');
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
        <div class="card" id="infoDiv">
            <h2 class="iconH-base iconH2-base iconH-order">订单信息</h2>

            <div class="msg_cont">
                <%-- 警告信息 --%>
                <span style="">Tips:</span>
                <span id="warnTips" class="errorMsg">
                    ${ empty requestScope.msg ?
                            "<span style=\"color: #666\">请输入正确格式的订单信息↓↓↓</span>"
                            :requestScope.msg }
                </span>
            </div>

            <form action="manager/order/${order.id}" method="post">
                <input type="hidden" name="_method" value="PUT">
                <fieldset><table>
                    <tr><th>订单编号：</th><td>${order.id}</td></tr>
                    <%--静态包含通用订单信息输入主体 --%>
                    <%@ include file="/pages/manager/order/trInput.jsp" %>
                </table></fieldset>
                <br/>
                <div class="btn-center">
                    <input type="submit" id="sub_btn" class="btn-common" value="提交修改"/>
                </div>
            </form>
        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于订单结束类型</h3>
            <p>（1）被买方取消； <br/>（2）被卖家拒绝；<br/>
                （3）被管理员确认为交易成功；<br/>（4）被管理员确认为交易失败。
            </p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>