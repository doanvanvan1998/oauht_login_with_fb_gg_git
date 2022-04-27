package com.example.demooauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultService implements IAuthService{
    @Autowired
    MailService mailService;

    @Override
    public void sendMailResetPassword(String mail, String token) {
        String url = "mail to reset password";
        this.mailService.sendTextMail(
                MailService.MAIL_FROM,
                mail,
                "Test sent mail dang",
                token,
                null,
                url,
                "Mail to reset password:\n"
        );
    }
}
