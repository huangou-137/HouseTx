<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.User"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/20 11:52
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>修改用户信息 | 住房交易系统（管理员）</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <link rel="stylesheet" type="text/css" href="static/css/form.css">
    <script type="text/javascript" src="static/script/input.js" charset="UTF-8"></script>
    <script type="text/javascript" src="static/script/user.js" charset="UTF-8"></script>
    <script type="text/javascript">
        $(function () {
            setTextareasWidth("infoDiv", 0.85, 0.75);

            //给所有需验证的输入框（包括textarea）添加焦点失去事件
            $(".verifying").blur(function () {
                updUserInputBlur(this.id);      //调用user.js的函数
            });

            // 点击reset按钮恢复原值事件
            $("#reset_btn").click(function () {
                return resetClick("是否将所有用户信息恢复原值？");
            });

            //提交修改事件
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

            <form action="manager/user/${user.uid}" method="post">
                <input type="hidden" name="_method" value="PUT"/>
                <fieldset><table>
                    <tr><th>用户ID：</th><td>${user.uid}</td></tr>
                    <%--静态包含通用用户信息输入主体 --%>
                    <%@ include file="/pages/manager/user/trInput.jsp" %>
                </table></fieldset>
                <br/>
                <div class="btn-center">&emsp;
                    <input type="reset" id="reset_btn" class="btn-common-blue" value="恢复原值"/>&emsp;&emsp;
                    <input type="submit" id="sub_btn" class="btn-common" value="提交修改"/>
                </div>
            </form>
        </div>
    </div>

    <div class="rightcolumn">
        &nbsp;
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>