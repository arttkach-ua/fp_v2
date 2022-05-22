package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarClassesRepoException extends Exception{
    private static final Logger logger = LogManager.getLogger(CarClassesRepoException.class);

    public CarClassesRepoException() {
        logger.error("Car classes exception was thrown");
    }

    public CarClassesRepoException(String message) {
        super(message);
        logger.error("Car classes exception was thrown", message);
    }

    public CarClassesRepoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Car classes exception was thrown", message, cause);
    }

    public CarClassesRepoException(Throwable cause) {
        super(cause);
        logger.error("Car classes exception was thrown", cause);
    }

}
