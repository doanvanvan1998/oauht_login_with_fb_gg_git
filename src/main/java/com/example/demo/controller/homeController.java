package com.example.demo.controller;

import com.example.demo.sercurity.auth.Custom0Auth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class homeController {

    @GetMapping("/list")
    public String homeUser(Authentication authentication, Model model, HttpSession session){
        Custom0Auth2User custom0Auth2User = null;
        DefaultOAuth2User oAuth2User = null;
        String name = "";

        try{
            custom0Auth2User = (Custom0Auth2User) authentication.getPrincipal();
            name = custom0Auth2User.getName();
            String token = (String) session.getAttribute("token");
            String id = (String) session.getAttribute("id");
            String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=large&access_token=" + token;
            model.addAttribute("imageUrl",imageUrl);
        }catch (Exception e){
            oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            name = oAuth2User.getAttribute("name");
            String imageUrl = oAuth2User.getAttribute("picture");
            model.addAttribute("imageUrl",imageUrl);
        }
        model.addAttribute("user",name);
        return "index";
    }
}
