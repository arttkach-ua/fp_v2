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
                <div class="card-header"><fmt:message key="new_car"/></div>
                <div class="card-body">
                    <form class="form-horizontal" method="POST" action="controller?action=addNewCar">
                        <!--Car brand-->
                        <div class="form-group">
                            <label for="car_brand" class="control-label cols-sm-2"><fmt:message key="car_brand"/></label>
                                <select  class="form-control" name="car_brand" id="car_brand" onchange="setCarModels(); setCompleteSets()">
                                    <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                                    <c:forEach items="${carBrandsList}" var="carBrand">
                                        <option value=${carBrand.getID()}>${carBrand.getCarBrandName()}</option>
                                    </c:forEach>
                                </select>
                        </div>
                        <!--Car model-->
                        <div class="form-group">
                            <label for="car_model" class="control-label cols-sm-2"><fmt:message key="car_model"/></label>
                            <select class="form-control" name="car_model" id="car_model" onchange="setCompleteSets()">
                                <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                            </select>
                        </div>
                        <!--Complete set-->
                        <div class="form-group">
                            <label for="complete_set" class="control-label cols-sm-2"><fmt:message key="completeSetName"/></label>
                            <select class="form-control" name="complete_set" id="complete_set">
                                <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                            </select>
                        </div>
                        <!--YEAR-->
                        <div class="form-group">
                            <label for="year" class="control-label cols-sm-2"><fmt:message key="year"/></label>
                            <input type="text" class="form-control" name="year" id="year" />
                        </div>

                        <!--State number-->
                        <div class="form-group">
                            <label for="state_number" class="control-label cols-sm-2"><fmt:message key="state_number"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="state_number" id="state_number" placeholder="<fmt:message key="state_number.placeholder"/>" />
                                </div>
                            </div>
                        </div>
                        <!--Vin code-->
                        <div class="form-group">
                            <label for="vin_code" class="control-label cols-sm-2"><fmt:message key="vin_code"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="vin_code" id="vin_code" placeholder="<fmt:message key="vin_code.placeholder"/>" />
                                </div>
                            </div>
                        </div>
                        <!--Tariff-->
                        <div class="form-group">
                            <label for="tariff" class="control-label cols-sm-2"><fmt:message key="tariff_name"/></label>
                            <select  class="form-control" name="tariff" id="tariff">
                                <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                                <c:forEach items="${tariffList}" var="tariff">
                                    <option value=${tariff.getID()}>${tariff.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <!--button add-->
                        <div class="form-group ">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <fmt:message key="add_new_car"/>
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
<script src="scripts/getCarModelsByBrand.js"></script>


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
