package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarRepoMySql;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import com.epam.tkach.carrent.model.service.CarService;
import com.epam.tkach.carrent.model.service.OrderService;
import com.epam.tkach.carrent.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AddNewOrder implements ICommand {
    private static final Logger logger = LogManager.getLogger(AddNewOrder.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            //car
            int carId = RequestReader.readIntFromRequest(request, PageParameters.ID);
            int userId = RequestReader.getUserIdFromSession(request);
            int daysCount = RequestReader.readIntFromRequest(request, PageParameters.DAYS_COUNT);
            String documents = RequestReader.readStringFromRequest(request, PageParameters.DOCUMENT);
            boolean withDriver = RequestReader.readBooleanFromRequest(request, PageParameters.WITH_DRIVER);

            Car car = CarService.getById(carId);
            User client = UserService.findByID(userId);

            Order order = OrderService.createNew(client, car, daysCount,documents,withDriver);
            OrderService.addNew(order);
            return Path.PAGE_SUCCESS;

        } catch (CarRepoException | UserRepoException | OrderRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        } catch (AuthenticationException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_SESSION_ERROR);
        }
    }
}
