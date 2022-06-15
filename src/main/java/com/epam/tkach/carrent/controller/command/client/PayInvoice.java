package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.controller.exceptions.TransactionException;
import com.epam.tkach.carrent.model.service.InvoiceService;
import com.epam.tkach.carrent.model.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class PayInvoice implements ICommand {
    private static final Logger logger = LogManager.getLogger(PayInvoice.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            logger.error("Session error");
            return Path.prepareErrorPage(request,response, Messages.ERROR_SESSION_ERROR);
        }

        try {
            int userId = (int)session.getAttribute(PageParameters.ID);
            int invoiceId = RequestReader.readIntFromRequest(request, PageParameters.ID);
            InvoiceService.pay(invoiceId);
            session.setAttribute(PageParameters.BALANCE, TransactionService.getUserBalance(userId));
            return Path.prepareSuccessPage(request, response, null);
        } catch (InvoiceRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, response, e.getMessage());
        } catch (TransactionException | OrderRepoException | SQLException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
