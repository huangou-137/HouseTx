<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 黄欧
  Time: 2021/4/5 20:54
  警告信息栏（无默认显示信息）
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty requestScope.msg}">
    <script type="text/javascript">
        $(function () {
            alert("${requestScope.msg}");
        });
    </script>
    <div class="msg_cont">
            <%-- 警告信息 --%>
        <span id="warnTips" class="errorMsg">
                ${requestScope.msg}
        </span>
    </div>
</c:if>
