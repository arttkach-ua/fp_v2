package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

public class I18N implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String fmtLocale = "javax.servlet.jsp.jstl.fmt.locale";
        String defaultLocale = "defaultLocale";

        if (request.getParameter("ua") != null) {
            Config.set(session, fmtLocale, Path.LOCALE_NAME_UA);
            session.setAttribute(defaultLocale, "ru");

        } else {
            Config.set(session, fmtLocale, "en");
            session.setAttribute(defaultLocale, Path.LOCALE_NAME_EN);
        }
        return "";
    }
}
