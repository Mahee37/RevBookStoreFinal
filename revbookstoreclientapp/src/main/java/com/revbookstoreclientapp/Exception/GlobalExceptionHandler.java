package com.revbookstoreclientapp.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSellersFoundException.class)
    public ModelAndView handleNoSellersFoundException(NoSellersFoundException ex) {
        logger.error("No sellers found exception: {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }
    
    @ExceptionHandler(NoProductsFoundException.class)
    public ModelAndView handleNoProductsFoundException(NoProductsFoundException ex) {
        logger.error("No products found exception: {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }
}
