package com.epam.tkach.carrent.controller.command.common;

import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenSuccessPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenSuccessPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Path.PAGE_SUCCESS;
    }
}
