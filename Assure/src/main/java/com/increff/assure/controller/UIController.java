package com.increff.assure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {


    @RequestMapping(value = "/ui/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    protected ModelAndView mav(String page) {

        ModelAndView mav = new ModelAndView(page);
        return mav;
    }
}
