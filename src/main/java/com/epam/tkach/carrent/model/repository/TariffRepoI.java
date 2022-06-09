package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.model.entity.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffRepoI {
    boolean addNew(Tariff tariff) throws TariffException;
    boolean update(Tariff tariff) throws TariffException;
    List<Tariff> getListForPagination(int currentPage, int recordsPerPage) throws TariffException;
    int getCountInDB() throws TariffException;
    Optional<Tariff> findById(int Id) throws TariffException;
    List<Tariff> getAll() throws TariffException;
}
