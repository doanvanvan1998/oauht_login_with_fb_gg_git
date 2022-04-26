package com.example.demologinfacebookgoogle.controller;

import com.example.demologinfacebookgoogle.repository.UserRepository;
import com.example.demologinfacebookgoogle.security.auth.MyUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/list")
    public String homePage(){


        return "fragments";
    }

}
