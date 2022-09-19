//与房屋信息相关的js函数（如：登记房产、修改房产信息等）
//需与input.js一起使用

$(function () {

    //给所有需验证的输入框添加焦点失去事件
    $(".verifying").blur(function () {
        //输入框下面的 提示信息（区域id以"_tip"结尾）
        let tipStr = "";
        //根据不同"id"对相应输入框的值进行不同的正则表达式验证，并选择不同的提示信息
        switch (this.id) {
            case "size":
                // 面积验证：必须是小于32 767（2^15 - 1）的正整数
                let sizeValue = $("#size").val();
                if(isNaN(sizeValue) || parseInt(sizeValue)<0 || parseInt(sizeValue)>32767){
                    //用于检查其参数是否是非数字值
                    tipStr = "必须输入0 - 32767之间的正整数！";
                    show_yn("size_yn", false);
                } else {
                    show_yn("size_yn", true);
                }
                break;

            case "price":
                // 价格验证：必须是正数(不包括0，小数保留两位)
                let priceRule = /^((0{1}\.\d{1,2})|([1-9]\d*\.{1}\d{1,2})|([1-9]+\d*))$/;
                //调用校验方法（input.js）
                if (reg("price", priceRule) == false) {
                    tipStr = "必须输入正数，且小数保留两位！";
                }
                break;

            //设置特殊情况
            default:
                break;
        }

        //修改提示信息
        $("#" + this.id + "_tip").text(tipStr);
        //同时将警告信息栏的内容恢复正常
        $("#warnTips").html("<span style=\"color: #666\">请输入正确格式的房屋信息↓↓↓</span>");
    });

    //给所有需验证的输入框添加焦点获取事件
    $(".verifying").focus(function () {
        inputFocus(this.id);
    });

    /**
     * 检查所有ID后缀为"_yn"的span标签，并弹出确认提交窗口
     * @param {string} confirmTip 选择弹窗提示文本
     * @return {boolean} 检查是否无误  及  确认弹窗选择
     */
    houseSubmit = function (confirmTip) {
        if (checkAllYn("房屋") === false) {
            return false;
        }
        if ($("#areaId").val() < 0) {
            alert("请选择地区!!!");
            return false;
        }
        return confirm(confirmTip);
    };
});