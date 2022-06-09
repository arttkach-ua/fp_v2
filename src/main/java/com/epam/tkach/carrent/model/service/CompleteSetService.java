package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.command.admin.ShowTariffList;
import com.epam.tkach.carrent.controller.exceptions.CompleteSetsRepoException;
import com.epam.tkach.carrent.model.entity.CompleteSet;
import com.epam.tkach.carrent.model.repository.CompleteSetsRepoI;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CompleteSetService {
    private static final Logger logger = LogManager.getLogger(ShowTariffList.class);

    public static void addNew(CompleteSet completeSet) throws CompleteSetsRepoException {
        CompleteSetsRepoI repo = RepositoryFactory.getCompleteSetsRepo();
        repo.addNew(completeSet);
    }

    public static void update(CompleteSet completeSet) throws CompleteSetsRepoException {
        CompleteSetsRepoI repo = RepositoryFactory.getCompleteSetsRepo();
        repo.update(completeSet);
    }

    public static int getCountInDB() throws CompleteSetsRepoException {
        CompleteSetsRepoI repo = RepositoryFactory.getCompleteSetsRepo();
        return repo.getCountInDb();
    }

    public static List<CompleteSet> getListForPagination(int currentPage, int recordsPerPage) throws CompleteSetsRepoException {
        CompleteSetsRepoI repo = RepositoryFactory.getCompleteSetsRepo();
        return repo.getListForPagination(currentPage, recordsPerPage);
    }

    public static CompleteSet findById(int id) throws CompleteSetsRepoException {
        CompleteSetsRepoI repo = RepositoryFactory.getCompleteSetsRepo();
        Optional<CompleteSet> opt = repo.getByID(id);
        if (opt.isEmpty()) throw new CompleteSetsRepoException();
        return opt.get();
    }

    public static List<CompleteSet> findByCarModelId(int id) throws CompleteSetsRepoException{
        CompleteSetsRepoI repo = RepositoryFactory.getCompleteSetsRepo();
        return repo.findByCarModelId(id);
    }
}
