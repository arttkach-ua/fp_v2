<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%--
<fmt:setLocale value="ua"/>
--%>
<fmt:setBundle basename="localization"/>
<%--<c:set value="${sessionScope.get(\"locale\").language}" var="lang"  scope="page"/>--%>

<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<!--Setting NavBar-->
<%--
<c:choose>
    <c:when test="${sessionScope.get(role)='admin'}">
        <%@ include file="/WEB-INF/navigation/admin_navbar.jsp" %>
    </c:when>
</c:choose>
--%>
<%@ include file="/WEB-INF/navigation/admin_navbar.jsp" %>

<%@ include file="/WEB-INF/navigation/footer.jsp" %>

</body>
</html>
