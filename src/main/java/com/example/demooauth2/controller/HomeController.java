package com.example.demooauth2.controller;

import com.example.demooauth2.dto.UserDTO;
import com.example.demooauth2.entity.UserEntity;
import com.example.demooauth2.repository.IUserRepository;
import com.example.demooauth2.service.DefaultService;
import com.example.demooauth2.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @Autowired
    DefaultService defaultService;
    @Autowired
    UserService userService;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final String token = RandomString.make(70);

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @GetMapping("/user")
    public String userPage(){
        return "user";
    }

    @GetMapping("/sign-up")
    public String registerPage(Model model){
        model.addAttribute("user", new UserDTO());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String addNewUser(UserDTO userDTO, RedirectAttributes redirectAttributes){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        boolean addSuccess = userService.addNewUser(userDTO);
        if (!addSuccess){
            redirectAttributes.addFlashAttribute("message", "Email exist");
            return "redirect:/sign-up";
        }
        return "redirect:/login";

    }

    @GetMapping("/reset-password/check-email")
    public String checkEmail(){
        return "check-email";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(){
        return "mail-to-reset";
    }

    @PostMapping("/reset-password")
    public String sendEmail(@RequestParam("emailToReset") String email, RedirectAttributes redirectAttributes){
        boolean check = userService.checkExist(email);
        if (check){
            defaultService.sendMailResetPassword(email, token);
            userService.updateUserToken(email, token);
        } else {
            redirectAttributes.addFlashAttribute("message", "Email not exist");
            return "redirect:/reset-password";
        }
        return "redirect:/reset-password/check-email";
    }

    @GetMapping("/reset-password/reset")
    public String resetUserPasswordPage(@RequestParam("token") String token, Model model){
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password/reset")
    public String resetUserPassword(@RequestParam("token") String token, @RequestParam("pass") String password){
        UserEntity user = iUserRepository.findByToken(token);
        user.setPassword(passwordEncoder.encode(password));
        iUserRepository.save(user);
        return "redirect:/login";
    }
}
