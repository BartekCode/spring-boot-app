package com.bartek.restApi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)    //adnotacja by stworzyc kolejnosc , jak damy Highest to nasz filtr bedzie sie odbywał przed innymi filtrami
@Component //by mogl sie nim zajac Spring
public class LoggerFilter implements Filter {

   private final static Logger logger = LoggerFactory.getLogger(LoggerFilter.class); //tworzymy logger z slf4j, dla naszej klasy

    @Override //metoda pobiera 3 parametry req resp i łańcuchj wszystkich filtrów(musimy request puscic dalej zatrzymac itp)
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest){ //jezeli ządanie jest żadaniem Http
            var httpRequest =(HttpServletRequest) servletRequest; //tworzymy HttpServletRequest
            logger.info("[doFilter] "+httpRequest.getMethod()+" "+ httpRequest.getRequestURI()); //logujemy informacje
        }
        filterChain.doFilter(servletRequest,servletResponse); //by po filtrze poszło dalej
        logger.info("[doFilter] 2");
    }


}
