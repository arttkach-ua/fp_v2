<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setBundle basename="localization"/>

<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>
<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--New car brand form -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="new_complete_set"/></div>
                <div class="card-body">

                    <form class="form-horizontal" method="POST" action="controller?action=addNewCompleteSet">

                        <!--Car brand-->
                        <div class="form-group">
                            <label for="car_brand" class="control-label cols-sm-2"><fmt:message key="car_brand"/></label>
                            <select onchange="setCarModels()" class="form-control" name="car_brand" id="car_brand">
                                <option  selected value=-1><fmt:message key="select_general_placeholder"/></option>
                                <c:forEach items="${carBrandsList}" var="carBrand">
                                    <option value=${carBrand.getID()}>${carBrand.getCarBrandName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <!--Car model-->
                        <div class="form-group">
                            <label for="car_model" class="control-label cols-sm-2"><fmt:message key="car_model"/></label>
                            <select class="form-control" name="car_model" id="car_model">
                                <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                                <c:forEach items="${carModelList}" var="carModel">
                                    <option value=${carModel.getID()}>${carModel.getModelName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <!--Body style-->
                        <div class="form-group">
                            <label for="body_style" class="control-label cols-sm-2"><fmt:message key="body_style"/></label>
                            <select class="form-control" name="body_style" id="body_style">
                                <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                                <c:forEach items="${bodyStyleList}" var="bodyStyle">
                                    <option value=${bodyStyle.getValue()}>
                                        <fmt:message key="${bodyStyle}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <!--Transmission-->
                        <div class="form-group">
                            <label for="transmission" class="control-label cols-sm-2"><fmt:message key="transmission"/></label>
                            <select class="form-control" name="transmission" id="transmission">
                                <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                                <c:forEach items="${transmissionList}" var="transmission">
                                    <option value=${transmission.getValue()}>
                                        <fmt:message key="${transmission}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <!--Fuel type-->
                        <div class="form-group">
                            <label for="fuel_type" class="control-label cols-sm-2"><fmt:message key="fuel_type"/></label>
                            <select class="form-control" name="fuel_type" id="fuel_type">
                                <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                                <c:forEach items="${fuelTypesList}" var="fuelType">
                                    <option value=${fuelType.getValue()}>
                                        <fmt:message key="${fuelType}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <!--Engine-->
                        <div class="form-group">
                            <label for="engine" class="control-label cols-sm-2"><fmt:message key="engine"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <input type="number" step="0.1" min="0.1" max="10" class="form-control" name="engine" id="engine" placeholder="<fmt:message key="engine.placeholder"/>" />
                                </div>
                            </div>
                        </div>

                        <div class="form-group ">
                            <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                <fmt:message key="add_complete_set"/>
                            </button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/navigation/footer.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.js"></script>
<script src="scripts/getCarModelsByBrand.js"></script>
</body>
</html>
