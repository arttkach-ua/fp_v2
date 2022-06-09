package com.epam.tkach.carrent.controller.command.admin.CompleteSets;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.controller.PageParameters;
import com.epam.tkach.carrent.controller.PaginationHelper;
import com.epam.tkach.carrent.controller.Path;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.CompleteSetsRepoException;
import com.epam.tkach.carrent.model.entity.CompleteSet;
import com.epam.tkach.carrent.model.service.CompleteSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCompleteSets implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowCompleteSets.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            int countInDB = CompleteSetService.getCountInDB();
            int currentPage = PaginationHelper.getCurrentPage(request);
            int recordsPerPage = 5;

            List<CompleteSet> entityList = CompleteSetService.getListForPagination(currentPage, recordsPerPage);
            int nOfPages = PaginationHelper.getNoOfPages(countInDB,recordsPerPage);
            logger.debug("no of pages" + nOfPages);
            request.setAttribute(PageParameters.ENTITY_LIST, entityList);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_ALL_COMPLETE_SETS;
        } catch (CompleteSetsRepoException ex) {
            logger.error(ex);
            return Path.prepareErrorPage(request, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
