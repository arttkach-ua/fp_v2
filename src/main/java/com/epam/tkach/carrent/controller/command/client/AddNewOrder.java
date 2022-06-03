package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.OrderStatuses;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarRepoMySql;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.repository.UserRepoI;
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
            CarRepoI repo = RepositoryFactory.getCarRepo();
            Optional<Car> carOpt = repo.getById(carId);
            if (carOpt.isEmpty()) throw new CarRepoException();
            Car car = carOpt.get();
            //user
            int userId = getUserIdFromSession(request);
            UserRepoI userRepo = RepositoryFactory.getUserRepo();
            Optional<User> userOpt = userRepo.findByID(userId);
            if (userOpt.isEmpty()) throw new UserRepoException();
            User client = userOpt.get();

            Order order = new Order();
            order.setClient(client);
            order.setCar(car);
            order.setDaysCount(RequestReader.readIntFromRequest(request, PageParameters.DAYS_COUNT));
            order.setPrice(car.getPrice());
            order.setStatus(OrderStatuses.NEW);



        } catch (CarRepoException | UserRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        } catch (AuthenticationException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_SESSION_ERROR);
        }

        return Path.prepareErrorPage(request, Messages.ERROR_PAGE_IN_PROGRESS);
    }

    private int getUserIdFromSession(HttpServletRequest request) throws AuthenticationException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            logger.error("Session error");
            throw new AuthenticationException();
        }
        return (int)session.getAttribute(PageParameters.ID);
    }

}
