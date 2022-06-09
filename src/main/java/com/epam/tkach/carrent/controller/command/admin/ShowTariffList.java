package com.epam.tkach.carrent.controller.command.admin;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.PaginationHelper;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CarModelRepoException;
import com.epam.tkach.carrent.controller.exceptions.CompleteSetsRepoException;
import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.entity.CompleteSet;
import com.epam.tkach.carrent.model.entity.Tariff;
import com.epam.tkach.carrent.model.repository.CarModelRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarModelRepoMySql;
import com.epam.tkach.carrent.model.repository.MySqlImp.TariffRepoMySql;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.repository.TariffRepoI;
import com.epam.tkach.carrent.model.service.CompleteSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTariffList implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowTariffList.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            TariffRepoI repo = RepositoryFactory.getTariffRepo();
            int countInDB = repo.getCountInDB();
            int currentPage = PaginationHelper.getCurrentPage(request);
            int recordsPerPage = 5;


            List<Tariff> listOfTariffs = repo.getListForPagination(currentPage, recordsPerPage);
            int nOfPages = PaginationHelper.getNoOfPages(countInDB,recordsPerPage);

            request.setAttribute(PageParameters.TARIFF_LIST, listOfTariffs);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_ALL_TARIFFS;
        } catch (TariffException ex) {
            logger.error(ex);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
