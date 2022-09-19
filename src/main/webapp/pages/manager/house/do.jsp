<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/8/25 23:39
  通用的【删除房产、前往准备修改房产】操作
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<br/>
<div class="btn-center">
    <form action="manager/house/${house.id}" method="post">
        <input type="hidden" name="_method" value="delete"/>
        <input id="del_btn" type="submit" class="btn-common-red" value="删除房产"/>
    </form>
    &emsp;&emsp;&emsp;
    <a href="manager/house/toUpd/${house.id}"><button class="btn-common">修改房产信息</button></a>
</div>

<script type="text/javascript">
    $(function () {
        $("#del_btn").click(function () {
            return confirm('确认删除该房产？');
        });
    });
</script>
