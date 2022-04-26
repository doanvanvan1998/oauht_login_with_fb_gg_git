package com.example.module_javaspringboot_day20_01_loginwithfbandgg.controller;

import com.example.module_javaspringboot_day20_01_loginwithfbandgg.entities.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/list")
    public String getList(){
        return "list";
    }
    @GetMapping("/index")
    public String getIndex(Model model, HttpSession session, HttpServletRequest request){
//        if (session.getAttribute("User") !=null){
//            UserEntity userEntity = (UserEntity) session.getAttribute("User");
//            model.addAttribute("UserLogin",userEntity);
//        }else {
//            model.addAttribute("UserLogin",new UserEntity());
//        }
//        model.addAttribute("UserLogin",request.getAttribute("ImageUser"));
        return "index";
    }
}
