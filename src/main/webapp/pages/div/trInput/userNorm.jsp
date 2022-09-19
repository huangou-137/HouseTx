<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/8/23 22:10
  作为tr输入的 用户一般信息 的通用部分：【手机，邮箱，简介】
  需要用到的额外文件：form.css
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<tr>
    <th><label for="phone">手机号：</label></th>
    <td><input type="text" class="verifying icon-base icon-phone" autocomplete="off" tabindex="1"
               id="phone" name="phone" placeholder="请输入手机号码……" value="${user.phone}"/>
        <span id="phone_yn"></span><br/><span id="phone_tip">${errorMap.phone}</span>
    </td>
</tr>

<tr>
    <th><label for="email">电子邮箱：</label></th>
    <td><input type="email" class="verifying icon-base icon-email" autocomplete="off" tabindex="1"
               id="email" name="email" placeholder="请输入电子邮箱……" value="${user.email}"/>
        <span id="email_yn"></span><br/><span id="email_tip">${errorMap.email}</span>
    </td>
</tr>

<tr>
    <th><label for="desc">用户简介：</label></th>
    <td><textarea class="icon-base icon-desc descTextArea" tabindex="1" id="desc" name="desc"
                  placeholder="请输入用户简介……">${user.desc}</textarea>
    </td>
</tr>