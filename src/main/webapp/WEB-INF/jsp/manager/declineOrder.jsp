<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setBundle basename="localization"/>

<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>
<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--Decline order form -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="label.decline_order"/></div>
                <div class="card-body">
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
                            <td><fmt:message key="client"/></td>
                            <td>${order.getClient().getFullName()}</td>
                        </tr><!--Client-->
                        <tr>
                            <th scope="row">2</th>
                            <td><fmt:message key="car"/></td>
                            <td>${order.getCar().getDescription()}</td>
                        </tr><!--Car-->
                        </tbody>
                    </table>
                    <form class="form-horizontal" method="POST" action="controller?action=declineOrder">
                        <input type="hidden" name="id" id ="id" value="${id}"/>
                        <div class="form-group">
                            <label for="comment" class="cols-sm-2 control-label"><fmt:message key="comment"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="comment" id="comment" placeholder="<fmt:message key="comment.placeholder"/>" />
                                </div>
                            </div>
                        </div>

                        <div class="form-group ">
                            <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                <fmt:message key="label.decline_order"/>
                            </button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</html>

