package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.Tariff;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import com.epam.tkach.carrent.model.service.CarBrandService;
import com.epam.tkach.carrent.model.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.List;

public class OpenNewCarPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenNewCarPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CarBrand> brandList = CarBrandService.getAll();
            List<Tariff> tariffList = TariffService.getAll();
            request.setAttribute(PageParameters.TARIFF_LIST, tariffList);
            request.setAttribute(PageParameters.CAR_BRAND_LIST, brandList);
            return Path.PAGE_ADD_CAR;
        } catch (CarBrandRepoException | TariffException e) {
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }

    }
}
