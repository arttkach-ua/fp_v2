package com.epam.tkach.carrent.controller.command.manager;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.enums.InvoiceTypes;
import com.epam.tkach.carrent.model.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.model.service.InvoiceService;
import com.epam.tkach.carrent.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmOrder implements ICommand {
    private static final Logger logger = LogManager.getLogger(ConfirmOrder.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            int orderId = RequestReader.readIntFromRequest(request, PageParameters.ID);
            OrderService.approveOrder(orderId);
            InvoiceService.createNewAndSaveInDB(orderId, InvoiceTypes.RENT,0, null);
            return Path.prepareSuccessPage(request, response, null);
        } catch (OrderRepoException | InvoiceRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
