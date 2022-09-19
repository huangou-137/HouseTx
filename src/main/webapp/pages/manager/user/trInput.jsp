<%@ page import="pers.ho.bean.User" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/5/12 21:01
  通用的用户信息输入主体，即添加用户和修改用户皆有部分：
  【用户名，登录密码，手机号，电子邮箱，交易密码，简介】
  需要用到的额外文件：form.css
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 静态包含 用户名 的tr输入部分 --%>
<%@include file="/pages/div/trInput/username.jsp"%>

<tr>
    <th><label class="imp">*</label><label for="loginPass">登录密码：</label></th>
    <td><input type="password" class="verifying icon-base icon-loginPass" required
               autocomplete="off" tabindex="1" id="loginPass" name="loginPass"
               placeholder="6-20位字母、数字或_混合" value="${user.loginPass}"/>
        <span id="loginPass_yn"></span>
        <!--  使登录密码可见或隐藏（默认） -->
        <button type="button" class="ovalArea btn-visible" id="visLoginPass"></button>
        <label for="visLoginPass" id="visLoginPassTag"> 显示</label><br/>
        <span id="loginPass_tip">${errorMap.loginPass}</span>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="reLoginPass">确认登录密码：</label></th>
    <td><input type="password" class="verifying icon-base icon-loginPass" required
               autocomplete="off" tabindex="1" id="reLoginPass"
               placeholder="请再次输入登录密码……" value="${user.loginPass}"/>
        <span id="reLoginPass_yn"></span>
        <!--  使确认登录密码可见或隐藏（默认） -->
        <button type="button" class="ovalArea btn-visible" id="visReLoginPass"></button>
        <label for="visReLoginPass" id="visReLoginPassTag"> 显示</label><br/>
        <span id="reLoginPass_tip"></span>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="txPass">交易密码：</label></th>
    <td><input type="password" class="verifying icon-base icon-txPass" required
               autocomplete="off" tabindex="1" id="txPass" name="txPass"
               placeholder="6-10位字母、数字混合" value="${user.txPass}"/>
        <span id="txPass_yn"></span>
        <!--  使交易密码可见或隐藏（默认） -->
        <button type="button" class="ovalArea btn-visible" id="visTxPass"></button>
        <label for="visTxPass" id="visTxPassTag"> 显示</label><br/>
        <span id="txPass_tip">${errorMap.txPass}</span>
    </td>
</tr>

<tr>
    <th><label class="imp">*</label><label for="reTxPass">确认交易密码：</label></th>
    <td><input type="password" class="verifying icon-base icon-txPass" required
               autocomplete="off" tabindex="1" id="reTxPass"
               placeholder="请再次输入交易密码……" value="${user.txPass}"/>
        <span id="reTxPass_yn"></span>
        <!--  使确认交易密码可见或隐藏（默认） -->
        <button type="button" class="ovalArea btn-visible" id="visReTxPass"></button>
        <label for="visReTxPass" id="visReTxPassTag"> 显示</label><br/>
        <span id="reTxPass_tip"></span>
    </td>
</tr>

<%-- 静态包含用户一般信息的tr输入部分 --%>
<%@include file="/pages/div/trInput/userNorm.jsp"%>
