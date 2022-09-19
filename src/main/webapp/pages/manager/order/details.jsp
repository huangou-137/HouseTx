<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.Order"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/18 21:37
  需要加上 isErrorPage="true" ，让本页面作为错误处理页面，否则之前转换成DELETE或PUT的请求 转发回此页面时：
  HTTP Status 405 – 方法不允许————消息 JSP 只允许 GET、POST 或 HEAD。
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true"%>
<html>
<head>
    <title>订单详情 | 住房交易系统（管理员）</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>

<div class="row">
    <div class="leftcolumn">
        &nbsp;
    </div>

    <div class="midcolumn">
        <div class="card" id="infoDiv">
            <h2>订单详细信息</h2>
            <%--静态包含警告信息栏 --%>
            <%@ include file="/pages/div/warnTips.jsp" %>

            <fieldset><table>
                <tr><th>订单编号：</th><td>${order.id}</td></tr>
                <tr><th>房屋编号：</th><td>${order.houseId} &emsp;&emsp;
                    <a href="manager/house/${order.houseId}" target="_blank">查看房屋详情</a></td></tr>
                <tr><th>买方ID：</th><td>${order.buyerId} &emsp;&emsp;
                        <a href="other/user/${order.buyerId}" target="_blank">查看买方信息</a></td></tr>
                <tr><th>卖方ID：</th><td>${order.sellerId} &emsp;&emsp;
                        <a href="other/user/${order.sellerId}" target="_blank">查看卖方信息</a></td></tr>
                <%--静态包含订单其它信息 --%>
                <%@ include file="/pages/div/orderMoreView.jsp" %>
            </table></fieldset>

            <c:if test="${canEnsure}">
                <br/>
                <%-- 确认订单 --%>
                <form action="manager/order/ensure/${order.id}" method="post">
                    <fieldset>
                        <legend>订单确认</legend>
                        <div class="radio-center">
                            <input type="radio" name="finished" id="finished-f" value="false">
                            <label for="finished-f" style="color: red">交易失败</label>&emsp;&emsp;
                            <input type="radio" name="finished" id="finished-t" value="true">
                            <label for="finished-t" style="color: green">交易完成</label>&emsp;&emsp;
                            <input type="submit" id="ensure_btn" class="btn-common" value="提交选择"/>
                        </div>
                    </fieldset>
                </form>
                <style>
                    /*为选中的按钮设置CSS样式（使其变大显眼）*/
                    input:checked {
                        height: 25px;
                        width: 25px;
                    }
                </style>
                <script type="text/javascript">
                    $(function () {
                        $("#finished-f").click(function () {
                            $("#ensure_btn").removeClass("btn-common").addClass("btn-common-red");
                            $('#ensure_btn').removeAttr("disabled");
                        });

                        $("#finished-t").click(function () {
                            $("#ensure_btn").removeClass("btn-common-red").addClass("btn-common");
                            $('#ensure_btn').removeAttr("disabled");
                        });

                        $("#ensure_btn").click(function () {
                            return confirm('确定提交订单确认选项？');
                        });
                    });
                </script>
            </c:if>

            <%-- 管理订单操作 --%>
            <br/>
            <div class="btn-center">
                <form action="manager/order/${order.id}" method="post">
                    <input type="hidden" name="_method" value="delete"/>
                    <input id="del_btn" type="submit" class="btn-common-red" value="删除订单"/>
                </form>
                &emsp;&emsp;&emsp;
                <a href="manager/order/toUpd/${order.id}"><button class="btn-common">修改订单信息</button></a>
            </div>
            <script type="text/javascript">
                $(function () {
                    $("#del_btn").click(function () {
                        return confirm('确认删除该订单？');
                    });
                });
            </script>

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
