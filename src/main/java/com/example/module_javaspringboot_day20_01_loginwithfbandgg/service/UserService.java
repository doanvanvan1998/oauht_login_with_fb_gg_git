package com.example.module_javaspringboot_day20_01_loginwithfbandgg.service;

import com.example.module_javaspringboot_day20_01_loginwithfbandgg.security.config.AuthProvider;
import com.example.module_javaspringboot_day20_01_loginwithfbandgg.entities.UserEntity;
import com.example.module_javaspringboot_day20_01_loginwithfbandgg.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {
    @Autowired
    IUserRepository iUserRepository;

    public void checkUserExistOauth2(String email) {
        UserEntity userEntity = iUserRepository.findByEmail(email);
        if (userEntity == null) {
            UserEntity newUser = new UserEntity();
            newUser.setId(null);
            newUser.setUsername("haiphong");
            newUser.setEmail(email);
            newUser.setEnabled(true);
            newUser.setAuthProvider(AuthProvider.FACEBOOK);
            iUserRepository.save(newUser);
        }

    }

    public void save(UserEntity userEntity) {
        iUserRepository.save(userEntity);
    }




    public void downloadImage(String url,String fileLocalPath){
        try(InputStream inputStream = URI.create(url).toURL().openStream()){
            Files.copy(inputStream, Paths.get(fileLocalPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
