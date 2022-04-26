package com.example.demo.service;

import com.example.demo.entity.ProviderEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void checkexitsUserAuth(String username,ProviderEntity provider){

        UserEntity user = userRepository.findByUsernameAndProvider(username,provider);

        if(user == null || !user.getProvider().equals(provider)) {
            user = new UserEntity();
            user.setUsername(username);
            user.setProvider(provider);
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}
