    <%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand" href="./">
                <img src="resourses/logo.png" width="40" height="40" class="d-inline-block align-top" alt="">
                <fmt:message key="title" />
            </a>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownCars" role="button" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
                <fmt:message key="navbar.orders" />
            </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="controller?action=myOrders"><fmt:message key="navbar.my_orders" /></a>
                        <a class="dropdown-item" href="controller?action=selectCar"><fmt:message key="navbar.new_order" /></a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="controller?action=carList"><fmt:message key="navbar.company_cars"/></a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="controller?action=myProfile"><fmt:message key="navbar.my_profile"/></a>
                </li>
            </ul>
                <div>
                    <span class="navbar-text ml-auto">My balance ${balance} UAH</span>
                    <a class="btn btn-outline-warning" href="controller?action=openTopUpPage" role="button">Top UP</a>
                </div>

            <div class="dropdown ml-auto">
                <button class="btn btn-outline-secondary btn-sm" type="button" id="dropdownMenuButton"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="material-icons">
                    language
                    </i>
                </button>
                <div class="dropdown-menu ml-auto" aria-labelledby="dropdownMenuButton">
                    <form class="form-inline" method="post" action="controller?action=i18n">
                        <button type="submit" name="ua" class="dropdown-item">Ukrainian</button>
                        <button type="submit" name="en" class="dropdown-item">English</button>
                    </form>
                </div>
            </div>
                <form class="form-inline my-2 my-lg-0" method="post" action="controller?action=logout">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="navbar.logout"/>
                    </button>
                </form>
            </div>
        </nav>

