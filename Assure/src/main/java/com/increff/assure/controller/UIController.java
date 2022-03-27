package com.increff.assure.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

    @Value("${app.baseUrl}")
    private String baseUrl;

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

    @RequestMapping(value = "/ui/bins")
    public ModelAndView bin() {
        return mav("bin.html");
    }

    @RequestMapping(value = "/ui/channels")
    public ModelAndView channel() {
        return mav("channel.html");
    }

    @RequestMapping(value = "/ui/orders")
    public ModelAndView order() {
        return mav("order.html");
    }

    protected ModelAndView mav(String page) {

        ModelAndView mav = new ModelAndView(page);
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }
}
