<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="pers.ho.bean.House"%>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/1 16:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>我的房子详情 | 住房交易系统</title>
    <%@ include file="/pages/common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/layout.css">
</head>

<body>
<%@ include file="/pages/common/menu.jsp" %>

<div class="row">
    <div class="leftcolumn">
        &nbsp;
    </div>

    <div class="midcolumn">
        <div class="card" id="infoDiv">
            <h2>房屋详细信息</h2>
            <%--静态包含警告信息栏 --%>
            <%@ include file="/pages/div/warnTips.jsp" %>

            <fieldset><table>
                <tr><th>编号:</th><td>${house.id}</td></tr>
                <tr><th>登记者ID:</th><td>${house.uid}（本人）</td></tr>
                <%--静态包含房产其它信息 --%>
                <%@include file="/pages/div/houseMoreView.jsp"%>
            </table></fieldset><br/>

            <div class="btn-center">
                <c:if test="${canDel}">
                    <a href="house/myHouse/toDel/${house.id}">删除房屋</a>&emsp;&emsp;
                </c:if>
                <c:if test="${canUpd}">
                    <a href="house/myHouse/toUpd/${house.id}">修改房屋信息</a>
                </c:if>
            </div>

        </div>
    </div>

    <div class="rightcolumn">
        <div class="card">
            <h3>关于房屋状态</h3>
            <p>（1）审核中——登记房产后的默认状态<br/>
                （2）审核失败——没有通过审核<br/>
                （3）待售中——审核通过<br/>
                （4）交易中——房主接受了买方的交易请求，商谈中<br/>
                （5）已出售——房子已被售出<br/>
                <b>tip:</b>审核失败时可以通过修改房屋信息再次提交审核；交易中或已出售的房产不能被删除和修改信息!
            </p>
        </div>
    </div>
</div>

<%@include file="/pages/common/footer.jsp" %>
</body>
</html>
