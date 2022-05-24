package com.epam.tkach.carrent.controller.filters;


import javax.servlet.*;
import org.apache.log4j.Logger;
import java.io.IOException;


public class EncodingFilter implements Filter {

    private static final Logger loggger = Logger.getLogger(EncodingFilter.class);

    private String requestEncoding;
    private String responseEncoding;

    public void init(FilterConfig config) throws ServletException {
        requestEncoding = config.getInitParameter("requestEncoding");
        responseEncoding = config.getInitParameter("responseEncoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain net)
            throws IOException, ServletException {
        request.setCharacterEncoding(requestEncoding);
        response.setCharacterEncoding(responseEncoding);
        loggger.debug("After - Request encoding: " + request.getCharacterEncoding());
        net.doFilter(request, response);
    }

    public void destroy(){
    }
}