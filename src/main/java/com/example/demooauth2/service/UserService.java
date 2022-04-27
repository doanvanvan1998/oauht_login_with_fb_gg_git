package com.example.demooauth2.service;

import com.example.demooauth2.dto.UserDTO;
import com.example.demooauth2.entity.Provider;
import com.example.demooauth2.entity.UserEntity;
import com.example.demooauth2.repository.IRoleRepository;
import com.example.demooauth2.repository.IUserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IRoleRepository iRoleRepository;

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

    public boolean addNewUser(UserDTO userDTO){
        UserEntity existUser = iUserRepository.findByUsername(userDTO.getUsername());
        if (existUser == null){
            UserEntity user = new UserEntity();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setRoles(Set.of(iRoleRepository.getById(1)));
            user.setEnabled(true);
            user.setProvider(Provider.LOCAL);
            iUserRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkExist(String username){
        UserEntity existUser = iUserRepository.findByUsername(username);
        if (existUser == null){
            return false;
        } else {
            return true;
        }
    }

    public void updateUserToken(String email, String token){
        UserEntity user = iUserRepository.findByUsername(email);
        user.setToken(token);
        iUserRepository.save(user);
    }
}
