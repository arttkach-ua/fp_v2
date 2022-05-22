package com.epam.tkach.carrent.controller.exceptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CarBrandRepoException extends Exception{
    private static final Logger logger = LogManager.getLogger(CarBrandRepoException.class);

    public CarBrandRepoException() {
        logger.error("Car brand exception was thrown");
    }

    public CarBrandRepoException(String message) {
        super(message);
        logger.error("Car brand exception was thrown", message);
    }

    public CarBrandRepoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Car brand exception was thrown", message, cause);
    }

    public CarBrandRepoException(Throwable cause) {
        super(cause);
    }

    public CarBrandRepoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("Car brand exception was thrown", message, cause);
    }
}
