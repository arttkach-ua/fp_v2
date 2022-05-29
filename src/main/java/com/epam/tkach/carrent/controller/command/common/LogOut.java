package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.tkach.carrent.controller.command.admin.AddNewCarModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogOut implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewCarModel.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        logger.debug("Logout finished");
        return Path.PAGE_LOGIN;
    }
}
