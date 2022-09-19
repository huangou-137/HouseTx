<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/16 21:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>房产详情 | 住房交易系统（管理员）</title>
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
            <h2>房产详细信息</h2>
            <%--静态包含警告信息栏 --%>
            <%@ include file="/pages/div/warnTips.jsp" %>

            <fieldset><table>
                <tr><th>编号:</th><td>${house.id}</td></tr>
                <tr><th>登记者ID:</th><td>${house.uid}&emsp;&emsp;
                    <a href="other/user/${house.uid}" target="_blank">查看登记人信息</a></td></tr>
                <%--静态包含房产其它信息 --%>
                <%@include file="/pages/div/houseMoreView.jsp"%>
            </table></fieldset>

            <c:if test="${canAudit}">
                <br/>
                <%-- 审核房产 --%>
                <form action="manager/house/audit/${house.id}" method="post">
                    <fieldset>
                        <legend>房产审核</legend>
                        <div class="radio-center">
                            <input type="radio" name="adopt" id="adopt-f" value="false">
                            <label for="adopt-f" style="color: red">失败</label>&emsp;&emsp;
                            <input type="radio" name="adopt" id="adopt-t" value="true">
                            <label for="adopt-t" style="color: green">通过</label>&emsp;&emsp;
                            <input type="submit" id="audit_btn" class="btn-common" value="提交选择"/>
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
                        $("#adopt-f").click(function () {
                            $("#audit_btn").removeClass("btn-common").addClass("btn-common-red");
                            $('#audit_btn').removeAttr("disabled");
                        });

                        $("#adopt-t").click(function () {
                            $("#audit_btn").removeClass("btn-common-red").addClass("btn-common");
                            $('#audit_btn').removeAttr("disabled");
                        });

                        $("#audit_btn").click(function () {
                            return confirm('确定提交房产审核选项？');
                        });
                    });
                </script>
            </c:if>

            <%-- 通用管理房产操作 --%>
            <%@include file="do.jsp"%>

        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于房屋状态</h3>
            <p>（1）审核中——登记房产后的默认状态<br/>
                （2）审核失败——没有通过审核<br/>
                （3）待售中——审核通过<br/>
                （4）交易中——房主接受了买方的交易请求，商谈中<br/>
                （5）已出售——房子已被售出<br/>
                <b>tip:</b>审核失败时可以通过修改房屋信息再次提交审核；已出售的房子用户不能修改相关信息!
            </p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>