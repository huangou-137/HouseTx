<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/8/24 12:17
  作为tr输入的 【验证码】 通用部分 以及 相对应脚本
  需要用到的额外文件：form.css
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<tr>
    <th>&emsp;<label class="imp">*</label><label for="checkCode">验证码：</label></th>
    <td>
        <input type="text" class="icon-base icon-code" tabindex="1" required
               autocomplete="off" id="checkCode" name="code"
               placeholder="请输入验证码……" value=""/>
        <img id="code_img" src="checkCode/create" style="vertical-align:middle;" alt=" "/>
    </td>
</tr>

<script type="text/javascript">
    $(function () {
        //点击验证码图片时，刷新图片链接
        $("#code_img").click(function () {
            let data = new Date().getTime();
            $(this).attr("src", "checkCode/create?" + data);
        });
    });
</script>