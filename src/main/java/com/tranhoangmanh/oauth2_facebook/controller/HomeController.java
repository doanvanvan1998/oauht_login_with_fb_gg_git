package com.tranhoangmanh.oauth2_facebook.controller;

import com.tranhoangmanh.oauth2_facebook.config.oauth2.CustomOAuth2User;
import com.tranhoangmanh.oauth2_facebook.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.*;
import java.net.URL;

@Controller
public class HomeController {

    @GetMapping("/list")
    public String list(HttpSession session, Model model) {
        UserResponse userResponse = (UserResponse) session.getAttribute("userResponse");
        model.addAttribute("userResponse", userResponse);

        String clientName = (String) session.getAttribute("clientName");
        if(clientName.equals("Facebook")){
            loginByFacebook(userResponse);
            return "login_success";
        }else if (clientName.equals("Google")) {
            loginByGoogle(userResponse);
            return "login_success_google";
        }
        return "not-found";
    }

    private void loginByGoogle(UserResponse userResponse) {

    }

    private void loginByFacebook(UserResponse userResponse) {
        try{
            URL url = new URL(userResponse.getImageUrl());
            InputStream inputStream = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))){
                outputStream.write(buffer, 0, n);
            }
            outputStream.close();
            inputStream.close();
            byte[] response = outputStream.toByteArray();
            FileOutputStream fos = new FileOutputStream("D:\\avatar_facebook\\" + userResponse.getId() + ".jpg");
            fos.write(response);
            fos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
