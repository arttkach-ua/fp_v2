package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionException extends Exception{
    private static final Logger logger = LogManager.getLogger(TransactionException.class);

    public TransactionException() {
        logger.error("Transaction exception was thrown");
    }

    public TransactionException(String message) {
        super(message);
        logger.error("Transaction exception was thrown", message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Transaction exception was thrown", message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("Transaction exception was thrown", message, cause);
    }
}
