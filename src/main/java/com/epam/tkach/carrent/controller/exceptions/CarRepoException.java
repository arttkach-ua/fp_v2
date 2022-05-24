package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarRepoException extends Exception{
    private static final Logger logger = LogManager.getLogger(CarRepoException.class);

    public CarRepoException() {
        logger.error("Car exception was thrown");
    }

    public CarRepoException(String message) {
        super(message);
        logger.error("Car exception was thrown", message);
    }

    public CarRepoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Car exception was thrown", message, cause);
    }

    public CarRepoException(Throwable cause) {
        super(cause);
        logger.error("Car exception was thrown", cause);
    }
}
