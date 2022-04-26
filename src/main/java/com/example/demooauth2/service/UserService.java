package com.example.demooauth2.service;

import com.example.demooauth2.entity.Provider;
import com.example.demooauth2.entity.UserEntity;
import com.example.demooauth2.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;

    public void checkExistUserOauth(String username, Provider provider){
        UserEntity existUser = iUserRepository.findByUsername(username);
        if (existUser == null){
            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setEnabled(true);
            user.setProvider(provider);
            iUserRepository.save(user);
        }
    }
}
