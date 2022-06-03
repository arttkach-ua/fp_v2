package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.PaginationHelper;
import com.epam.tkach.carrent.controller.Path;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenCarSelectPage implements ICommand {
    private static final Logger logger = LogManager.getLogger(OpenCarSelectPage.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            CarRepoI repo = RepositoryFactory.getCarRepo();
            CarBrandRepoI brandRepo = RepositoryFactory.getCarBrandRepo();

            int carBrandsCount = repo.getCountInDB();
            int currentPage = PaginationHelper.getCurrentPage(request);
            int recordsPerPage = 5;

            List<Car> carList = repo.getListForPagination(currentPage, recordsPerPage);
            int nOfPages = PaginationHelper.getNoOfPages(carBrandsCount, recordsPerPage);


            request.setAttribute(PageParameters.CAR_BRAND_LIST, brandRepo.getAll());
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
