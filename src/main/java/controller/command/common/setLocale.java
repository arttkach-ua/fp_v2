package controller.command.common;

import controller.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class setLocale implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession();
//
//        String fmtLocale = "javax.servlet.jsp.jstl.fmt.locale";
//        String defaultLocale = "defaultLocale";
//
//        if (request.getParameter("ua") != null) {
//            Config.set(session, fmtLocale, Path.LOCALE_NAME_RU);
//            session.setAttribute(defaultLocale, "ru");
//
//        } else {
//            Config.set(session, fmtLocale, "en");
//            session.setAttribute(defaultLocale, Path.LOCALE_NAME_EN);
//        }
//
//        User user = (User) session.getAttribute("user");
//        return (user.getRoleId() == 1) ? "controller?action=users" : "controller?action=account";
        return "";
    }
}
