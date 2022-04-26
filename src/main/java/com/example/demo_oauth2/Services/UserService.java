package com.example.demo_oauth2.Services;

import com.example.demo_oauth2.Entities.Provider;
import com.example.demo_oauth2.Entities.UserEntity;
import com.example.demo_oauth2.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void checkexistUser(String username, Provider e){
        UserEntity user = userRepository.findByUsername(username);
        if(user==null){
            user = new UserEntity();
            user.setUsername(username);
            user.setProvider(e);
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}
