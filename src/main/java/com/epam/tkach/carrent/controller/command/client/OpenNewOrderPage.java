package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.service.CarService;
import com.epam.tkach.carrent.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OpenNewOrderPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenNewOrderPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            //setting car
            int carId = RequestReader.readIntFromRequest(request, PageParameters.ID);

            HttpSession session = request.getSession(false);
            if (session == null) {
                logger.error("Session error");
                return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
            }
            int userId = (int)session.getAttribute(PageParameters.ID);

            request.setAttribute("user", UserService.findByID(userId));
            request.setAttribute("car", CarService.getById(carId));
            return Path.PAGE_CREATE_ORDER;
        } catch (CarRepoException | UserRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
