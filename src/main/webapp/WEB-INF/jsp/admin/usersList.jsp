<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<fmt:setBundle basename="localization"/>

<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--Search Form -->
<form action="controller" method="get" id="seachUserForm" role="form">
    <div class="fs-2 mb-3">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
        </svg>
        <fmt:message key="search"/>
    </div>
    <input type="hidden" id="searchAction" name="action" value="usersList">
    <div class="form-group col-xs-5">
        <input type="text" name="userName" id="userName" class="form-control" value="${userName}" placeholder="<fmt:message key="user_search_placeholder"/>"/>
        <button type="submit" class="btn btn-info">
            <span class="glyphicon glyphicon-search"></span>
            <fmt:message key="search"/>
        </button>
    </div>

</form>
<!--Users list Form -->
<div class="fs-2 mb-3">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-card-checklist" viewBox="0 0 16 16">
        <path d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z"/>
        <path d="M7 5.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-1.496-.854a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0zM7 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-1.496-.854a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
    </svg>
    <fmt:message key="users_list"/>
</div>
<form action="controller?action=usersList" method="get">
<main class="m-3">
    <div class="row col-md-6">
        <table class="table table-striped table-bordered table-sm">
            <tr>
                <th>ID</th>
                <th><fmt:message key="email"/></th>
                <th><fmt:message key="first_name"/></th>
                <th><fmt:message key="second_name"/></th>
                <th><fmt:message key="document_info"/></th>
                <th><fmt:message key="phone_number"/></th>
                <th><fmt:message key="role"/></th>
                <th><fmt:message key="blocked"/></th>
                <th><fmt:message key="receive_notifications"/></th>
                <th></th>
            </tr>

            <c:forEach items="${usersList}" var="user">
                <tr>
                    <td>${user.getID()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getFirstName()}</td>
                    <td>${user.getSecondName()}</td>
                    <td>${user.getDocumentInfo()}</td>
                    <td>${user.getPhone()}</td>
                    <td>${user.getRole()}</td>
                    <td>
                        <c:if test="${user.getBlocked()}">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
                                <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"></path>
                            </svg>
                            <fmt:message key="${user.getBlocked()}"/>
                            </c:if>
                    </td>

                    <td>
                            <c:if test="${user.isReceiveNotifications()}">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-clipboard-check-fill" viewBox="0 0 16 16">
                                    <path d="M6.5 0A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3Zm3 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h3Z"/>
                                    <path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1A2.5 2.5 0 0 1 9.5 5h-3A2.5 2.5 0 0 1 4 2.5v-1Zm6.854 7.354-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L7.5 10.793l2.646-2.647a.5.5 0 0 1 .708.708Z"/>
                                </svg>
                                <fmt:message key="${user.isReceiveNotifications()}"/>
                            </c:if>
                    </td>

                    <td>
                        <c:if test="${!user.getBlocked()}">
                            <a href="controller?action=setUserBlock&newValue=true&id=${user.getID()}" class="btn btn-danger"><fmt:message key="block_user"/></a>
                        </c:if>
                        <c:if test="${user.getBlocked()}">
                            <a href="controller?action=setUserBlock&newValue=false&id=${user.getID()}" class="btn btn-success"><fmt:message key="unblock_user"/></a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <nav aria-label="Navigation for users">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item"><a class="page-link"
                                             href="usersList?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&userName=${userName}"><fmt:message key="pagination.previous"/></a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active"><a class="page-link">
                                    ${i} <span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?action=usersList&recordsPerPage=${recordsPerPage}&userName=${userName}&currentPage=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="controller?action=usersList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&userName=${userName}"><fmt:message key="pagination.next"/></a>
                    </li>
                </c:if>
            </ul>
        </nav>

    </div>
</main>
</form>
<a class="btn btn-primary" id = "add-new-user" href="controller?action=openRegisterForm" role="button"><fmt:message key="add_new_user"/></a>
<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</form>
</html>
