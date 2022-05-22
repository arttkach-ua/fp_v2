package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarClassesRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.enums.CarClass;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarClassesRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarClassesRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenNewCarModelPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenNewCarModelPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CarBrandRepoI carBrandRepo = new CarBrandRepoMySql();
        CarClassesRepoI carClassRepo = new CarClassesRepoMySql();
        List<CarBrand> brandsList = null;
        List<CarClass> classesList = null;

        try {
            brandsList = carBrandRepo.getAll();
            classesList = carClassRepo.getAll();
        } catch (CarBrandRepoException| CarClassesRepoException ex) {
            logger.error("Error in OpenNewCarModelPage:::execute method", ex);
        }
        request.setAttribute("carClasses",classesList);
        request.setAttribute("carBrandsList", brandsList);
        return Path.PAGE_ADD_CAR_MODEL;
    }
}
