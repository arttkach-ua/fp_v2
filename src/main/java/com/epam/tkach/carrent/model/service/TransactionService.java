package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.TransactionException;
import com.epam.tkach.carrent.model.entity.Transaction;
import com.epam.tkach.carrent.model.repository.MySqlImp.TransactionRepoMySql;
import com.epam.tkach.carrent.model.repository.TransactionRepoI;

public class TransactionService {
    public static void addNew(Transaction transaction) throws TransactionException {
        TransactionRepoI repo = new TransactionRepoMySql();
        repo.addNew(transaction);
    }
    public static double getUserBalance(int userId) throws TransactionException {
        TransactionRepoI repo = new TransactionRepoMySql();
        return repo.getUserBalance(userId);

    }
}
