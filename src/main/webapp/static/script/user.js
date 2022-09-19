//与用户信息相关的js函数（如：注册、修改用户信息等）
//需与common.js、input.js一起使用
$(function () {

    // *******使密码隐藏或显示的按钮点击事件(需common.js)*******
    //（1.1）登录密码
    $("#visLoginPass").click(function () {
        visInput("visLoginPass","loginPass","visLoginPassTag");
    });
    //（1.2）确认登录密码
    $("#visReLoginPass").click(function () {
        visInput("visReLoginPass","reLoginPass","visReLoginPassTag");
    });
    //（2.1）交易密码
    $("#visTxPass").click(function () {
        visInput("visTxPass","txPass","visTxPassTag");
    });
    //（2.2）确认交易密码
    $("#visReTxPass").click(function () {
        visInput("visReTxPass","reTxPass","visReTxPassTag");
    });

    /**
     * 修改用户信息时的输入框焦点失去事件
     * @param {elementId} ID
     */
    updUserInputBlur = function(ID) {
        if ($("#" + ID).attr("value") == $("#" + ID).val()) {
            $("#" + ID + "_yn").text("");
            $("#" + ID + "_tip").text("");
            if (ID == "loginPass" && $("#loginPass").val() == $("#reLoginPass").val()) {
                $("#reLoginPass_yn").text("");
                $("#reLoginPass_tip").text("");
            }
            if (ID == "txPass" && $("#txPass").val() == $("#reTxPass").val()) {
                $("#reTxPass_yn").text("");
                $("#reTxPass_tip").text("");
            }
        } else {
            // 修改原值后与添加时的失去失去事件通用
            addUserInputBlur(ID);
        }
    };

    /**
     * 添加用户信息时的输入框焦点失去事件
     * @param {elementId} ID
     */
    addUserInputBlur = function(ID) {
        let bool = userInputBlur(ID);
        if (ID == "username" && bool){
            let val = $("#" + ID).val();
            let servletUrl = getProjUrl() + "/ajax/existsUsername";
            $.getJSON(servletUrl, "username=" + val, function (result) {
                if (result) {
                    let existTip = "用户名已存在或不可用！";
                    alert(existTip);
                    $("#warnTips").text(existTip);
                    show_yn("username_yn",false);
                    $("#username_tip").text(existTip);
                } else {
                    //除用户名可用外，还要符合正则判断
                    $("#warnTips").text("用户名可用！");
                }
            });
        }
    };

    /**
     * 输入框焦点失去事件
     * @param {elementId} ID
     * @return {boolean} 格式是否正确
     */
    userInputBlur = function (ID) {
        //输入框下面的 提示信息（区域id以"_tip"结尾）
        let tipStr = "";
        //根据不同"ID"对相应输入框的值进行不同的正则表达式验证，并选择不同的提示信息
        switch (ID) {
            case "username":
                //用户名验证：必须由汉字，字母，数字或下划线组成，并且长度为6到20位
                let usernameRule = /^[a-zA-Z0-9_\u4e00-\u9fa5]{6,20}$/;
                //调用校验方法（input.js）
                if (reg("username", usernameRule) == false) {
                    tipStr = "用户名必须由6-20位汉字、字母、数字或_混合组成";
                }
                break;

            case "loginPass":
                //登录密码验证：必须由字母，数字下划线组成，并且长度为6到20位
                let loginPassRule = /^\w{6,20}$/;
                if (reg("loginPass", loginPassRule) == false) {
                    tipStr = "登录密码必须由6-20位字母、数字下划线组成";
                }
            // 不 + break; 登录密码输入框失去焦点后要和确认登录密码同时验证

            case "reLoginPass":
                //登录密码确认验证
                let loginPassValue = document.getElementById("loginPass").value;
                let reLoginPassValue = document.getElementById("reLoginPass").value;
                let bool_eq = (loginPassValue != "" && loginPassValue == reLoginPassValue);
                show_yn("reLoginPass_yn", bool_eq);
                if (bool_eq == false) {
                    //因可能在登录密码输入框失去焦点后同时验证，故在此处便要修改提示信息，以免位置出错
                    $("#reLoginPass_tip").text("确认登录密码和登录密码不一致");
                    if (ID == "reLoginPass") {
                        //确认登录密码输入框失去焦点的情况：
                        tipStr = "确认登录密码和登录密码不一致";
                    }
                } else {
                    $("#reLoginPass_tip").text("");
                }
                break;

            case "phone":
                if (document.getElementById("phone").value == "") {
                    //手机号非必填项，可以为空
                    break;
                }
                // 手机号验证
                let phoneRule = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
                if (reg("phone", phoneRule) == false) {
                    tipStr = "手机号格式不对";
                }
                break;

            case "email":
                if (document.getElementById("email").value == "") {
                    //电子邮箱非必填项，可以为空
                    break;
                }
                //电子邮箱验证
                let emailRule = /^[A-z0-9]+@[a-z0-9]+.com$/;
                if (reg("email", emailRule) == false) {
                    tipStr = "电子邮箱需要以“@____.com”结尾";
                }
                break;

            case "txPass":
                //交易密码验证：必须由字母或数字组成，并且长度为6到10位
                let txPassRule = /^[A-Za-z0-9]{6,10}$/;
                if (reg("txPass", txPassRule) == false) {
                    tipStr = "交易密码必须由6-10位字母或数字组成";
                }
            // 不 + break; 交易密码输入框失去焦点后要和确认交易密码同时验证

            case "reTxPass":
                //交易密码确认验证
                let txPassValue = document.getElementById("txPass").value;
                let reTxPassValue = document.getElementById("reTxPass").value;
                let bool_eq1 = (txPassValue != "" && txPassValue == reTxPassValue);
                show_yn("reTxPass_yn", bool_eq1);
                if (bool_eq1 == false) {
                    //因可能在交易密码输入框失去焦点后同时验证，故在此处便要修改提示信息，以免位置出错
                    $("#reTxPass_tip").text("确认交易密码和交易密码不一致");
                    if (ID == "reTxPass") {
                        //确认交易密码输入框失去焦点的情况：
                        tipStr = "确认交易密码和交易密码不一致";
                    }
                } else {
                    $("#reTxPass_tip").text("");
                }
                break;

            //设置特殊情况
            default:
                break;
        }

        //修改提示信息
        $("#" + ID + "_tip").text(tipStr);
        //同时将警告信息栏的内容恢复正常
        $("#warnTips").html("<span style=\"color: #666\">请输入正确格式的用户信息↓↓↓</span>");
        return (tipStr == "");
    };

    // ******给所有需验证的输入框（包括textarea）添加焦点获取事件******
    $(".verifying").focus(function () {
        inputFocus(this.id);
    });

});