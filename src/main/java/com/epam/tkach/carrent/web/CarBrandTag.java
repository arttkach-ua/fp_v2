package com.epam.tkach.carrent.web;

import com.epam.tkach.carrent.model.entity.CarBrand;
import netscape.javascript.JSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.*;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CarBrandTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(CarBrandTag.class);
    private List<CarBrand> carBrandList;
    int rows;

    public void setCarBrandList(List<CarBrand> carBrandList) {
        this.carBrandList = carBrandList;
        if (carBrandList!=null) rows = carBrandList.size();
    }

    StringWriter sw = new StringWriter();

    @Override
    public int doStartTag() throws JspException {
        logger.debug("doStartTag");
        try{
            Locale locale;
            String local = (String) Config.find(pageContext, "javax.servlet.jsp.jstl.fmt.locale");
            if (local==null) {
                locale = new Locale("ua");
            }else{
                locale = new Locale(local);
            }
            ResourceBundle exampleBundle = ResourceBundle.getBundle("localization", locale);
            exampleBundle.getString("car_brand");
            JspWriter out = pageContext.getOut();
            out.write("<table class=\"table table-striped table-bordered table-sm\">");
            out.write("<tr><th>ID</th><th>"+exampleBundle.getString("car_brand")+"</th></tr>");
        } catch (IOException e) {
            logger.error(e);
            throw new JSException(e);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        try{
            JspWriter out = pageContext.getOut();

            for (CarBrand carBrand : carBrandList) {
                out.write("<tr>\n" +
                        " <td>" + carBrand.getID() + "</td>\n" +
                        " <td>" + carBrand.getCarBrandName() + "</td>\n" +
                        " </tr>");
            }
            out.write("</table>");
        } catch (IOException e) {
            logger.error(e);
        }
        return EVAL_BODY_INCLUDE;
    }
}
