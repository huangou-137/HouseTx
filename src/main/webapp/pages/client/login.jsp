<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/10 21:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>登录 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/login.css" charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="static/css/form.css" charset="UTF-8">
    <script type="text/javascript">
        $(function () {
            //点击可见/隐藏登录密码按钮时
            $("#visPass").click(function () {
                visInput("visPass","pass","visPassTag");
            });

            //点击切换按钮时
            $("#switch").click(function () {
                if ($("#trUser").hasClass("hidden")) {
                    //管理员→普通用户：隐藏管理员名输入框，显示用户名输入框和注册链接
                    $("#trUser").removeClass("hidden");
                    $("#trAdmin").addClass("hidden");
                    $("#gotoRegist").removeClass("hidden");
                    $("#switchTag").text("管理员");
                    $("#errorMsg").html("<span style=\"color: #666\">请输入用户名和密码↓↓↓</span>");
                    $("#loginType").val("user");
                    $("#pass").attr("name", "loginPass");
                } else {
                    //普通用户→管理员：显示管理员名输入框，隐藏用户名输入框和注册链接
                    $("#trAdmin").removeClass("hidden");
                    $("#trUser").addClass("hidden");
                    $("#gotoRegist").addClass("hidden");
                    $("#switchTag").text("普通用户");
                    $("#errorMsg").html("<span style=\"color: #666\">请输入管理员名和密码↓↓↓</span>");
                    $("#loginType").val("admin");
                    $("#pass").attr("name", "pass");
                }
            });
        });
    </script>
</head>

<body>
<div class="form-panel" style="height:190px;width: 360px;">
    <form action="login" method="post">
        <%-- 隐藏项，表明登录类型是：用户？管理员？  --%>
        <input id="loginType" type="hidden" name="loginType" value="user" />
        <table>
            <legend class="legend_tab">欢迎登录</legend>
            <div class="msg_cont">
                <!-- 警告信息 -->
                <span id="errorMsg" class="errorMsg">
                    ${ empty requestScope.msg ? "<span style=\"color: #666\">请输入用户名和密码↓↓↓</span>"
                            :requestScope.msg }
                </span>
            </div>

            <tr id="trUser">
                <th><label>用户名：</label></th>
                <td><input type="text" class="icon-base icon-user" autocomplete="off" tabindex="1"
                           name="username" placeholder="请输入用户名……" value="${requestScope.username}"/></td>
            </tr>

            <tr id="trAdmin" class="hidden">
                <th><label>管理员：</label></th>
                <td><input type="text" class="icon-base icon-admin" autocomplete="off" tabindex="1"
                           name="adminName" placeholder="请输入管理员名……" value="${requestScope.adminName}"/>
                </td>
            </tr>

            <tr>
                <th><label>密码：</label></th>
                <td><input type="password" class="icon-base icon-loginPass" autocomplete="off" tabindex="1" required
                           id="pass" name="loginPass" placeholder="请输入登录密码……" value=""/></td>
                <td><!--  使登录密码可见或隐藏（默认）  -->
                    <button type="button" class="ovalArea btn-visible" id="visPass"></button>
                    <label for="visPass" id="visPassTag"> 显示</label>
                </td>
            </tr>

            <tr  style="text-align: center">
                <td colspan="3" class="td-btn">
                    切换<label id="switchTag">管理员</label>:
                    <button type="button" class="btn-switch" id="switch"></button>
                    &emsp;&emsp;
                    <input type="submit" class="btn-common" value="登录" />
                </td>
            </tr>
            <tr id="gotoRegist">
                <td colspan="3">如果没有账号，请<a href="pages/client/regist.jsp">点击此处注册&gt;&gt;&gt;</a></td>
            </tr>
        </table>
    </form>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>