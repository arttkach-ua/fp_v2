package com.epam.tkach.carrent.controller.command.manager;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenDeclineOrderPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenDeclineOrderPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            int orderId = RequestReader.readIntFromRequest(request, PageParameters.ID);
            request.setAttribute(PageParameters.ID, orderId);
            request.setAttribute("order", OrderService.findById(orderId));
        } catch (OrderRepoException e) {
            e.printStackTrace();
        }
        return Path.PAGE_DECLINE_ORDER;
    }
}
