package com.epam.tkach.carrent.controller.command.manager;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CloseOrderWithDamage implements ICommand {
    private static final Logger logger = LogManager.getLogger(CloseOrderWithDamage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int id = RequestReader.readIntFromRequest(request, PageParameters.ID);
        String damage = RequestReader.readStringFromRequest(request, PageParameters.DAMAGE);
        double amount = RequestReader.readDoubleFromRequest(request, PageParameters.SUM);

        if (damage==null||damage.isEmpty()) return Path.prepareErrorPage(request, response, Messages.ERROR_EMPTY_DAMAGE);
        if (amount==0d) return Path.prepareErrorPage(request, response, Messages.ERROR_AMOUNT);

        try {
            OrderService.closeOrderWithDamage(id,damage,amount);
            return Path.prepareSuccessPage(request, response, null);
        } catch (OrderRepoException|CarRepoException|InvoiceRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
