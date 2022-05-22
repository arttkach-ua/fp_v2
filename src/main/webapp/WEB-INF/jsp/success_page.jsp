<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="localization"/>
<%--<c:set value="${sessionScope.get(\"locale\").language}" var="lang"  scope="page"/>--%>

<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>
<body>
<%@ include file="/WEB-INF/navigation/admin_navbar.jsp" %>
<div class="text-center">
    <img src="resourses/Success.png" alt="Success" style="width:10%">

    <c:forEach items="${messages}" var="message">
        <p class="font-weight-bold"><fmt:message key="${message}" /></p>
    </c:forEach>
</div>

</body>
</html>
