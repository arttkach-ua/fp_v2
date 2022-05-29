<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<fmt:setBundle basename="localization"/>

<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<main class="my-form">
    <div class="cotainer">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header"><fmt:message key="register"/></div>
                    <div class="card-body">
                        <form class="form-with-validation" name="my-form" action="controller?action=register" method="post" onsubmit="return Valid()">
                            <div class="form-group row">
                                <label for="email" class="col-md-4 col-form-label text-md-right"><fmt:message key="email" /></label>
                                <div class="col-md-6">
                                    <input type="text" id="email" class="form-control" name="email"
                                           placeholder="<fmt:message key="email.placeholder"/>" onchange="email_validate(this.value);">
                                    <div class="status" id="status"></div>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="first_name" class="col-md-4 col-form-label text-md-right"><fmt:message key="first_name" /></label>
                                <div class="col-md-6">
                                    <input type="text" id="first_name" class="form-control" name="first_name"
                                           placeholder="<fmt:message key="first_name.placeholder"/>">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="second_name" class="col-md-4 col-form-label text-md-right"><fmt:message key="second_name" /></label>
                                <div class="col-md-6">
                                    <input type="text" id="second_name" class="form-control" name="second_name"
                                           placeholder="<fmt:message key="second_name.placeholder"/>">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="phone_number" class="col-md-4 col-form-label text-md-right"><fmt:message key="phone_number" /></label>
                                <div class="col-md-6">
                                    <input type="tel" id="phone_number" class="form-control" name="phone_number"
                                           placeholder="<fmt:message key="phone_number.placeholder"/>">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="document" class="col-md-4 col-form-label text-md-right"><fmt:message key="document_info" /></label>
                                <div class="col-md-6">
                                    <input type="text" id="document" class="form-control" name="document"
                                           placeholder="<fmt:message key="document_info.placeholder"/>">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="password" class="col-md-4 col-form-label text-md-right"><fmt:message key="pass" /></label>
                                <div class="col-md-6">
                                    <input type="password" id="password" class="form-control" name="password"
                                           placeholder="<fmt:message key="pass.placeholder"/>">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="confirm_password" class="col-md-4 col-form-label text-md-right"><fmt:message key="pass_confirm" /></label>
                                <div class="col-md-6">
                                    <input type="password" id="confirm_password" class="form-control" name="confirm_password"
                                           placeholder="<fmt:message key="pass_confirm.placeholder"/>">
                                    <span id="confirmMessagePass" class="confirmMessage"></span>
                                </div>

                            </div>

                            <c:choose>
                                <c:when test = "${sessionScope.role == 'ADMIN'}">
                                    <div class="form-group row">
                                        <label for="role" class="col-md-4 col-form-label text-md-right"><fmt:message key="role"/></label>
                                        <div class="col-md-6">
                                            <select class="form-control" id="role" name="role">
                                                <c:forEach items="${rolesList}" var="role">
                                                    <option value=${role.getValue()}>
                                                        <fmt:message key="${role}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                    </div>
                                </c:when>
                            </c:choose>

                            <div class = "form-group row">
                                <div class="form-check form-check-inline col-md-4">
                                    <input class="form-check-input" type="checkbox" id="receiveNotifications" value="receiveNotifications" name = "receiveNotifications">
                                    <label class="form-check-label" for="receiveNotifications"><fmt:message key="receive_notifications"/></label>
                                </div>
                            </div>

                            <div class="col-md-6 offset-md-4">
                                <button type="submit" class="btn btn-primary" id="submit">
                                    <fmt:message key="register"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</main>
<script src="scripts/RegisterValidation.js"></script>
</body>
</body>
</html>
