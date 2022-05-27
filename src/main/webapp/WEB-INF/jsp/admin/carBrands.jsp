<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setBundle basename="localization"/>

<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--Car brands list form -->
<form action="controller?action=carBrands" method="get">
    <main class="m-3">
        <div class="row col-md-6">
            <table class="table table-striped table-bordered table-sm">
                <tr>
                    <th>ID</th>
                    <th><fmt:message key="car_brand"/></th>
                    <!--<th></th>-->
                </tr>

                <c:forEach items="${carBrandsList}" var="carBrand">
                    <tr>
                        <td>${carBrand.getID()}</td>
                        <td>${carBrand.getCarBrandName()}</td>
                        <!--<td><a href="#" id="remove"
                               onclick="document.getElementById('action').value = 'remove';document.getElementById('idEmployee').value = '${user.getID()}';

                                       document.getElementById('employeeForm').submit();">
                            <span class="glyphicon glyphicon-trash"/>
                        </a>

                        </td>-->
                    </tr>
                </c:forEach>
            </table>

            <nav aria-label="Navigation for car brands">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=carBrands&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}"><fmt:message key="pagination.previous"/></a>
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
                                                         href="controller?action=carBrands&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=carBrands&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}"><fmt:message key="pagination.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>

        </div>
    </main>
</form>

<a class="btn btn-primary" id = "add-new-brand" href="controller?action=openNewCarBrandPage" role="button"><fmt:message key="add_new_car_brand"/></a>

<%@ include file="/WEB-INF/navigation/footer.jsp" %>

</body>
</html>
