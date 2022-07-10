package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.TransactionException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.Transaction;
import com.epam.tkach.carrent.model.service.NotificationService;
import com.epam.tkach.carrent.model.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TopUp implements ICommand {
    private static final Logger logger = LogManager.getLogger(TopUp.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            logger.error("Session error");
            return Path.prepareErrorPage(request,response, Messages.ERROR_SESSION_ERROR);
        }

        try {
            int userId = (int)session.getAttribute(PageParameters.ID);
            Transaction transaction = RequestReader.CreateTopUpTransactionFromRequest(request, userId);
            TransactionService.addNew(transaction);
            session.setAttribute(PageParameters.BALANCE, TransactionService.getUserBalance(userId));
            NotificationService.notifyAboutTopUp(transaction.getUser(), SessionHelper.getCurrentLocale(request), transaction.getSum());
            return Path.prepareSuccessPage(request, response, null);
        } catch (UserRepoException | TransactionException e) {
            logger.error(e);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
