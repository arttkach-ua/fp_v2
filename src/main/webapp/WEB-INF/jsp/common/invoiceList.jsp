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
                <input hidden name = "action" id="action" value="invoiceList"/>
                <!--Paid filter-->
                <div class="form-group">
                    <label for="paid_filter" class="control-label"><fmt:message key="paid"/></label>
                    <select class="form-control" id="paid_filter" name="paid_filter">
                        <option selected value=-1><fmt:message key="select_general_placeholder"/></option>
                        <c:forEach items="${paidList}" var="entity">
                            <option ${paid_filter!=null&&paid_filter eq entity ? 'selected' : ''} value=${entity}><fmt:message key="${entity}"/></option>
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
<!--Invoice list form-->
<form action="controller?action=invoiceList" method="get">
    <main class="m-3">
        <div class="row col-md-6">
            <table class="table table-striped table-bordered table-sm">
                <caption style="caption-side: top"><fmt:message key="navbar.invoices"/></caption>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="client"/></th>
                    <th scope="col"><fmt:message key="order_number"/></th>
                    <th scope="col"><fmt:message key="date"/></th>
                    <th scope="col"><fmt:message key="type"/></th>
                    <th scope="col"><fmt:message key="car"/></th>
                    <th scope="col"><fmt:message key="sum"/></th>
                    <th scope="col"><fmt:message key="description"/></th>
                    <th scope="col"><fmt:message key="paid"/></th>
                    <th scope="col"></th>
                </tr>

                <c:forEach items="${entityList}" var="entity">
                    <tr class=${entity.isPaid() ? "table-success" : "table-light"}>
                        <td>${entity.getID()}</td>
                        <td>${entity.getClient().getFullName()}</td>
                        <td>${entity.getOrder().getID()}</td>
                        <td>${entity.getFormattedDate()}</td>
                        <td><fmt:message key="${entity.getType()}"/></td>
                        <td>${entity.getOrder().getCar().getDescription()}</td>
                        <td>${entity.getAmount()}</td>
                        <td>${entity.getDescription()}</td>
                        <td><fmt:message key="${entity.isPaid()}"/></td>

                        <td>
                            <c:if test = "${!entity.isPaid()&&sessionScope.role == 'CLIENT'}">
                                <a class="btn btn-outline-success" href="controller?action=payInvoice&id=${entity.getID()}" role="button"><fmt:message key="button.pay_invoice"/></a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <nav aria-label="Navigation for cars">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=invoiceList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&sort_field=${sort_field}&paid_filter=${paid_filter}"><fmt:message key="pagination.previous"/></a>
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
                                                         href="controller?action=invoiceList&recordsPerPage=${recordsPerPage}&currentPage=${i}&sort_field=${sort_field}&paid_filter=${paid_filter}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=invoiceList&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&sort_field=${sort_field}&paid_filter=${paid_filter}"><fmt:message key="pagination.next"/></a>
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
