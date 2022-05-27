package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepoException extends Exception{
    private static final Logger logger = LogManager.getLogger(UserRepoException.class);

    public UserRepoException() {
        logger.error("User repo exception was thrown");
    }

    public UserRepoException(String message) {
        super(message);
        logger.error("User repo exception was thrown", message);
    }

    public UserRepoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("User repo exception was thrown", message, cause);
    }

    public UserRepoException(Throwable cause) {
        super(cause);
        logger.error("User repo exception was thrown", cause);
    }
}
