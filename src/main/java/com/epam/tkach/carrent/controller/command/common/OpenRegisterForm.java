package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.model.entity.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OpenRegisterForm implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenRegisterForm.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Role role = (Role)session.getAttribute(PageParameters.ROLE);
            if (role==Role.ADMIN) {
                request.setAttribute(PageParameters.ROLES_LIST,Role.values());
            }
        }
        return Path.PAGE_REGISTER;
    }
}
