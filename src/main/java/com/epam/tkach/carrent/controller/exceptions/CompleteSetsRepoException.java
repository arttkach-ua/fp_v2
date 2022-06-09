package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompleteSetsRepoException extends Exception{
    private static final Logger logger = LogManager.getLogger(CompleteSetsRepoException.class);

    public CompleteSetsRepoException() {
        logger.error("Complete sets exception was thrown");
    }

    public CompleteSetsRepoException(String message) {
        super(message);
        logger.error("Complete sets exception was thrown", message);
    }

    public CompleteSetsRepoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Complete sets exception was thrown", message, cause);
    }

    public CompleteSetsRepoException(Throwable cause) {
        super(cause);
        logger.error("Complete sets exception was thrown", cause);
    }
}
