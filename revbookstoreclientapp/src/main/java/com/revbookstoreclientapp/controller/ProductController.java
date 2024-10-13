package com.revbookstoreclientapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @GetMapping("/view")
    public ModelAndView viewProducts() {
        // Create a ModelAndView object
        ModelAndView modelAndView = new ModelAndView();

        // Set the view name to the JSP page
        modelAndView.setViewName("viewProducts");

        // You can add attributes to the model if needed
        // modelAndView.addObject("attributeName", attributeValue);

        return modelAndView;
    }
    @GetMapping("/review")
    public ModelAndView ReviewProducts() {
        // Create a ModelAndView object
        ModelAndView modelAndView = new ModelAndView();

        // Set the view name to the JSP page
        modelAndView.setViewName("reviewreq");

        // You can add attributes to the model if needed
        // modelAndView.addObject("attributeName", attributeValue);

        return modelAndView;
    }
}
