package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.model.entity.Invoice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Class user for operations with Invoice entity in DB
 */
public interface InvoiceRepoI {
    boolean addNew(Invoice invoice) throws InvoiceRepoException;

    boolean update(Invoice invoice) throws InvoiceRepoException;

    boolean delete(Invoice invoice) throws InvoiceRepoException;

    int getCountInDB(LinkedHashMap<String, Object> filters) throws InvoiceRepoException;

    List<Invoice> getListForPagination(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters, String orderBy) throws InvoiceRepoException;

    Optional<Invoice> getById(int id) throws InvoiceRepoException;
}
