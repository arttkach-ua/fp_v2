package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.model.repository.MySqlImp.CarRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

public class SessionHelper {
    private static final Logger logger = LogManager.getLogger(CarRepoMySql.class);

    public static int getUserIdFromSession(HttpServletRequest request) throws AuthenticationException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            logger.error("Session error");
            throw new AuthenticationException();
        }
        return (int)session.getAttribute(PageParameters.ID);
    }
    public static String getCurrentLocale(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String defaultValue = "ua";
        if (session == null) {
            logger.error("Session error");
            return defaultValue;
        }
        String locale = (String) Config.get(session, "javax.servlet.jsp.jstl.fmt.locale");
        if (locale!=null){
            return locale;
        }else{
            return defaultValue;
        }
    }
}
