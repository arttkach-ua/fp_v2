package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenNewCarPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenNewCarPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CarBrandRepoI brandRepo = new CarBrandRepoMySql();
        List<CarBrand> brandList = null;
        try {
            brandList = brandRepo.getAll();
        } catch (CarBrandRepoException ex) {
            logger.error(ex);
        }
        request.setAttribute(PageParameters.CAR_BRAND_LIST, brandList);
        request.setAttribute(PageParameters.FUEL_TYPES_LIST, FuelTypes.values());
        request.setAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
        return Path.PAGE_ADD_CAR;
    }
}
