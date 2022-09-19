<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Time: 2021/2/18 14:27
  分页条
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="page_nav">

    <c:set var="pageNumStr" value="${pageUrl.endsWith('?') ? 'pageNum=' : '&pageNum='}" />

    <%--大于首页，才显示--%>
    <c:if test="${pageInfo.pageNum > 1}">
        <a href="${pageUrl }${pageNumStr}1">首页</a>
        <a href="${pageUrl }${pageNumStr}${pageInfo.pageNum-1}">上一页</a>
    </c:if>
    <%--页码输出的开始--%>
    <c:choose>
        <%--情况1：如果总页码小于等于5的情况，页码的范围是：1-总页码--%>
        <c:when test="${pageInfo.pages <= 5 }">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${pageInfo.pages}"/>
        </c:when>
        <%--情况2：总页码大于5的情况--%>
        <c:when test="${pageInfo.pages > 5}">
            <c:choose>
                <%--小情况1：当前页码为前面3个：1，2，3的情况，页码范围是：1-5.--%>
                <c:when test="${pageInfo.pageNum <= 3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                </c:when>
                <%--小情况2：当前页码为最后3个，页码范围是：总页码减4 - 总页码--%>
                <c:when test="${pageInfo.pageNum > pageInfo.pages-3}">
                    <c:set var="begin" value="${pageInfo.pages-4}"/>
                    <c:set var="end" value="${pageInfo.pages}"/>
                </c:when>
                <%--其它情况3：页码范围是：当前页码减2 - 当前页码加2--%>
                <c:otherwise>
                    <c:set var="begin" value="${pageInfo.pageNum-2}"/>
                    <c:set var="end" value="${pageInfo.pageNum+2}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i == pageInfo.pageNum}">
            【${i}】
        </c:if>
        <c:if test="${i != pageInfo.pageNum}">
            <a href="${ pageUrl }${pageNumStr}${i}">${i}</a>
        </c:if>
    </c:forEach>
    <%--页码输出的结束--%>

    <%-- 如果已经 是最后一页，则不显示下一页，末页 --%>
    <c:if test="${pageInfo.pageNum < pageInfo.pages}">
        <a href="${ pageUrl }${pageNumStr}${pageInfo.pageNum+1}">下一页</a>
        <a href="${ pageUrl }${pageNumStr}${pageInfo.pages}">末页</a>
    </c:if>

    共${ pageInfo.pages }页，本页共${ pageInfo.size }条记录
    到第<input type="number" max="${ pageInfo.pages }" min="1"
             value="${param.pageNum}" name="pn" id="pn_input" size="3"/>页
    <input id="searchPageBtn" type="button" class="btn-common" value="确定">

    <script type="text/javascript">
        $(function () {
            // 页码输入栏失去焦点事件
            $("#pn_input").blur(function () {
                // 限制页码值
                let pageNum = parseInt($("#pn_input").val());
                let minNo =  parseInt($("#pn_input").attr("min"));
                let maxNo =  parseInt($("#pn_input").attr("max"));
                if (isNaN(pageNum) || pageNum < minNo) {
                    $("#pn_input").val(minNo);
                } else if (pageNum > maxNo){
                    $("#pn_input").val(maxNo);
                }
            });

            // 跳到指定的页码
            $("#searchPageBtn").click(function () {
                let pageNum = $("#pn_input").val();
                // location地址栏对象的href属性————浏览器地址栏中的地址（可读写）
                location.href = "${pageScope.basePath}${pageUrl}${pageNumStr}" + pageNum;
            });
        });
    </script>

</div>
