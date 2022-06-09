package com.epam.tkach.carrent.controller.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TariffException extends Exception{
    private static final Logger logger = LogManager.getLogger(TariffException.class);

    public TariffException() {
        logger.error("Tariff exception was thrown");
    }

    public TariffException(String message) {
        super(message);
        logger.error("Tariff exception was thrown", message);
    }

    public TariffException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Tariff exception was thrown", message, cause);
    }

    public TariffException(Throwable cause) {
        super(cause);
    }

    public TariffException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("Tariff exception was thrown", message, cause);
    }
}
