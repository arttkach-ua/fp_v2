package com.epam.tkach.carrent.controller.filters;


import javax.servlet.*;
import org.apache.log4j.Logger;
import java.io.IOException;


public class EncodingFilter implements Filter {

    private static final Logger loggger = Logger.getLogger(EncodingFilter.class);

    private String requestEncoding;
    private String responseEncoding;
    //private String encoding;

    public void init(FilterConfig config) throws ServletException {
        requestEncoding = config.getInitParameter("requestEncoding");
        responseEncoding = config.getInitParameter("responseEncoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        request.setCharacterEncoding(requestEncoding);
        response.setCharacterEncoding(responseEncoding);
        response.setContentType("text/html;charset=UTF-8");

        loggger.debug("After - Request encoding: " + request.getCharacterEncoding());
        next.doFilter(request, response);
    }

    public void destroy(){
    }
}