package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvoiceRepoException extends Exception{
    private static final Logger logger = LogManager.getLogger(InvoiceRepoException.class);

    public InvoiceRepoException() {
        logger.error("Invoice repo exception was thrown");
    }

    public InvoiceRepoException(String message) {
        super(message);
        logger.error("Invoice repo exception was thrown", message);
    }

    public InvoiceRepoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Invoice repo exception was thrown", message, cause);
    }

    public InvoiceRepoException(Throwable cause) {
        super(cause);
    }

    public InvoiceRepoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("Invoice repo exception was thrown", message, cause);
    }
}
