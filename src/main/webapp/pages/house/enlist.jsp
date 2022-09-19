<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/13 12:32
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登记房产 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/form.css">
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript" src="static/script/input.js" charset="UTF-8"></script>
    <script type="text/javascript" src="static/script/house.js" charset="UTF-8"></script>
    <script type="text/javascript">
        $(function () {
            setTextareasWidth("infoDiv", 0.85, 0.75);

            //提交登记事件
            $("#sub_btn").click(function () {
                return houseSubmit("确认提交房屋信息进行登记？");
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
            <h2 class="iconH-base iconH2-base iconH-house">房产信息</h2>

            <div class="msg_cont">
                <%-- 警告信息 --%>
                <span style="">Tips:</span>
                <span id="warnTips" class="errorMsg">
                    ${ empty requestScope.msg ?
                            "<span style=\"color: #666\">请输入正确格式的房产信息↓↓↓</span>"
                            :requestScope.msg }
                </span>
            </div>

            <form action="house/enlist" method="post">
                <fieldset>
                    <table>
                        <%--静态包含房屋信息主体 --%>
                        <%@ include file="/pages/div/trInput/houseMain.jsp" %>
                        <%-- 静态包含 验证码 的tr输入部分 --%>
                        <%@include file="/pages/div/trInput/checkCode.jsp"%>
                    </table>
                </fieldset>
                <br/>
                <div class="btn-center">&emsp;
                    <input type="submit" id="sub_btn" class="btn-common" value="提交登记"/>
                </div>
            </form>
        </div>

    </div>
    <div class="rightcolumn">
        <div class="card">
            <h3>登记须知</h3>
            <p>&emsp;&emsp;登记成功的房产需管理员审核后才能被交易！</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>