<%--
  Created by IntelliJ IDEA.
  Admin: 黄欧
  Time: 2021/4/3 14:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改管理员信息 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <link rel="stylesheet" type="text/css" href="static/css/form.css">
    <script type="text/javascript" src="static/script/input.js" charset="UTF-8"></script>
    <script type="text/javascript" src="static/script/admin.js" charset="UTF-8"></script>
    <script type="text/javascript">
        $(function () {
            setTextareasWidth("infoDiv", 0.75, 0.65);

            //给所有需验证的输入框（包括textarea）的添加焦点获取事件
            $(".verifying").focus(function () {
                inputFocus(this.id);    //调用input.js的函数
            });

            //给所有需验证的输入框（包括textarea）的添加焦点失去事件
            $(".verifying").blur(function () {
                if ($("#" + this.id).attr("value") == $("#" + this.id).val()) {
                    $("#" + this.id + "_yn").text("");
                    $("#" + this.id + "_tip").text("");
                    if (this.id == "pass" && $("#pass").val() == $("#rePass").val()) {
                        $("#rePass_yn").text("");
                        $("#rePass_tip").text("");
                    }
                    return;
                }
                let bool = adminInputBlur(this.id);     //调用admin.js的函数
                if (this.id == "adminName" && bool){
                    let val = this.value;
                    let servletUrl = getProjUrl() + "/ajax/existsAdminName";
                    $.getJSON(servletUrl, "adminName=" + val, function (result) {
                        if (result) {
                            let existTip = "管理员名已存在或不可用！";
                            alert(existTip);
                            $("#warnTips").text(existTip);
                            show_yn("adminName_yn",false);
                            $("#adminName_tip").text(existTip);
                        } else {
                            //除管理员名可用外，还要符合正则判断
                            $("#warnTips").text("管理员名可用！");
                        }
                    });
                }
            });

            //点击可见/隐藏密码按钮时
            $("#visPass").click(function () {
                visInput("visPass","pass","visPassTag");
            });
            //点击可见/隐藏确认密码按钮时
            $("#visRePass").click(function () {
                visInput("visRePass","rePass","visRePassTag");
            });

            //点击reset按钮恢复原值事件
            $("#reset_btn").click(function () {
                return resetClick("是否将所有管理员信息恢复原值？");
            });

            //提交更新事件
            $("#sub_btn").click(function () {
                return checkAndConfirm("管理员", "确认提交管理员信息进行更新？");
            });
        });
    </script>
</head>

<body>
<%@ include file="/pages/admin/menu.jsp" %>

<div class="row">
    <div class="leftcolumn">
        &nbsp;
    </div>
    <div class="midcolumn">
        <div class="card" id="infoDiv">
            <h2 class="iconH-base iconH2-base iconH-admin">管理员信息</h2>

            <div class="msg_cont">
                <%-- 警告信息 --%>
                <span style="">Tips:</span>
                <span id="warnTips" class="errorMsg">
                    ${ empty requestScope.msg ?
                            "<span style=\"color: #666\">请输入正确格式的管理员信息↓↓↓</span>"
                            :requestScope.msg }
                </span>
            </div>

            <form action="admin/upd" method="post">
                <fieldset><table>
                    <tr><th>管理员ID：</th><td>${admin.id}</td></tr>
                    <tr>
                        <th><label class="imp">*</label><label for="adminName">管理员名：</label></th>
                        <td><input type="text" class="verifying icon-base icon-admin" required
                                   autocomplete="off" tabindex="1" id="adminName" name="adminName"
                                   placeholder="6-20位汉字字母数字或_混合" value="${admin.adminName}"/>
                            <span id="adminName_yn"></span><br/>
                            <span id="adminName_tip">${errorMap.adminName}</span></td>
                    </tr>

                    <tr>
                        <th><label for="pass">密码：</label></th>
                        <td><input type="password" class="verifying icon-base icon-pass"
                                   autocomplete="off" tabindex="1" id="pass" name="pass"
                                   placeholder="6-20位字母、数字或_混合" value=""/>
                            <span id="pass_yn"></span>
                            <!--  使密码可见或隐藏（默认） -->
                            <button type="button" class="ovalArea btn-visible" id="visPass"></button>
                            <label for="visPass" id="visPassTag"> 显示</label><br/>
                            <span id="pass_tip">${errorMap.pass}</span></td>
                    </tr>

                    <tr>
                        <th><label for="rePass">确认密码：</label></th>
                        <td><input type="password" class="verifying icon-base icon-pass"
                                   autocomplete="off" tabindex="1" id="rePass"
                                   placeholder="请再次输入密码……" value=""/>
                            <span id="rePass_yn"></span>
                            <!--  使确认密码可见或隐藏（默认） -->
                            <button type="button" class="ovalArea btn-visible" id="visRePass"></button>
                            <label for="visRePass" id="visRePassTag"> 显示</label><br/>
                            <span id="rePass_tip"></span></td>
                    </tr>

                    <tr>
                        <th><label class="imp">*</label><label for="tel">固话号：</label></th>
                        <td><input type="text" class="verifying icon-base icon-tel" required
                                   autocomplete="off" tabindex="1" id="tel" name="tel"
                                   placeholder="请输入固话号……" value="${admin.tel}"/>
                            <span id="tel_yn"></span><br/>
                            <span id="tel_tip">${errorMap.tel}</span></td>
                    </tr>

                    <tr>
                        <th><label for="email">电子邮箱：</label></th>
                        <td><input type="email" class="verifying icon-base icon-email"
                                   autocomplete="off" tabindex="1" id="email" name="email"
                                   placeholder="请输入电子邮箱……" value="${admin.email}"/>
                            <span id="email_yn"></span><br/>
                            <span id="email_tip">${errorMap.email}</span></td>
                    </tr>

                    <tr>
                        <th><label for="desc">简介：</label></th>
                        <td><textarea class="icon-base icon-desc" wrap="soft" required
                                      tabindex="1" id="desc" name="desc"
                                      placeholder="请输入管理员简介……">${admin.desc}</textarea></td>
                    </tr>
                </table></fieldset>
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
            <p>（1）除了<span style="color: red">管理员ID</span>之外，其它管理员信息都可以修改。</p>
            <p>（2）<label class="imp">*重要</label>管理员信息不能修改为<span style="color: purple">空值</span>!</p>
            <p>（3）原密码不显示，且本页面的密码框不输入值或仅输入空格时默认不修改原密码。
                而<i><span style="color: orangered">修改密码</span>后需要重新登录!</i></p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>