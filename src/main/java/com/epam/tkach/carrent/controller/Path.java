package com.epam.tkach.carrent.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains all pages of web application
 * created for easier access to pages url
 * @author Tkach Artem
 */

public class Path {
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
    public static final String PAGE_ADD_CAR_BRAND = "/WEB-INF/jsp/admin/newCarBrand.jsp";
    public static final String PAGE_ADD_CAR_MODEL = "/WEB-INF/jsp/admin/newCarModel.jsp";
    public static final String PAGE_ADD_CAR = "/WEB-INF/jsp/admin/newCar.jsp";






    //admin commands

    //client commands

    //manager commands

    //common commands
    public static final String COMMAND_LOGIN = "com/epam/tkach/carrent/controller";
    public static final String COMMAND_LOGOUT = "com/epam/tkach/carrent/controller";

    /**
     * Method sets to request error message and returns error page
     * @param request - HttpServletRequest
     * @param errorMessage - message that will be shown to user
     * @return Error page
     */
    public static final String prepareErrorPage(HttpServletRequest request, String errorMessage){
        List<String> errorList = new ArrayList();
        errorList.add(errorMessage);
        request.setAttribute(PageParameters.ERRORS,errorList);
        return Path.PAGE_ERROR_PAGE;
    }
}
