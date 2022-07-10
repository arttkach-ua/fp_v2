<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<fmt:setBundle basename="localization"/>

<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>
<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--New car model list form -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="new_car_model"/></div>
                <div class="card-body">

                    <form class="form-horizontal" method="POST" action="controller?action=addNewCarModel">

                        <div class="input-group">
                            <label for="car_class" class="cols-lg-2 control-label"><fmt:message key="car_class"/></label>
                            <select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" id="car_class" name="car_class">
                                <c:forEach items="${carClasses}" var="carClass">
                                    <option value=${carClass.getValue()}>
                                        <fmt:message key="${carClass}"/>
                                    </option>
                                </c:forEach>
                            </select>

                        </div>

                        <div class="input-group">
                            <label for="car_brand" class="cols-lg-2 control-label"><fmt:message key="car_brand"/></label>
                            <select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" id="car_brand" name="car_brand">
                                <c:forEach items="${carBrandsList}" var="carBrand">
                                    <option value=${carBrand.getID()}>${carBrand.getCarBrandName()}</option>
                                </c:forEach>
                            </select>

                        </div>

                        <div class="form-group">
                            <label for="name" class="cols-sm-2 control-label"><fmt:message key="car_model"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="name" id="name" placeholder="<fmt:message key="car_model.placeholder"/>" />
                                </div>
                            </div>
                        </div>

                        <div class="form-group ">
                            <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                <fmt:message key="add_new_car_model"/>
                            </button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>


</form>>
<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</html>
