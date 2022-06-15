package com.epam.tkach.carrent.controller.command.manager;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CloseOrder implements ICommand {
    private static final Logger logger = LogManager.getLogger(CloseOrder.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int orderId = RequestReader.readIntFromRequest(request, PageParameters.ID);
        try {
            OrderService.closeOrder(orderId);
            return Path.prepareSuccessPage(request, response, null);
        } catch (OrderRepoException | CarRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
