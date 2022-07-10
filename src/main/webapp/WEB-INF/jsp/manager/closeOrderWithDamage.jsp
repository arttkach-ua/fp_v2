<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setBundle basename="localization"/>

<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>
<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--Close with damage form -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="button.closeOrderWithDamage"/></div>
                <div class="card-body">

                    <form class="form-horizontal" method="POST" action="controller?action=closeOrderWithDamage">
                        <input type="hidden" name="id" id="id" value="${id}">
                        <div class="form-group">
                            <label for="damage" class="cols-sm-2 control-label"><fmt:message key="damage"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="damage" id="damage" placeholder="<fmt:message key="damage.placeholder"/>" />
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="sum" class="cols-sm-2 control-label"><fmt:message key="sum"/></label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <input type="number" step="0.1" class="form-control" name="sum" id="sum" placeholder="<fmt:message key="sum.placeholder"/>" />
                                </div>
                            </div>
                        </div>

                        <div class="form-group ">
                            <button type="submit" class="btn btn-primary btn-lg btn-block login-button">
                                <fmt:message key="button.closeOrderWithDamage"/>
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
