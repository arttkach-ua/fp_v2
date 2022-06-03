package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderRepoException extends Exception{
    private static final Logger logger = LogManager.getLogger(OrderRepoException.class);

    public OrderRepoException() {
        logger.error("Order exception was thrown");
    }

    public OrderRepoException(String message) {
        super(message);
        logger.error("Order exception was thrown", message);
    }

    public OrderRepoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Order exception was thrown", message, cause);
    }

    public OrderRepoException(Throwable cause) {
        super(cause);
    }

    public OrderRepoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("Order exception was thrown", message, cause);
    }
}
