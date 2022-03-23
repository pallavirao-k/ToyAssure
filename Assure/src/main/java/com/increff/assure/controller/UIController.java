package com.increff.assure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {


    @RequestMapping(value = "/ui/clients")
    public ModelAndView client() {
        return mav("client.html");
    }

    @RequestMapping(value = "/ui/customers")
    public ModelAndView customer() {
        return mav("customer.html");
    }

    @RequestMapping(value = "/ui/products")
    public ModelAndView product() {
        return mav("product.html");
    }

    protected ModelAndView mav(String page) {

        ModelAndView mav = new ModelAndView(page);
        return mav;
    }
}
