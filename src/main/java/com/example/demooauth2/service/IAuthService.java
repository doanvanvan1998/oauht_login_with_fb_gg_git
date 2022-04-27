package com.example.demooauth2.service;

public interface IAuthService {
    void sendMailResetPassword(String mail, String token);
}
