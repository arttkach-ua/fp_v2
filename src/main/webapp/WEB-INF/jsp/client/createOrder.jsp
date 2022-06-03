<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setBundle basename="localization"/>

<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>
<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--New order form -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="new_order"/></div>
                <div class="card-body">

                    <form class="form-horizontal" method="POST" action="controller?action=addNewOrder">
                        <input type="hidden" id="id" name="id" value="${car.getID()}">
                        <table class="table table-sm">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col"><fmt:message key="characteristic"/></th>
                                <th scope="col"><fmt:message key="value"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td><fmt:message key="car_brand"/></td>
                                <td>${car.getBrand().getCarBrandName()}</td>
                            </tr><!--Brand-->
                            <tr>
                                <th scope="row">2</th>
                                <td><fmt:message key="car_model"/></td>
                                <td>${car.getModel().getModelName()}</td>
                            </tr><!--Model-->
                            <tr>
                                <th scope="row">3</th>
                                <td><fmt:message key="car_class"/></td>
                                <td>${car.getCarClass()}</td>
                            </tr><!--Class-->
                            <tr>
                                <th scope="row">4</th>
                                <td><fmt:message key="year"/></td>
                                <td>${car.getGraduationYear()}</td>
                            </tr><!--Year-->
                            <tr>
                                <th scope="row">5</th>
                                <td><fmt:message key="body_style"/></td>
                                <td>${car.getBodyStyle()}</td>
                            </tr><!--Body style-->
                            <tr>
                                <th scope="row">6</th>
                                <td><fmt:message key="fuel_type"/></td>
                                <td>${car.getFuelType()}</td>
                            </tr><!--Fuel type-->
                            <tr>
                                <th scope="row">7</th>
                                <td><fmt:message key="engine"/></td>
                                <td>${car.getEngine()}</td>
                            </tr><!--Engine-->
                            <tr>
                                <th scope="row">9</th>
                                <td><fmt:message key="state_number"/></td>
                                <td>${car.getStateNumber()}</td>
                            </tr><!--State number-->
                            <tr>
                                <th scope="row">10</th>
                                <td><fmt:message key="driver"/></td>
                                <td><input class="form-check-input" type="checkbox" id="with_driver" name="with_driver"/><fmt:message key="i_need_driver"/></td>
                            </tr><!--Engine-->
                            <tr>
                                <th scope="row">11</th>
                                <td><fmt:message key="price_per_day"/></td>
                                <td><p id="price_per_day" name="price_per_day">${car.getPrice()}</p></td>
                            </tr><!--Engine-->
                            <tr>
                                <th scope="row">12</th>
                                <td><fmt:message key="days_count"/></td>
                                <td><input type="number" value="1" step="1" class="form-control" id="days_count" name = "days_count" onchange="getSum()"></td>
                            </tr><!--DAYS-->
                            <tr>
                                <th scope="row">13</th>
                                <td><fmt:message key="sum"/></td>
                                <td><input type="number" value="${car.getPrice()}" class="form-control" id="sum" name = "sum" readonly></td>
                            </tr><!--SUM-->
                            <tr>
                                <th scope="row">14</th>
                                <td><fmt:message key="document_info"/></td>
                                <td><input type="text" value="${user.getDocumentInfo()}" class="form-control" id="document" name = "document"></td>
                            </tr><!--SUM-->
                        </table>

                        <div class="form-group ">
                            <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                <fmt:message key="create_order"/>
                            </button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/navigation/footer.jsp" %>
<script src="scripts/CreateOrderScripts.js"></script>
</body>
</html>
