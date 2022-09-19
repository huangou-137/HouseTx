//与管理员信息修改相关的js函数

$(function () {

    /**
     * 输入框焦点失去事件
     * @param {elementId} ID
     * @return {boolean} 格式是否正确
     */
    let adminInputBlur = function (ID) {
        //输入框下面的 提示信息（区域id以"_tip"结尾）
        let tipStr = "";
        //根据不同"ID"对相应输入框的值进行不同的正则表达式验证，并选择不同的提示信息
        switch (ID) {

            case "adminName":
                //管理员名验证：必须由汉字，字母，数字或下划线组成，并且长度为6到20位
                let adminNameRule = /^[a-zA-Z0-9_\u4e00-\u9fa5]{6,20}$/;
                //调用校验方法（input.js）
                if (reg("adminName", adminNameRule) == false) {
                    tipStr = "管理员名必须由6-20位汉字、字母、数字或_混合组成";
                }
                break;

            case "pass":
                //密码验证：必须由字母，数字下划线组成，并且长度为8到20位
                let passRule = /^\w{8,20}$/;
                if (reg("pass", passRule) == false) {
                    tipStr = "密码必须由8-20位字母、数字下划线组成";
                }
            // 不 + break; 密码输入框失去焦点后要和确认密码同时验证

            case "rePass":
                //密码确认验证
                let passValue = document.getElementById("pass").value;
                let rePassValue = document.getElementById("rePass").value;
                let bool_eq = (passValue != "" && passValue == rePassValue);
                show_yn("rePass_yn", bool_eq);
                if (bool_eq == false) {
                    //因可能在密码输入框失去焦点后同时验证，故在此处便要修改提示信息，以免位置出错
                    $("#rePass_tip").text("确认密码和密码不一致");
                    if (ID != "pass") {
                        //确认密码输入框失去焦点的情况：
                        tipStr = "确认密码和密码不一致";
                    }
                } else {
                    $("#rePass_tip").text("");
                }
                break;

            case "tel":
                // 固话号验证
                let telRule = /^\d{3,4}\-\d{7,8}$/;
                if (reg("tel", telRule) == false) {
                    tipStr = "固话号格式不对";
                }
                break;

            case "email":
                //电子邮箱验证
                let emailRule = /^[A-z0-9]+@[a-z0-9]+.com$/;
                if (reg("email", emailRule) == false) {
                    tipStr = "电子邮箱需要以“@____.com”结尾";
                }
                break;

            //设置特殊情况
            default:
                break;
        }

        //修改提示信息
        $("#" + ID + "_tip").text(tipStr);
        //同时将警告信息栏的内容恢复正常
        $("#warnTips").html("<span style=\"color: #666\">请输入正确格式的管理员信息↓↓↓</span>");
        return (tipStr == "");
    }

});