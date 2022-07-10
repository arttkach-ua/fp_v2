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
        <form class="form-horizontal" action="controller" method="get">
            <div class="row">
                <input hidden name = "action" id="action" value="ordersList"/>
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
                <!--Status filter-->
                <div class="form-group">
                    <label for="status_filter" class="control-label"><fmt:message key="status"/></label>
                    <select class="form-control" id="status_filter" name="status_filter">
                        <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                        <c:forEach items="${statusList}" var="status">
                            <option ${status_filter!=null&&status_filter eq status.getValue() ? 'selected' : ''} value=${status.getValue()}>
                                <fmt:message key="${status}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div><!--Filters-->
            <%--
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
            --%>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="button.filter"/>
            </button>
        </form>
    </div>
</div>
<!--Orders form-->
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
                    <th scope="col"><fmt:message key="state_number"/></th>
                    <th scope="col"><fmt:message key="sum"/></th>
                    <th scope="col"><fmt:message key="status"/></th>
                    <th scope="col"></th>
                    <c:if test = "${sessionScope.role == 'MANAGER'}">

                    </c:if>
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
                        <td>${entity.getCar().getStateNumber()}</td>
                        <td>${entity.getRentSum()}</td>
                        <td><fmt:message key="${entity.getStatus()}"/></td>
                        <c:if test = "${(sessionScope.role == 'MANAGER')&&(entity.getStatus()=='NEW')}">
                                <td><a class="btn btn-outline-success" href="controller?action=ConfirmOrder&id=${entity.getID()}" role="button"><fmt:message key="button.confirm_order"/></a>
                                <a class="btn btn-outline-danger" href="controller?action=OpenDeclineOrderPage&id=${entity.getID()}" role="button"><fmt:message key="button.decline_order"/></a></td>
                        </c:if>
                        <c:if test = "${(entity.getStatus() eq'CANCELED')||(entity.getStatus() eq'HASDAMAGE')}">
                            <td>${entity.getManagerComment()}</td>
                        </c:if>
                        <c:if test = "${(sessionScope.role == 'MANAGER')&&(entity.getStatus()=='INPROGRESS')}">
                            <td><a class="btn btn-outline-success" href="controller?action=closeOrder&id=${entity.getID()}" role="button"><fmt:message key="button.closeOrder"/></a>
                                <a class="btn btn-outline-danger" href="controller?action=openCloseOrderWithDamage&id=${entity.getID()}" role="button"><fmt:message key="button.closeOrderWithDamage"/></a></td>
                        </c:if>

                    </tr>
                </c:forEach>
            </table>

            <nav aria-label="Navigation for orders">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=ordersList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}"><fmt:message key="pagination.previous"/></a>
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
                                                         href="controller?action=ordersList&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=ordersList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}"><fmt:message key="pagination.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </main>
</form>

<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</form>
</html>
