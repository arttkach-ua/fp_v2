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
import java.io.IOException;

public class DeclineOrder implements ICommand {
    private static final Logger logger = LogManager.getLogger(DeclineOrder.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String comment = RequestReader.readStringFromRequest(request,"comment");
            if (comment==null||comment.isEmpty()){
                Path.prepareErrorPage(request, response,Messages.ERROR_EMPTY_COMMENT);
            }
            int orderId = RequestReader.readIntFromRequest(request, PageParameters.ID);
            OrderService.declineOrder(orderId, comment);
            response.sendRedirect(Path.COMMAND_ALL_ORDERS);

            return Path.COMMAND_REDIRECT;
        } catch (OrderRepoException | CarRepoException | IOException e) {
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }

    }
}
