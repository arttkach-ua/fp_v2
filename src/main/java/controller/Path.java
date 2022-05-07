package controller;

/**
 * Class contains all pages of web application
 * created for easier access to pages url
 * @author Tkach Artem
 */

public class Path {
    // Localization::: i18n
    public static final String LOCALE_NAME_RU = "ua";
    public static final String LOCALE_NAME_EN = "en";

    //pages
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE_ALL_USERS = "/WEB-INF/jsp/all_users.jsp";

    //admin commands

    //client commands

    //manager commands

    //common commands
    public static final String COMMAND_LOGIN = "controller?action=login";
    public static final String COMMAND_LOGOUT = "controller?action=logout";
}
