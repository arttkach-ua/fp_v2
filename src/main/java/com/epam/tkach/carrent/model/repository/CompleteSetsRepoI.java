package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.CompleteSetsRepoException;
import com.epam.tkach.carrent.model.entity.CompleteSet;

import java.util.List;
import java.util.Optional;

public interface CompleteSetsRepoI {
    int getCountInDb() throws CompleteSetsRepoException;

    List<CompleteSet> getListForPagination(int currentPage, int recordsPerPage) throws CompleteSetsRepoException;

    boolean addNew(CompleteSet completeSet) throws CompleteSetsRepoException;

    Optional<CompleteSet> getByID(int id) throws CompleteSetsRepoException;

    boolean update(CompleteSet completeSet) throws CompleteSetsRepoException;

    List<CompleteSet> findByCarModelId(int id) throws CompleteSetsRepoException;
}
