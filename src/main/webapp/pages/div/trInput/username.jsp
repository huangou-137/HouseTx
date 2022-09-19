<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/8/23 22:54
  作为tr输入的 【用户名】 通用部分
  需要用到的额外文件：form.css
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<tr>
    <th><label class="imp">*</label><label for="username">用户名：</label></th>
    <td>
        <input type="text" class="verifying icon-base icon-user" required
               autocomplete="off" tabindex="1" id="username" name="username"
               placeholder="6-20位汉字字母数字或_混合" value="${user.username}"/>
        <span id="username_yn"></span><br/>
        <span id="username_tip">${errorMap.username}</span>
    </td>
</tr>
