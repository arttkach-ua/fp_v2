package com.epam.tkach.carrent.controller;

import com.epam.tkach.carrent.controller.command.admin.AddNewCarBrand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Class used to get values from request and process errors if values are not correct
 * @author Tkach A.
 */
public class RequestReader {
    private static final Logger logger = LogManager.getLogger(RequestReader.class);

    public static int readIntFromRequest(HttpServletRequest request, String param){
        int value = 0;
        try{
            value = Integer.parseInt(request.getParameter(param));
        }catch (NumberFormatException ex){
            logger.error("Failed to read "+param, ex);
        }
        return value;
    }

    public static double readDoubleFromRequest(HttpServletRequest request, String param){
        double value = 0d;
        try{
            value = Double.parseDouble(request.getParameter(param));
        }catch (NumberFormatException ex){
            logger.error("Failed to read "+param, ex);
        }
        return value;
    }
}
