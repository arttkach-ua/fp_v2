
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<!--Search Form -->
<form action="usersList" method="get" id="seachUserForm" role="form">
    <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
    <div class="form-group col-xs-5">
        <input type="text" name="userName" id="userName" class="form-control" required="true" placeholder="Type the Name or Last Name of the user"/>
    </div>
    <button type="submit" class="btn btn-info">
        <span class="glyphicon glyphicon-search"></span> Search
    </button>
    <br></br>
    <br></br>
</form>
<!--Users list Form -->
<form action="usersList" method="get">
<main class="m-3">
    <div class="row col-md-6">
        <table class="table table-striped table-bordered table-sm">
            <tr>
                <th>ID</th>
                <th>e-mail</th>
                <th>First Name</th>
                <th>Second Name</th>
                <th>Documents</th>
                <th>Phone</th>
                <th>Role</th>
                <th>blocked</th>
                <th></th>>
            </tr>

            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.getID()}</td>
                    <td>${user.getEmail()}</td>
                    <td>${user.getFirstName()}</td>
                    <td>${user.getSecondName()}</td>
                    <td>${user.getDocumentInfo()}</td>
                    <td>${user.getPhone()}</td>
                    <td>${user.getRole()}</td>
                    <td>${user.isBlocked()}</td>
                    <td><a href="#" id="remove"
                           onclick="document.getElementById('action').value = 'remove';document.getElementById('idEmployee').value = '${user.getID()}';

                                   document.getElementById('employeeForm').submit();">
                        <span class="glyphicon glyphicon-trash"/>
                    </a>

                    </td>
                </tr>
            </c:forEach>
        </table>

        <nav aria-label="Navigation for users">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item"><a class="page-link"
                                             href="usersList?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}"><fmt:message key="pagination.previous"/></a>
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
                                                     href="usersList?recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="usersList?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}"><fmt:message key="pagination.next"/></a>
                    </li>
                </c:if>
            </ul>
        </nav>

    </div>
</main>
</form>>
<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</form>
</html>
