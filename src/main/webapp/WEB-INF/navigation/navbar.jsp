
    <c:choose>
        <c:when test = "${sessionScope.role == null}">
            <%@ include file="/WEB-INF/navigation/common_navbar.jsp" %>
        </c:when>
        <c:when test = "${sessionScope.role == 'ADMIN'}">
            <%@ include file="/WEB-INF/navigation/admin_navbar.jsp" %>
        </c:when>
        <c:when test = "${sessionScope.role == 'CLIENT'}">
            <%@ include file="/WEB-INF/navigation/client_navbar.jsp" %>
        </c:when>
        <c:when test = "${sessionScope.role == 'MANAGER'}">
            <%@ include file="/WEB-INF/navigation/manager_navbar.jsp" %>
        </c:when>
    </c:choose>