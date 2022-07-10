<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<fmt:setBundle basename="localization"/>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/jsp/head.jsp" %>

<body>
<%@ include file="/WEB-INF/navigation/navbar.jsp" %>
<!--Car list form-->
<form action="controller?action=completeSetsList" method="get">
    <main class="m-3">
        <div class="row col-md-6">
            <table class="table table-striped table-bordered table-sm">
                <caption style="caption-side: top"><fmt:message key="navbar.completeSets"/></caption>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="car_brand"/></th>
                    <th scope="col"><fmt:message key="car_model"/></th>
                    <th scope="col"><fmt:message key="completeSetName"/></th>
                    <th scope="col"><fmt:message key="body_style"/></th>
                    <th scope="col"><fmt:message key="transmission"/></th>
                    <th scope="col"><fmt:message key="fuel_type"/></th>
                    <th scope="col"><fmt:message key="engine"/></th>
                </tr>

                <c:forEach items="${entityList}" var="entity">
                    <tr>
                        <td><a href="controller?action=editCompleteSet&id=${entity.getID()}">${entity.getID()}</a></td>
                        <td>${entity.getCarModel().getBrand().getCarBrandName()}</td>
                        <td>${entity.getCarModel().getModelName()}</td>
                        <td>${entity.getName()}</td>
                        <td><fmt:message key="${entity.getBodyStyle()}"/></td>
                        <td><fmt:message key="${entity.getTransmission()}"/></td>
                        <td><fmt:message key="${entity.getFuelType()}"/></td>
                        <td>${entity.getEngine()}</td>
                    </tr>
                </c:forEach>
            </table>

            <nav aria-label="Navigation for complete sets">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=completeSets&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}"><fmt:message key="pagination.previous"/></a>
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
                                                         href="controller?action=completeSets&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?action=completeSets&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}"><fmt:message key="pagination.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </main>
</form>

<a class="btn btn-primary" id = "add-new-model" href="controller?action=openNewCompleteSetPage" role="button"><fmt:message key="add_complete_set"/></a>

<%@ include file="/WEB-INF/navigation/footer.jsp" %>
</body>
</form>
</html>
