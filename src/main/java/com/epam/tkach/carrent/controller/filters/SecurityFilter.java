package com.epam.tkach.carrent.controller.filters;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.model.entity.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

@WebFilter("/*")
public class SecurityFilter implements Filter{
    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);
    private static Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        logger.debug("Filter initialization starts");
//        // roles
        accessMap.put(Role.ADMIN, getAdminCommands());
        accessMap.put(Role.CLIENT, getClientCommands());
        accessMap.put(Role.MANAGER, getManagerCommands());
//        logger.trace("Access map --> " + accessMap);
//
          //commons = myAsList(filterConfig.getInitParameter("common"));
       initCommons();
//        logger.trace("Common commands --> " + commons);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Filter starts");
        if (accessAllowed(servletRequest)) {
            logger.debug("Access allowed. Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessages = "You do not have permission to access the requested resource";
            servletRequest.setAttribute(PageParameters.ERRORS, Messages.ACCESS_DENIED);

            logger.trace("Set the request attribute: errorMessage --> " + errorMessages);

            servletRequest.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("action");
        if (commandName == null || commandName.isEmpty()) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return commons.contains(commandName);
        }

        Role role = (Role)session.getAttribute("role");

        logger.debug("role:::" + role);
        if (role == null) {
            return commons.contains(commandName);
        }


        return commons.contains(commandName) || accessMap.get(role).contains(commandName);
    }

    private void initCommons() {
        commons.add("openLoginForm");
        commons.add("openRegisterForm");
        commons.add("login");
        commons.add("logout");
        commons.add("register");
        commons.add("i18n");
        commons.add("redirect");
        commons.add("carList");
        commons.add("myProfile");
        commons.add("updateProfile");
        commons.add("openSuccessPage");
        commons.add("openErrorPage");
    }
    
    private ArrayList<String> getAdminCommands(){
        //admin commands
        ArrayList<String> commands = new ArrayList(); 
        commands.add("usersList");
        commands.add("carBrands");
        commands.add("carModels");
        commands.add("tariffsList");
        commands.add("openNewCarBrandPage");
        commands.add("openNewCarModelPage");
        commands.add("openNewCarPage");
        commands.add("addNewCarBrand");
        commands.add("addNewCarModel");
        commands.add("openNewTariffPage");
        commands.add("addNewCar");
        commands.add("addNewTariff");
        commands.add("setUserBlock");
        commands.add("editCar");
        commands.add("updateCar");
        commands.add("editTariff");
        commands.add("updateTariff");
        commands.add("completeSets");
        commands.add("openNewCompleteSetPage");
        commands.add("addNewCompleteSet");
        commands.add("editCompleteSet");
        commands.add("updateCompleteSet");
        return commands;
    }

    private ArrayList<String> getManagerCommands(){
        ArrayList<String> commands = new ArrayList();
        commands.add("carBrands");
        commands.add("carModels");
        commands.add("tariffsList");
        commands.add("completeSets");
        commands.add("ConfirmOrder");
        commands.add("OpenDeclineOrderPage");
        commands.add("declineOrder");
        commands.add("closeOrder");
        commands.add("closeOrderWithDamege");
        commands.add("openErrorPage");
        commands.add("openSuccessPage");
        commands.add("closeOrder");
        commands.add("closeOrderWithDamage");
        commands.add("openCloseOrderWithDamage");
        commands.add("ordersList");
        commands.add("invoiceList");
        return commands;
    }
    private ArrayList<String> getClientCommands(){
        ArrayList<String> commands = new ArrayList();
        commands.add("openTopUpPage");
        commands.add("TopUp");
        commands.add("selectCar");
        commands.add("OpenNewOrderPage");
        commands.add("addNewOrder");
        commands.add("ordersList");
        commands.add("invoiceList");
        commands.add("payInvoice");
        return commands;
    }
}
