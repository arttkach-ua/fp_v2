package com.epam.tkach.carrent.controller.command.manager;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenCloseOrderPageWithDamage implements ICommand {

    private static final Logger logger = LogManager.getLogger(OpenCloseOrderPageWithDamage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int orderId = RequestReader.readIntFromRequest(request, PageParameters.ID);
        request.setAttribute(PageParameters.ID, orderId);
        return Path.PAGE_OPEN_CLOSE_ORDER_WITH_DAMAGE;
    }
}
