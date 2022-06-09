package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.TariffException;
import com.epam.tkach.carrent.model.entity.Tariff;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.repository.TariffRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class TariffService {
    private static final Logger logger = LogManager.getLogger(TariffService.class);

    public void addNew(Tariff tariff) throws TariffException {
        TariffRepoI repo = RepositoryFactory.getTariffRepo();
        repo.addNew(tariff);
    }
    public static void update(Tariff tariff) throws TariffException{
        TariffRepoI repo = RepositoryFactory.getTariffRepo();
        repo.update(tariff);
    }
    public static Tariff findById(int id) throws TariffException {
        TariffRepoI repo = RepositoryFactory.getTariffRepo();
        Optional<Tariff> opt = repo.findById(id);
        if (opt.isEmpty()) throw new TariffException();
        return opt.get();
    }
    public static List<Tariff> getAll() throws TariffException{
        TariffRepoI repo = RepositoryFactory.getTariffRepo();
        return repo.getAll();
    }
}
