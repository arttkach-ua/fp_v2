package com.epam.tkach.carrent.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class contains all pages of web application
 * created for easier access to pages url
 * @author Tkach Artem
 */

public class Path {
    private static final Logger logger = LogManager.getLogger(Path.class);
    // Localization::: i18n
    public static final String LOCALE_NAME_UA = "ua";
    public static final String LOCALE_NAME_EN = "en";

    //pages
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_LOGIN = "WEB-INF/jsp/common/login.jsp";
    public static final String PAGE_REGISTER = "WEB-INF/jsp/common/register.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE_SUCCESS = "/WEB-INF/jsp/success_page.jsp";
    public static final String PAGE_ALL_USERS = "/WEB-INF/jsp/admin/usersList.jsp";
    public static final String PAGE_ALL_CAR_BRANDS = "/WEB-INF/jsp/admin/carBrands.jsp";
    public static final String PAGE_ALL_CAR_MODELS = "/WEB-INF/jsp/admin/carModels.jsp";
    public static final String PAGE_ALL_CARS = "/WEB-INF/jsp/admin/allCars.jsp";
    public static final String PAGE_ALL_TARIFFS = "/WEB-INF/jsp/admin/tariffList.jsp";
    public static final String PAGE_ALL_COMPLETE_SETS = "/WEB-INF/jsp/admin/allCompleteSets.jsp";
    public static final String PAGE_ALL_ORDERS = "/WEB-INF/jsp/common/ordersList.jsp";
    public static final String PAGE_ALL_INVOICES = "/WEB-INF/jsp/common/invoiceList.jsp";
    public static final String PAGE_ADD_CAR_BRAND = "/WEB-INF/jsp/admin/newCarBrand.jsp";
    public static final String PAGE_ADD_CAR_MODEL = "/WEB-INF/jsp/admin/newCarModel.jsp";
    public static final String PAGE_ADD_CAR = "/WEB-INF/jsp/admin/newCar.jsp";
    public static final String PAGE_ADD_TARIFF = "/WEB-INF/jsp/admin/newTariff.jsp";
    public static final String PAGE_ADD_COMPLETE_SET = "/WEB-INF/jsp/admin/newCompleteSet.jsp";
    public static final String PAGE_EDIT_TARIFF = "/WEB-INF/jsp/admin/editTariff.jsp";
    public static final String PAGE_EDIT_COMPLETE_SET = "/WEB-INF/jsp/admin/editCompleteSet.jsp";
    public static final String PAGE_MY_PROGILE = "/WEB-INF/jsp/common/myProfile.jsp";
    public static final String PAGE_EDIT_CAR = "/WEB-INF/jsp/admin/editCar.jsp";
    public static final String PAGE_TOP_UP = "/WEB-INF/jsp/client/TopUp.jsp";
    public static final String PAGE_SELECT_CAR = "/WEB-INF/jsp/client/selectCarPage.jsp";
    public static final String PAGE_CREATE_ORDER = "/WEB-INF/jsp/client/createOrder.jsp";
    public static final String PAGE_DECLINE_ORDER = "/WEB-INF/jsp/manager/declineOrder.jsp";
    public static final String PAGE_OPEN_CLOSE_ORDER_WITH_DAMAGE = "/WEB-INF/jsp/manager/closeOrderWithDamage.jsp";


    //common commands
    public static final String COMMAND_LOGIN = "com/epam/tkach/carrent/controller";
    public static final String COMMAND_LOGOUT = "com/epam/tkach/carrent/controller";
    public static final String COMMAND_REDIRECT = "redirect";
    public static final String COMMAND_ALL_CAR_BRANDS = "controller?action=carBrands";
    public static final String COMMAND_ALL_ORDERS = "controller?action=ordersList";
    public static final String OPEN_ERROR_PAGE = "controller?action=openErrorPage";
    public static final String OPEN_SUCCESS_PAGE = "controller?action=openSuccessPage";

    /**
     * Method sets to request error message and returns error page
     * @param request - HttpServletRequest
     * @param errorMessage - message that will be shown to user
     * @return Error page
     */
    public static final String prepareErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMessage){
        try {
            response.sendRedirect(Path.OPEN_ERROR_PAGE);
            HttpSession session = request.getSession();
            session.setAttribute(PageParameters.ERRORS,errorMessage);
            return Path.COMMAND_REDIRECT;
        } catch (IOException e) {
            logger.error(e);
            return Path.OPEN_ERROR_PAGE;
        }
    }

    public static final String prepareErrorPage(HttpServletRequest request, HttpServletResponse response, ArrayList<String> errorsList){
        try {
            response.sendRedirect(Path.OPEN_ERROR_PAGE);
            HttpSession session = request.getSession();
            session.setAttribute(PageParameters.ERRORS,errorsList);
            return Path.COMMAND_REDIRECT;
        } catch (IOException e) {
            logger.error(e);
            return Path.OPEN_ERROR_PAGE;
        }
    }

    public static final String prepareSuccessPage(HttpServletRequest request, HttpServletResponse response, String errorMessage){
        try {
            response.sendRedirect(Path.OPEN_SUCCESS_PAGE);
            HttpSession session = request.getSession();
            session.setAttribute(PageParameters.ERRORS,errorMessage);
            return Path.COMMAND_REDIRECT;
        } catch (IOException e) {
            logger.error(e);
            return Path.OPEN_ERROR_PAGE;
        }
    }
}
