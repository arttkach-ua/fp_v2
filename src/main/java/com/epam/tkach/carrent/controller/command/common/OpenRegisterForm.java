package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.model.entity.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OpenRegisterForm implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Role role = (Role)session.getAttribute("role");
            if (role==Role.ADMIN) {
                request.setAttribute("rolesList",Role.values());
            }
        }
        return Path.PAGE_REGISTER;
    }
}
