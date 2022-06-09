package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.InvoiceRepoException;
import com.epam.tkach.carrent.model.entity.Invoice;
import com.epam.tkach.carrent.model.repository.InvoiceRepoI;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class InvoiceRepoMySql implements InvoiceRepoI {
    @Override
    public boolean addNew(Invoice invoice) throws InvoiceRepoException {
        return false;
    }

    @Override
    public boolean update(Invoice invoice) throws InvoiceRepoException {
        return false;
    }

    @Override
    public boolean delete(Invoice invoice) throws InvoiceRepoException {
        return false;
    }

    @Override
    public int getCountInDB(LinkedHashMap<String, Object> filters) throws InvoiceRepoException {
        return 0;
    }

    @Override
    public List<Invoice> getListForPagination(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters, String orderBy) throws InvoiceRepoException {
        return null;
    }

    @Override
    public Optional<Invoice> getById(int id) throws InvoiceRepoException {
        return Optional.empty();
    }
}
