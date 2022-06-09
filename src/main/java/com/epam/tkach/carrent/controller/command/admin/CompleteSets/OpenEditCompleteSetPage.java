package com.epam.tkach.carrent.controller.command.admin.CompleteSets;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.RequestReader;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CompleteSetsRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.CompleteSet;
import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.service.CarBrandService;
import com.epam.tkach.carrent.model.service.CompleteSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenEditCompleteSetPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenEditCompleteSetPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            int id = RequestReader.readIntFromRequest(request, PageParameters.ID);
            CompleteSet entity = CompleteSetService.findById(id);
            List<CarBrand> CarBrandList = CarBrandService.getAll();
            request.setAttribute(PageParameters.ENTITY, entity);
            request.setAttribute(PageParameters.CAR_BRAND_LIST, CarBrandList);
            request.setAttribute(PageParameters.FUEL_TYPES_LIST, FuelTypes.values());
            request.setAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
            request.setAttribute(PageParameters.BODY_STYLE_LIST, BodyStyles.values());
            return Path.PAGE_EDIT_COMPLETE_SET;
        } catch (CompleteSetsRepoException | CarBrandRepoException e) {
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }

    }
}
