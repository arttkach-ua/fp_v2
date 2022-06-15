<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<fmt:setBundle basename="localization"/>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--FILTERS-->
<p>
    <a class="btn btn-primary" data-toggle="collapse" href="#filtersCollapse" role="button" aria-expanded="false" aria-controls="filtersCollapse">
        <fmt:message key="filters_and_sorting"/>
    </a>
</p>
<div class="collapse" id="filtersCollapse">
    <div class="container-fluid">
        <form class="form-horizontal" action="controller?action=selectCar" method="get">
            <div class="row">
                <input hidden name = "action" id="action" value="carList"/>
                <!--Car class filter-->
                <div class="form-group">
                    <label for="car_class_filter" class="control-label"><fmt:message key="car_class"/></label>
                    <select class="form-control" id="car_class_filter" name="car_class_filter">
                        <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                        <c:forEach items="${carClassList}" var="carClass">
                            <option ${car_class_filter!=null&&car_class_filter eq carClass.getValue() ? 'selected' : ''} value=${carClass.getValue()}>
                                <fmt:message key="${carClass}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <!--car brand filter-->
                <div class="form-group">
                    <label for="car_brand_filter" class="control-label"><fmt:message key="car_brand"/></label>
                    <select class="form-control" id="car_brand_filter" name="car_brand_filter">
                        <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                        <c:forEach items="${carBrandsList}" var="carBrand">
                            <option ${car_brand_filter!=null&&car_brand_filter eq carBrand.getID() ? 'selected' : ''} value=${carBrand.getID()}>${carBrand.getCarBrandName()}</option>
                        </c:forEach>
                    </select>
                </div>
                <!--TransMission filter-->
                <div class="form-group">
                    <label for="transmission_filter" class="control-label"><fmt:message key="transmission"/></label>
                    <select class="form-control" id="transmission_filter" name="transmission_filter">
                        <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                        <c:forEach items="${transmissionList}" var="transmission">
                            <option ${transmission_filter!=null&&transmission_filter eq transmission.getValue() ? 'selected' : ''} value=${transmission.getValue()}>
                                <fmt:message key="${transmission}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div><!--Filters-->
            <div class="row"><!--Sorting-->
                <div class="form-group">
                    <label for="sort_field" class="control-label"><fmt:message key="sort_by"/></label>
                    <select class="form-control" id="sort_field" name="sort_field">
                        <option selected value=-1><fmt:message key="select_general_placeholder"/></option>

                        <c:forEach items="${sortList}" var="entity">
                            <option ${sort_field!=null&&sort_field eq entity ? 'selected' : ''} value=${entity}>
                                <fmt:message key="${entity}"/>
                            </option>
                        </c:forEach>

                    </select>
                </div>
            </div><!--Sorting-->
            <button type="submit" class="btn btn-primary">
                <fmt:message key="button.filter"/>
            </button>
        </form>
    </div>
</div>
<!--Car list form-->
<form action="controller?action=carList" method="get">
    <main class="m-3">
        <div class="row col-md-6">
            <table class="table table-striped table-bordered table-sm">
                <caption style="caption-side: top"><fmt:message key="navbar.company_cars"/></caption>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="available"/></th>
                    <th scope="col"><fmt:message key="car_class"/></th>
                    <th scope="col"><fmt:message key="car_brand"/></th>
                    <th scope="col"><fmt:message key="car_model"/></th>
                    <th scope="col"><fmt:message key="year"/></th>
                    <th scope="col"><fmt:message key="engine"/></th>
                    <th scope="col"><fmt:message key="transmission"/></th>
                    <th scope="col"><fmt:message key="fuel_type"/></th>
                    <th scope="col"><fmt:message key="vin_code"/></th>
                    <th scope="col"><fmt:message key="state_number"/></th>
                    <th scope="col"><fmt:message key="price_per_day"/></th>
                    <th scope="col"><fmt:message key="driver_price"/></th>
                </tr>

                <c:forEach items="${carList}" var="car">
                    <tr>
                        <c:if test = "${(sessionScope.role == 'ADMIN')}">
                            <td><a href="controller?action=editCar&id=${car.getID()}">${car.getID()}</a></td>
                        </c:if>
                        <c:if test = "${(sessionScope.role != 'ADMIN')}">
                            <td>${car.getID()}</td>
                        </c:if>
                        <td>${car.isAvailable()}</td>
                        <td><fmt:message key="${car.getCarClass()}"/></td>
                        <td>${car.getBrand().getCarBrandName()}</td>
                        <td>${car.getModel().getModelName()}</td>
                        <td>${car.getGraduationYear()}</td>
                        <td>${car.getCompleteSet().getEngine()}</td>
                        <td><fmt:message key="${car.getCompleteSet().getTransmission()}"/></td>
                        <td><fmt:message key="${car.getCompleteSet().getFuelType()}"/></td>
                        <td>${car.getVinCode()}</td>
                        <td>${car.getStateNumber()}</td>
                        <td>${car.getTariff().getRentPrice()}</td>
                        <td>${car.getTariff().getDriverPrice()}</td>
                    </tr>
                </c:forEach>
            </table>

            <nav aria-label="Navigation for cars">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=carList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&car_class_filter=${car_class_filter}&car_brand_filter=${car_brand_filter}&transmission_filter=${transmission_filter}&sort_field=${sort_field}"><fmt:message key="pagination.previous"/></a>
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
                                                         href="controller?action=carList&recordsPerPage=${recordsPerPage}&currentPage=${i}&car_class_filter=${car_class_filter}&car_brand_filter=${car_brand_filter}&transmission_filter=${transmission_filter}&sort_field=${sort_field}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=carList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&car_class_filter=${car_class_filter}&car_brand_filter=${car_brand_filter}&transmission_filter=${transmission_filter}&sort_field=${sort_field}"><fmt:message key="pagination.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </main>
</form>
<c:if test = "${(sessionScope.role == 'ADMIN')}">
    <a class="btn btn-primary" id = "add-new-model" href="controller?action=openNewCarPage" role="button"><fmt:message key="add_new_car"/></a>
</c:if>
<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</form>
</html>
