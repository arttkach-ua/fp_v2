package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.TransactionException;
import com.epam.tkach.carrent.model.entity.Transaction;

import java.sql.Connection;
import java.util.List;

public interface TransactionRepoI {
    boolean addNew(Transaction transaction) throws TransactionException;

    boolean addNew(Transaction transaction, Connection con) throws TransactionException;

    List<Transaction> getAll() throws TransactionException;

    double getUserBalance(int userId) throws TransactionException;

}
