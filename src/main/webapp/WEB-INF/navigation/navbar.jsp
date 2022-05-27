
    <c:choose>
        <c:when test = "${sessionScope.role == null}">
            <%@ include file="/WEB-INF/navigation/common_navbar.jsp" %>
            <p>NULL</p>
        </c:when>
        <c:when test = "${sessionScope.role == 'ADMIN'}">
            <%@ include file="/WEB-INF/navigation/admin_navbar.jsp" %>
            <p>'ADMIN'</p>
        </c:when>
        <c:when test = "${sessionScope.role == 'CLIENT'}">
            <%@ include file="/WEB-INF/navigation/client_navbar.jsp" %>
            <p>'CLIENT'</p>
        </c:when>
        <c:when test = "${sessionScope.role == 'MANAGER'}">
            <%@ include file="/WEB-INF/navigation/manager_navbar.jsp" %>
            <p>'MANAGER'</p>
        </c:when>
    </c:choose>