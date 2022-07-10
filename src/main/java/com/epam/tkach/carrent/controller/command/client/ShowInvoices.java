package com.epam.tkach.carrent.controller.command.client;

import com.epam.tkach.carrent.controller.*;
import com.epam.tkach.carrent.controller.command.ICommand;
import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.Invoice;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.Role;
import com.epam.tkach.carrent.model.service.InvoiceService;
import com.epam.tkach.carrent.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ShowInvoices implements ICommand {
    private static final Logger logger = LogManager.getLogger(ShowInvoices.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String paidFilter = RequestReader.readStringFromRequest(request, PageParameters.PAID_FILTER);
            LinkedHashMap<String, Object> filters = new LinkedHashMap<>();
            int userId = SessionHelper.getUserIdFromSession(request);
            String sortBy = RequestReader.readStringFromRequest(request, PageParameters.SORT_FIELD);
            User user = UserService.findByID(userId);
            if (user.getRole()== Role.CLIENT){
                //Нужен отбор по пользователю
                filters.put("client_id", userId);
            }

            if (paidFilter!=null&&!"-1".equals(paidFilter)&&!"".equals(paidFilter)){
                filters.put("invoices.paid", "true".equals(paidFilter));
                request.setAttribute(PageParameters.PAID_FILTER, "true".equals(paidFilter));
            }
            ArrayList<Boolean> paidList = new ArrayList<>();
            paidList.add(true);
            paidList.add(false);

            int countInDB = InvoiceService.getCountInDB(filters);
            int currentPage = PaginationHelper.getCurrentPage(request);
            int recordsPerPage = 5;
            List<Invoice> entityList = InvoiceService.getList(currentPage,recordsPerPage,filters,InvoiceService.getColumnNameToSort(sortBy));
            int nOfPages = PaginationHelper.getNoOfPages(countInDB,recordsPerPage);

            request.setAttribute(PageParameters.SORT_FIELD, sortBy);
            request.setAttribute(PageParameters.SORT_LIST, InvoiceService.getSortingList());
            request.setAttribute(PageParameters.PAID_LIST, paidList);
            request.setAttribute(PageParameters.ENTITY_LIST, entityList);
            request.setAttribute(PageParameters.NO_OF_PAGES, nOfPages);
            request.setAttribute(PageParameters.CURRENT_PAGE, currentPage);
            request.setAttribute(PageParameters.RECORD_PER_PAGE, recordsPerPage);
            return Path.PAGE_ALL_INVOICES;
        } catch (AuthenticationException | UserRepoException | InvoiceRepoException e) {
            logger.error(e);
            return Path.prepareErrorPage(request,response, Messages.ERROR_DATABASE_ERROR);
        }
    }
}
