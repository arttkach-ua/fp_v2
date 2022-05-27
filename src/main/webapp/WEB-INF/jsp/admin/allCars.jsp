<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%--<fmt:setLocale value="ua"/>--%>
<fmt:setBundle basename="localization"/>

<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--Car list form-->
<form action="controller?action=carList" method="get">
    <main class="m-3">
        <div class="row col-md-6">
            <table class="table table-striped table-bordered table-sm">
                <tr>
                    <th>ID</th>
                    <th><fmt:message key="car_class"/></th>
                    <th><fmt:message key="car_brand"/></th>
                    <th><fmt:message key="car_model"/></th>
                    <th><fmt:message key="year"/></th>
                    <th><fmt:message key="engine"/></th>
                    <th><fmt:message key="transmission"/></th>
                    <th><fmt:message key="fuel_type"/></th>
                    <th><fmt:message key="vin_code"/></th>
                    <th><fmt:message key="state_number"/></th>
                </tr>

                <c:forEach items="${carList}" var="car">
                    <tr>
                        <td>${car.getID()}</td>
                        <td><fmt:message key="${car.getCarClass()}"/></td>
                        <td>${car.getBrand().getCarBrandName()}</td>
                        <td>${car.getModel().getModelName()}</td>
                        <td>${car.getGraduationYear()}</td>
                        <td>${car.getEngine()}</td>
                        <td><fmt:message key="${car.getTransmission()}"/></td>
                        <td><fmt:message key="${car.getFuelType()}"/></td>
                        <td>${car.getVinCode()}</td>
                        <td>${car.getStateNumber()}</td>
                    </tr>
                </c:forEach>
            </table>

            <nav aria-label="Navigation for cars">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=carList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}"><fmt:message key="pagination.previous"/></a>
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
                                                         href="controller?action=carList&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=carList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}"><fmt:message key="pagination.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </main>
</form>

<a class="btn btn-primary" id = "add-new-model" href="controller?action=openNewCarPage" role="button"><fmt:message key="add_new_car"/></a>

<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</form>
</html>
