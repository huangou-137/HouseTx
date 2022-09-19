
/**输入框或文本区域textarea的校验 校验成功与否显示区域的ID 必须按照eleId+"_yn"的格式命名
 * @param {elementId} eleId input标签ID
 * @param  rule 用于验证的正则表达式
 * @return {boolean} 校验是否成功
 */
function reg(eleId, rule) {
    let inputValue = document.getElementById(eleId).value;
    let result = rule.test(inputValue.trim());
    // 校验成功与否显示区域的ID
    let id_yn = eleId + "_yn";
    let bool_yn = (result && inputValue != "");
    show_yn(id_yn, bool_yn);
    return bool_yn;
}

/**
 * 更改校验成功与否显示区域的文字内容和字体颜色
 * @param {elementId} id_yn 校验成功与否显示区域ID
 * @param {boolean} bool_yn   校验成功与否
 */
function show_yn(id_yn, bool_yn) {
    if (bool_yn === true) {
        document.getElementById(id_yn).innerHTML = "√";
        document.getElementById(id_yn).style.color = "green";
    } else {
        document.getElementById(id_yn).innerHTML = "×";
        document.getElementById(id_yn).style.color = "red";
    }
}

$(function () {

    /**
     * 检查id为ID的输入表单是否出错，在更新提示信息区域或警告栏处的信息<br/>
     * 一般用于焦点获取事件
     */
    inputFocus = function (ID) {
        //未校验或校验成功时，输入框下面的提示信息区域（id以"_tip"结尾）为空
        if ($("#" + ID + "_yn").text() != "×") {
            $("#" + ID + "_tip").text("");
        } else {
            //修改警告信息栏的内容
            $("#warnTips").text($("#" + ID + "_tip").text());
        }
    };

    /**
     * 检查所有ID后缀为"_yn"的span标签 <br/> 一般用于提交表单事件
     * @param pojoName 实体名称，如用户、房产……
     * @return {boolean} 检查是否无误
     */
    checkAllYn = function (pojoName) {
        // 让焦点所在的文本框失去焦点
        $(document.activeElement).blur();
        //获取所有的校验成功与否显示span标签，并循环检查
        let elems = $("span[id$=\"_yn\"]");
        for (let i = 0; i < elems.length; i++) {
            if (elems[i].innerText == "×") {
                alert(pojoName + "信息输入错误，请仔细检查!!!");
                return false;
            }
        }
        return true;
    };

    /**
     * 检查所有ID后缀为"_yn"的span标签，并弹出确认窗口<br/>
     * 一般用于提交表单事件
     * @param pojoName 实体名称，如用户、房产……
     * @param confirmTip 确认弹窗的提示信息
     * @return {boolean} 检查是否无误  及  确认弹窗选择
     */
    checkAndConfirm = function (pojoName, confirmTip) {
        if (checkAllYn(pojoName) === true) {
            return confirm(confirmTip);
        } else {
            return false;
        }
    };

    /**
     * 清空所有"_yn"、"_tip"的文本内容
     */
    clearAllYnAndTip = function () {
        //循环重置所有校验成功与否显示区域中的内容
        $("span[id$=\"_yn\"]").each(function(){
            $(this).text("");
        });
        //循环重置所有提示消息标签中的内容
        $("span[id$=\"_tip\"]").each(function(){
            $(this).text("");
        });
    };

    /**
     * 重置按钮的点击事件
     * @param confirmTip 确认弹窗的提示信息
     * @return {boolean} 确认弹窗的选择————是否重置？
     */
    resetClick = function (confirmTip) {
        if (confirm(confirmTip)) {
            clearAllYnAndTip();
            return true;
        } else {
            return false;
        }
    };
});
