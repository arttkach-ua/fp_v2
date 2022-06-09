package com.epam.tkach.carrent.controller.command.admin.CompleteSets;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.command.admin.OpenEditCarPage;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.service.CarBrandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenNewCompleteSetPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenNewCompleteSetPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<CarBrand> CarBrandList = CarBrandService.getAll();
            request.setAttribute(PageParameters.CAR_BRAND_LIST, CarBrandList);
            request.setAttribute(PageParameters.FUEL_TYPES_LIST, FuelTypes.values());
            request.setAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
            request.setAttribute(PageParameters.BODY_STYLE_LIST, BodyStyles.values());
            return Path.PAGE_ADD_COMPLETE_SET;
        } catch (CarBrandRepoException ex) {
            logger.error(ex);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
