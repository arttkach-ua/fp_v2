<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<fmt:setBundle basename="localization"/>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--Car list form-->
<form action="controller?action=ordersList" method="get">
    <main class="m-3">
        <div class="row col-md-6">
            <table class="table table-striped table-bordered table-sm">
                <caption style="caption-side: top"><fmt:message key="navbar.my_orders"/></caption>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="first_name"/></th>
                    <th scope="col"><fmt:message key="second_name"/></th>
                    <c:if test = "${sessionScope.role == 'MANAGER'}">
                        <th scope="col"><fmt:message key="phone_number"/></th>
                        <th scope="col"><fmt:message key="email"/></th>
                    </c:if>
                    <th scope="col"><fmt:message key="car_brand"/></th>
                    <th scope="col"><fmt:message key="car_model"/></th>
                    <th scope="col"><fmt:message key="completeSetName"/></th>
                    <th scope="col"><fmt:message key="sum"/></th>
                    <th scope="col"><fmt:message key="status"/></th>
                    <c:if test = "${sessionScope.role == 'MANAGER'}">

                    </c:if>
                    <%--
                    <th scope="col"><fmt:message key="vin_code"/></th>
                    <th scope="col"><fmt:message key="state_number"/></th>
                    <th scope="col"><fmt:message key="price_per_day"/></th>
                    <th scope="col"><fmt:message key="driver_price"/></th>
                    --%>
                </tr>

                <c:forEach items="${entityList}" var="entity">
                    <tr>
                        <td>${entity.getID()}</td>
                        <td>${entity.getClient().getFirstName()}</td>
                        <td>${entity.getClient().getSecondName()}</td>
                        <c:if test = "${sessionScope.role == 'MANAGER'}">
                            <td>${entity.getClient().getPhone()}</td>
                            <td>${entity.getClient().getEmail()}</td>
                        </c:if>
                        <td>${entity.getCar().getBrand().getCarBrandName()}</td>
                        <td>${entity.getCar().getModel().getModelName()}</td>
                        <td>${entity.getCar().getCompleteSet().getName()}</td>
                        <td>${entity.getRentSum()}</td>
                        <td>${entity.getStatus()}</td>
                        <c:if test = "${(sessionScope.role == 'MANAGER')&&(entity.getStatus()=='NEW')}">
                                <td><a class="btn btn-outline-success" href="controller?action=ConfirmOrder&id=${entity.getID()}" role="button">Confirm order</a></td>
                                <td><a class="btn btn-outline-danger" href="controller?action=OpenDeclineOrderPage&id=${entity.getID()}" role="button">Dectine order</a></td>
                        </c:if>
                        <%--
                        <td><fmt:message key="${car.getCompleteSet().getTransmission()}"/></td>
                        <td><fmt:message key="${car.getCompleteSet().getFuelType()}"/></td>
                        <td>${car.getVinCode()}</td>
                        <td>${car.getStateNumber()}</td>
                        <td>${car.getTariff().getRentPrice()}</td>
                        <td>${car.getTariff().getDriverPrice()}</td>
                        --%>
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
