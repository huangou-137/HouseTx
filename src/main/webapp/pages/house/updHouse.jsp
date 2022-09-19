<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/2 20:29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>修改房屋信息 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/form.css">
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
    <script type="text/javascript" src="static/script/input.js" charset="UTF-8"></script>
    <script type="text/javascript" src="static/script/house.js" charset="UTF-8"></script>
    <script type="text/javascript">
        $(function () {
            setTextareasWidth("infoDiv", 0.85, 0.75);

            // 点击reset按钮恢复原值事件
            $("#reset_btn").click(function () {
                return resetClick("是否将所有房产信息恢复原值？");
            });

            //提交修改事件
            $("#sub_btn").click(function () {
                return houseSubmit("确认提交房屋信息进行修改？");
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
            <h2 class="iconH-base iconH2-base iconH-house">房产信息</h2>

            <div class="msg_cont">
                <%-- 警告信息 --%>
                <span style="">Tips:</span>
                <span id="warnTips" class="errorMsg">
                    ${ empty requestScope.msg ?
                            "<span style=\"color: #666\">请输入正确格式的房产信息↓↓↓</span>"
                            :requestScope.msg }
                </span>
            </div>

            <form action="house/myHouse/${house.id}" method="post">
                <input type="hidden" name="_method" value="PUT"/>
                <fieldset><table>
                    <tr><th>编号：</th><td>${house.id}</td></tr>
                    <%--静态包含房屋信息主体 --%>
                    <%@ include file="/pages/div/trInput/houseMain.jsp" %>
                </table></fieldset>
                <br/>
                <div class="btn-center">&emsp;
                    <input type="reset" id="reset_btn" class="btn-common-blue" value="恢复原值"/>&emsp;&emsp;
                    <input type="submit" id="sub_btn" class="btn-common" value="提交修改/重新登记"/>
                </div>
            </form>
        </div>

    </div>
    <div class="rightcolumn">
        <div class="card">
            <h3>修改须知</h3>
            <p>&emsp;&emsp;房产信息修改后需要重新通过管理员审核，
                故修改房产又称为<span style="color: orange">重新登记</span>。</p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>