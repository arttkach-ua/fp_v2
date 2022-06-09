package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarRepoMySql;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.service.CarBrandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class OpenEditCarPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenEditCarPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        int id = RequestReader.readIntFromRequest(request, PageParameters.ID);

        try {
            CarRepoI repo = RepositoryFactory.getCarRepo();
            Optional<Car> carOpt = repo.getById(id);
            if (carOpt.isEmpty()) return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
            Car car = carOpt.get();
            setCarFieldsToRequest(request, car);
            List<CarBrand> brandList = CarBrandService.getAll();

            request.setAttribute(PageParameters.CAR_BRAND_LIST, brandList);
            request.setAttribute(PageParameters.FUEL_TYPES_LIST, FuelTypes.values());
            request.setAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
            request.setAttribute(PageParameters.BODY_STYLE_LIST, BodyStyles.values());
            return Path.PAGE_EDIT_CAR;
        } catch (CarRepoException | CarBrandRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }

    private void setCarFieldsToRequest(HttpServletRequest request, Car car){
        request.setAttribute(PageParameters.ID, car.getID());
        request.setAttribute(PageParameters.CAR_BRAND, car.getBrand());
        request.setAttribute(PageParameters.CAR_MODEL, car.getModel());
        request.setAttribute(PageParameters.YEAR, car.getGraduationYear());
        request.setAttribute(PageParameters.VIN_CODE, car.getVinCode());
        request.setAttribute(PageParameters.STATE_NUMBER, car.getStateNumber());
        //request.setAttribute(PageParameters.PRICE, car.getPrice());
    }
}
