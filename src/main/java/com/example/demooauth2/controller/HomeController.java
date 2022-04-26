package com.example.demooauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/list")
    public String homePage(){
        return "index";
    }

    @GetMapping("/user")
    public String userPage(){
        return "user";
    }
}
