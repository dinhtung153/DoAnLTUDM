package com.mht2.doan.ecommerce;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

    @RequestMapping("/home")
    public String home() {
        return "index";
    }

    @RequestMapping("/add-category")
    public String home1() {
        return "add-product";
    }

    @RequestMapping("/blank")
    public String blank() {
        return "pages-blank";
    }

    @RequestMapping("/login")
    public String login() {
        return "pages-login";
    }

    @RequestMapping("/register")
    public String register() {
        return "pages-register";
    }
}
