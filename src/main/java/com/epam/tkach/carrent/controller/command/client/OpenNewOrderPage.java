package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.command.common.Login;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class OpenNewOrderPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenNewOrderPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            //setting car
            int carId = RequestReader.readIntFromRequest(request, PageParameters.ID);
            CarRepoI carRepo= RepositoryFactory.getCarRepo();
            Optional<Car> carOpt = carRepo.getById(carId);
            if (carOpt.isEmpty()) throw new CarRepoException("Car not found");
            //Setting user
            HttpSession session = request.getSession(false);
            if (session == null) {
                logger.error("Session error");
                return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
            }
            int userId = (int)session.getAttribute(PageParameters.ID);
            UserRepoI userRepo = RepositoryFactory.getUserRepo();
            Optional<User> userOpt = userRepo.findByID(userId);
            if (userOpt.isEmpty()) throw new UserRepoException("User not found");
            request.setAttribute("user", userOpt.get());
            request.setAttribute("car", carOpt.get());
            return Path.PAGE_CREATE_ORDER;
        } catch (CarRepoException | UserRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
