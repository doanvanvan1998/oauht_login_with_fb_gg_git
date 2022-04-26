package com.example.demologinfacebookgoogle.service;

import com.example.demologinfacebookgoogle.entiy.Provider;
import com.example.demologinfacebookgoogle.entiy.UserEntity;
import com.example.demologinfacebookgoogle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void checkExistUserAuth(String username,Provider provider){
        UserEntity existsuser=userRepository.findByUsername(username);
        if (existsuser==null){
            UserEntity userEntity= new UserEntity();
            userEntity.setUsername(username);
            userEntity.setProvider(provider);
            userEntity.setEnable(true);
            userRepository.save(userEntity);
        }
    }
}
