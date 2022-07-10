package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.TransactionException;
import com.epam.tkach.carrent.model.entity.Transaction;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.repository.MySqlImp.TransactionRepoMySql;
import com.epam.tkach.carrent.model.repository.TransactionRepoI;

import java.sql.Connection;
import java.time.LocalDateTime;

public class TransactionService {
    /**
     * Adds new transaction in DB. Must be user in auto commit mode
     * @param transaction
     * @throws TransactionException
     */
    public static void addNew(Transaction transaction) throws TransactionException {
        TransactionRepoI repo = new TransactionRepoMySql();
        repo.addNew(transaction);
    }

    /**
     * Adds new transaction in DB. Must be user in manual commit mode
     * @param transaction
     * @param con
     * @throws TransactionException
     */
    public static void addNew(Transaction transaction, Connection con) throws TransactionException {
        TransactionRepoI repo = new TransactionRepoMySql();
        repo.addNew(transaction, con);
    }
    public static double getUserBalance(int userId) throws TransactionException {
        TransactionRepoI repo = new TransactionRepoMySql();
        return repo.getUserBalance(userId);
    }

    public static Transaction createPayment(User user, int invoiceId, double amount){
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Payment by invoice#" + invoiceId);
        transaction.setSum(amount*(-1));
        return transaction;
    }
}
