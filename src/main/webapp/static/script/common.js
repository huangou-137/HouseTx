$(function () {

    //当鼠标指针悬在任意按钮上面时
    $("button").hover(
        function () {
            this.style.cursor = "pointer";
        });

    // 获取项目服务器的网络地址
    getProjUrl = function () {
        return "http://localhost:8080/HouseTx_Maven";
    };

    //  ————下拉菜单相关————
    // 光标悬浮在下拉菜单时
    $(".dropdown-content").hover(function () {
        $(this).prev().addClass("hover");
    });
    // 光标离开下拉菜单时
    $(".dropdown-content").mouseleave(function () {
        $(this).prev().removeClass("hover");
    });

    /**
     * 点击显示/隐藏按钮
     * @param {elementId} btnID 按钮ID
     * @param {elementId} inputID   待显示/隐藏的input输入框
     * @param {elementId} tagID 信息提示span的ID
     */
    visInput = function (btnID, inputID, tagID) {
        if ($("#"+inputID).attr("type") === "password") {
            $("#"+inputID).attr("type", "text");
            $("#"+btnID).removeClass("btn-visible").addClass("btn-invisible");
            $("#"+tagID).text(" 隐藏");
        } else {
            $("#"+inputID).attr("type", "password");
            $("#"+btnID).removeClass("btn-invisible").addClass("btn-visible");
            $("#"+tagID).text(" 显示");
        }
    };

    /**
     * 调用本函数会设置页面内所有的文本区域宽度
     * @param {string} baseElemId 以该ID所属的标签宽度作为基准
     * @param {number} factor 因子，倍数，一般为1.0以下
     * @param reFactor {number} 调整浏览器窗口大小时的变动因子
     */
    setTextareasWidth = function (baseElemId, factor, reFactor) {
        $("textarea").css("width",$("#"+baseElemId).width()*factor);
        //当调整浏览器窗口大小时：
        $(window).resize(function(){
            $("textarea").css("width",$("#"+baseElemId).width()*reFactor);
        });
    };

    /**
     * 倒计时（一直到0停止）
     * @param count 剩余秒数
     * @param showId 显示区域ID
     */
    countDown= function (count,showId) {
        let num = parseInt(count);
        if (isNaN(num)) {
            return;
        }
        //1秒后执行一次
        window.setTimeout(function () {
            count--;
            $("#" + showId).text(count);
            countDown(num,showId)
        }, 1000);
    };

});