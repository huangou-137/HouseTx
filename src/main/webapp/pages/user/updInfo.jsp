<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/3/28 1:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改用户信息 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <link rel="stylesheet" type="text/css" href="static/css/form.css">
    <script type="text/javascript" src="static/script/input.js" charset="UTF-8"></script>
    <script type="text/javascript" src="static/script/user.js" charset="UTF-8"></script>
    <script type="text/javascript">
        $(function () {
            setTextareasWidth("infoDiv", 0.75, 0.65);

            // 给所有需验证的输入框（包括textarea）添加焦点失去事件
            $(".verifying").blur(function () {
                updUserInputBlur(this.id);      //调用user.js的函数
            });

            // 点击reset按钮恢复原值事件
            $("#reset_btn").click(function () {
                return resetClick("是否将所有用户信息恢复原值？");
            });

            //提交更新事件
            $("#sub_btn").click(function () {
                return checkAndConfirm("用户", "确认提交用户信息进行修改?");
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
            <h2 class="iconH-base iconH2-base iconH-user">用户信息</h2>

            <div class="msg_cont">
                <%-- 警告信息 --%>
                <span style="">Tips:</span>
                <span id="warnTips" class="errorMsg">
                    ${ empty requestScope.msg ?
                            "<span style=\"color: #666\">请输入正确格式的用户信息↓↓↓</span>"
                            :requestScope.msg }
                </span>
            </div>

            <form action="user/upd" method="post">
                <fieldset>
                    <legend>&emsp;重要信息&emsp;</legend>
                    <table>
                        <%-- 静态包含 用户名 的tr输入部分 --%>
                        <%@include file="/pages/div/trInput/username.jsp"%>

                        <tr>
                            <th><label class="imp">*</label><label for="loginPass">登录密码：</label></th>
                            <td><input type="password" class="verifying icon-base icon-loginPass"
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
                            <td><input type="password" class="verifying icon-base icon-loginPass"
                                       autocomplete="off" tabindex="1" id="reLoginPass"
                                       placeholder="请再次输入登录密码……" value=""/>
                                <span id="reLoginPass_yn"></span>
                                <!--  使确认登录密码可见或隐藏（默认） -->
                                <button type="button" class="ovalArea btn-visible" id="visReLoginPass"></button>
                                <label for="visReLoginPass" id="visReLoginPassTag"> 显示</label><br/>
                                <span id="reLoginPass_tip"></span>
                            </td>
                        </tr>

                        <tr>
                            <th><label class="imp">*</label><label for="txPass">交易密码：</label></th>
                            <td><input type="password" class="verifying icon-base icon-txPass"
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
                            <td><input type="password" class="verifying icon-base icon-txPass"
                                       autocomplete="off" tabindex="1" id="reTxPass"
                                       placeholder="请再次输入交易密码……" value=""/>
                                <span id="reTxPass_yn"></span>
                                <!--  使确认交易密码可见或隐藏（默认） -->
                                <button type="button" class="ovalArea btn-visible" id="visReTxPass"></button>
                                <label for="visReTxPass" id="visReTxPassTag"> 显示</label><br/>
                                <span id="reTxPass_tip"></span>
                            </td>
                        </tr>
                    </table></fieldset>

                <fieldset>
                    <legend>&emsp;一般信息&emsp;</legend>
                    <table>
                        <%-- 静态包含用户一般信息的tr输入部分 --%>
                        <%@include file="/pages/div/trInput/userNorm.jsp"%>
                    </table>
                </fieldset>
                <br/>
                <div class="btn-center">
                    <input type="reset" id="reset_btn" class="btn-common-blue" value="恢复原值"/>&emsp;&emsp;
                    <input type="submit" id="sub_btn" class="btn-common" value="提交更新"/>
                </div>
            </form>
        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>修改须知</h3>
            <p>（1）除了<span style="color: red">用户ID</span>之外，其它用户信息都可以修改。</p>
            <p>（2）<label class="imp">*重要</label>用户信息不能修改为<span style="color: red">空值</span>!</p>
            <p>（3）原密码不显示，且本页面的密码框不输入值或仅输入空格时默认不修改原密码。
                而<i><span style="color: orangered">修改登录密码</span>后需要重新登录!</i></p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>