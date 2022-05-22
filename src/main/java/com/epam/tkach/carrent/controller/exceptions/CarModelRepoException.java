package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarModelRepoException extends Exception{
    private static final Logger logger = LogManager.getLogger(CarBrandRepoException.class);

    public CarModelRepoException() {
        logger.error("Car model exception was thrown");
    }

    public CarModelRepoException(String message) {
        super(message);
        logger.error("Car model exception was thrown", message);
    }

    public CarModelRepoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Car model exception was thrown", message, cause);
    }

    public CarModelRepoException(Throwable cause) {
        super(cause);
        logger.error("Car model exception was thrown", cause);
    }
}
