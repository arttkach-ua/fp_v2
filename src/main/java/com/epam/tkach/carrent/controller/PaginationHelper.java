package com.epam.tkach.carrent.controller;

import javax.servlet.http.HttpServletRequest;

public class PaginationHelper {
    public static int getCurrentPage(HttpServletRequest request){
        int currentPage = 1;
        if (request.getParameter(PageParameters.CURRENT_PAGE) != null) {
            currentPage = RequestReader.readIntFromRequest(request, PageParameters.CURRENT_PAGE);
        }
        return currentPage;
    }

    public static int getNoOfPages(int countOfRecords, int recordsPerPage){
        if (recordsPerPage==0) return 1;

        int nOfPages = countOfRecords / recordsPerPage;
        if (countOfRecords % recordsPerPage > 0) {
            nOfPages++;
        }
        return nOfPages;
    }
}
