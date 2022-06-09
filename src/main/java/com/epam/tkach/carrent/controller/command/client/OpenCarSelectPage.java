package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.command.common.Login;
import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.Transaction;
import com.epam.tkach.carrent.model.entity.enums.CarClass;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;
import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarRepoMySql;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.service.CarBrandService;
import com.epam.tkach.carrent.model.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OpenCarSelectPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenCarSelectPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {

            int carClassFilter =  RequestReader.readIntFromRequest(request, PageParameters.CAR_CLASS_FILTER);
            int carBrandFilter = RequestReader.readIntFromRequest(request, PageParameters.CAR_BRAND_FILTER);
            int transmissionFilter = RequestReader.readIntFromRequest(request, PageParameters.TRANSMISSION_FILTER);
            String sortBy = RequestReader.readStringFromRequest(request, PageParameters.SORT_FIELD);

            LinkedHashMap<String, Object> filters = new LinkedHashMap<>();
            filters.put("cars.available", true);
            if (carClassFilter != -1&&carClassFilter != 0){
                filters.put("cm.car_classes_id", carClassFilter);
                request.setAttribute(PageParameters.CAR_CLASS_FILTER, carClassFilter);
            }
            if (carBrandFilter != -1&&carBrandFilter != 0){
                filters.put("cars.brand_id", carBrandFilter);
                request.setAttribute(PageParameters.CAR_BRAND_FILTER, carBrandFilter);
            }
            if (transmissionFilter != -1&&transmissionFilter != 0){
                filters.put("cs.transmission_id", transmissionFilter);
                request.setAttribute(PageParameters.TRANSMISSION_FILTER, transmissionFilter);
            }

            int carBrandsCount = CarService.getCountInDB(filters);
            int currentPage = PaginationHelper.getCurrentPage(request);
            int recordsPerPage = 5;

            List<Car> carList = CarService.getList(currentPage, recordsPerPage, filters, CarService.getColumnNameToSort(sortBy));
            int nOfPages = PaginationHelper.getNoOfPages(carBrandsCount, recordsPerPage);

            request.setAttribute(PageParameters.SORT_FIELD, sortBy);
            request.setAttribute(PageParameters.SORT_LIST, CarService.getSortingList());
            request.setAttribute(PageParameters.CAR_BRAND_LIST, CarBrandService.getAll());
            request.setAttribute(PageParameters.TRANSMISSION_LIST, TransmissionTypes.values());
            request.setAttribute(PageParameters.CAR_CLASS_LIST, CarClass.values());
            request.setAttribute(PageParameters.CAR_LIST, carList);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_SELECT_CAR;
        } catch (CarRepoException | CarBrandRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
