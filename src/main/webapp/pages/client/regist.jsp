<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/13 18:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户注册 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/login.css">
    <link rel="stylesheet" type="text/css" href="static/css/form.css">
    <script type="text/javascript" src="static/script/input.js" charset="UTF-8"></script>
    <script type="text/javascript" src="static/script/user.js" charset="UTF-8"></script>
    <script type="text/javascript">
        $(function () {
            //给所有需验证的输入框（包括textarea）的添加焦点失去事件
            $(".verifying").blur(function () {
                addUserInputBlur(this.id);      //调用user.js的函数
            });

            //提交注册事件
            $("#sub_btn").click(function () {
                return checkAndConfirm("用户", "确认提交用户信息进行注册？");
            });
        });
    </script>
</head>

<body>
<div class="form-panel" style="height:605px;width: 460px;overflow-y: auto;">
    <form action="regist" method="post">
        <table>
            <legend class="legend_tab">欢迎注册</legend>
            <div class="msg_cont">
                <%-- 警告信息 --%>
                <span id="warnTips" class="errorMsg">
                    ${ empty requestScope.msg ? "<span style=\"color: #666\">请输入正确格式的用户信息↓↓↓</span>"
                    :requestScope.msg }
                </span>
            </div>

            <%-- 静态包含 用户名 的tr输入部分 --%>
            <%@include file="/pages/div/trInput/username.jsp"%>

            <tr>
                <th><label class="imp">*</label><label for="loginPass">登录密码：</label></th>
                <td><input type="password" class="verifying icon-base icon-loginPass" required
                           autocomplete="off" tabindex="1" id="loginPass" name="loginPass"
                           placeholder="6-20位字母、数字或_混合" value=""/>
                    <span id="loginPass_yn"></span>
                    <!--  使登录密码可见或隐藏（默认） -->
                    <button type="button" class="ovalArea btn-visible" id="visLoginPass"></button>
                    <label for="visLoginPass" id="visLoginPassTag"> 显示</label><br/>
                    <span id="loginPass_tip">${errorMap.loginPass}</span>
                </td>
            </tr>

            <tr>
                <th><label class="imp">*</label><label for="reLoginPass">确认登录密码：</label></th>
                <td><input type="password" class="verifying icon-base icon-loginPass" required autocomplete="off"
                           tabindex="1" id="reLoginPass" placeholder="请再次输入登录密码……" value=""/>
                    <span id="reLoginPass_yn"></span>
                    <!--  使确认登录密码可见或隐藏（默认） -->
                    <button type="button" class="ovalArea btn-visible" id="visReLoginPass"></button>
                    <label for="visReLoginPass" id="visReLoginPassTag"> 显示</label><br/>
                    <span id="reLoginPass_tip"></span>
                </td>
            </tr>

            <tr>
                <th><label class="imp">*</label><label for="txPass">交易密码：</label></th>
                <td><input type="password" class="verifying icon-base icon-txPass" required
                           autocomplete="off" tabindex="1" id="txPass" name="txPass"
                           placeholder="6-10位字母、数字混合" value=""/>
                    <span id="txPass_yn"></span>
                    <!--  使交易密码可见或隐藏（默认） -->
                    <button type="button" class="ovalArea btn-visible" id="visTxPass"></button>
                    <label for="visTxPass" id="visTxPassTag"> 显示</label><br/>
                    <span id="txPass_tip">${errorMap.txPass}</span>
                </td>
            </tr>

            <tr>
                <th><label class="imp">*</label><label for="reTxPass">确认交易密码：</label></th>
                <td><input type="password" class="verifying icon-base icon-txPass" required autocomplete="off"
                           tabindex="1" id="reTxPass" placeholder="请再次输入交易密码……" value=""/>
                    <span id="reTxPass_yn"></span>
                    <!--  使确认交易密码可见或隐藏（默认） -->
                    <button type="button" class="ovalArea btn-visible" id="visReTxPass"></button>
                    <label for="visReTxPass" id="visReTxPassTag"> 显示</label><br/>
                    <span id="reTxPass_tip"></span>
                </td>
            </tr>

            <%-- 静态包含用户一般信息的tr输入部分 --%>
            <%@include file="/pages/div/trInput/userNorm.jsp"%>

            <%-- 静态包含 验证码 的tr输入部分 --%>
            <%@include file="/pages/div/trInput/checkCode.jsp"%>

            <tr>
                <td colspan="2" class="td-btn">
                    <input type="submit" id="sub_btn" class="btn-common" value="提交注册"/>
                </td>
            </tr>
            <tr style="text-align: center">
                <td colspan="2">已有账号，请<a href="pages/user/login.jsp">点击此处登录&gt;&gt;&gt;</a></td>
            </tr>
        </table>
    </form>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>