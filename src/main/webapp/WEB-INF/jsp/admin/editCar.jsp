<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setBundle basename="localization"/>

<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>
<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--New car form -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="edit_car"/></div>
                <div class="card-body">
                    <form class="form-horizontal" method="POST" action="controller?action=updateCar">
                        <!--ID-->
                        <div class="form-group">
                            <label for="id" class="control-label cols-sm-2" >ID</label>
                            <input type="text" readonly class="form-control cols-sm-4" name="id" id="id" value="${id}"/>
                        </div>
                        <!--Car brand-->
                        <div class="form-group">
                            <label for="car_brand" class="control-label cols-sm-2"><fmt:message key="car_brand"/></label>
                                <select class="form-control cols-sm-4" name="car_brand" id="car_brand">
                                    <option selected value="${car_brand.getID()}">${car_brand.getCarBrandName()}</option>
                                    <c:forEach items="${carBrandsList}" var="carBrandItem">
                                        <option value=${carBrandItem.getID()}>${carBrandItem.getCarBrandName()}</option>
                                    </c:forEach>
                                </select>
                        </div>
                        <!--Car model-->
                        <div class="form-group">
                            <label for="car_model" class="control-label cols-sm-2"><fmt:message key="car_model"/></label>
                            <select class="form-control cols-sm-4" name="car_model" id="car_model">
                                <option selected value="${car_model.getID()}">${car_model.getModelName()}</option>
                                <c:forEach items="${carModelList}" var="carModelItem">
                                    <option value=${carModelItem.getID()}>${carModelItem.getModelName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <!--YEAR-->
                        <div class="form-group">
                            <label for="year" class="control-label cols-sm-2"><fmt:message key="year"/></label>
                            <input type="text" class="form-control cols-sm-4" name="year" id="year" value="${year}"/>
                        </div>
                        <!--Body style-->
                        <div class="form-group">
                            <label for="body_style" class="control-label cols-sm-2"><fmt:message key="body_style"/></label>
                            <select class="form-control cols-sm-4" name="body_style" id="body_style">
                                <option selected value="${body_style.getValue()}"><fmt:message key="${body_style}"/></option>
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
                            <select class="form-control cols-sm-4" name="transmission" id="transmission">
                                <option selected value="${transmission.getValue()}"><fmt:message key="${transmission}"/></option>
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
                            <select class="form-control cols-sm-4" name="fuel_type" id="fuel_type">
                                <option selected value="${fuel_type.getValue()}"><fmt:message key="${fuel_type}"/></option>
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
                            <div class="cols-sm-4">
                                <div class="input-group">
                                    <input type="number" value="${engine}" step="0.1" min="0.1" max="10" class="form-control" name="engine" id="engine" placeholder="<fmt:message key="engine.placeholder"/>" />
                                </div>
                            </div>
                        </div>
                        <!--State number-->
                        <div class="form-group">
                            <label for="state_number" class="control-label cols-sm-2"><fmt:message key="state_number"/></label>
                            <div class="cols-sm-4">
                                <div class="input-group">
                                    <input type="text"  class="form-control" value="${state_number}" name="state_number" id="state_number" placeholder="<fmt:message key="state_number.placeholder"/>" />
                                </div>
                            </div>
                        </div>
                        <!--Vin code-->
                        <div class="form-group">
                            <label for="vin_code" class="control-label cols-sm-2"><fmt:message key="vin_code"/></label>
                            <div class="cols-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" value="${vin_code}" name="vin_code" id="vin_code" placeholder="<fmt:message key="vin_code.placeholder"/>" />
                                </div>
                            </div>
                        </div>
                        <!--Price per day-->
                        <div class="form-group">
                            <label for="price" class="control-label cols-sm-2"><fmt:message key="price_per_day"/></label>
                            <div class="cols-sm-4">
                                <div class="input-group">
                                    <input type="number" step="0.1" min="0.1" value="${price}" class="form-control" name="price" id="price" placeholder="<fmt:message key="price.placeholder"/>" />
                                </div>
                            </div>
                        </div>
                        <!--button add-->
                        <div class="form-group ">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <fmt:message key="save_changes"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!--Car model dropDown script-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.js"></script>
<%--<script src="/WEB-INF/scripts/newCarScripts.js"></script>--%>
<script type="text/javascript">$(document).ready(function () {
    $('#car_brand').change(function () {
        $('#car_model').find('option').remove();
        $('#car_model').append('<option><fmt:message key="select_general_placeholder"/></option>');

        let brand_id = $('#car_brand').val();
        let data = {
            operation: "car_model",
            id: brand_id
        };
        $.ajax({
            url: "TestServlet",
            method: "GET",
            data: data,
            success: function (data, textStatus, jqXHR) {
                console.log(data);
                let obj = $.parseJSON(data);
                $.each(obj, function (key, value) {
                    $('#car_model').append('<option value="' + value.ID + '">' + value.modelName + '</option>')
                });
                //$('select').formSelect();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#car_model').append('<option>Car model Unavailable</option>');
            },
            cache: false
        });
    });
});</script>

<!--DatePicker script-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/js/bootstrap-datepicker.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet">
<script type="text/javascript">
    $(document).ready(function(){
        $("#year").datepicker({
            format: "yyyy",
            viewMode: "years",
            minViewMode: "years",
            autoclose:true
        });
    })
</script>
<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</html>
