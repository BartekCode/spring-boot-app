package com.bartek.restApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("login")
    public String getLoginView(){
        return "login";
    }

    @GetMapping("/discoveriesSite")
    public String home(){
        return "discoveriesSite";
    }
}
