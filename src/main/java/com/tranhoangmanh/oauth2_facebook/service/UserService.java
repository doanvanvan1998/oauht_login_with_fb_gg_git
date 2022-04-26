package com.tranhoangmanh.oauth2_facebook.service;

import com.tranhoangmanh.oauth2_facebook.entity.Provider;
import com.tranhoangmanh.oauth2_facebook.entity.UserEntity;
import com.tranhoangmanh.oauth2_facebook.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;

    public void checkExistUserOAuth(String userName, String clientName){
        Optional<UserEntity> optionalUserEntity = Optional.empty();
        if(clientName.equalsIgnoreCase("Facebook")){
            optionalUserEntity = userRepository.findByUsernameAndProvider(userName, Provider.FACEBOOK);
        }else if(clientName.equalsIgnoreCase("Google")){
            optionalUserEntity = userRepository.findByUsernameAndProvider(userName, Provider.GOOGLE);
        }else {
            optionalUserEntity = userRepository.findByUsernameAndProvider(userName, Provider.OTHER);
        }
        if(!optionalUserEntity.isPresent()){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            if(clientName.equals("Google")) {
                userEntity.setProvider(Provider.GOOGLE);
            }else if (clientName.equals("Facebook")){
                userEntity.setProvider(Provider.FACEBOOK);
            }else {
                userEntity.setProvider(Provider.OTHER);
            }
            userRepository.save(userEntity);
        }
    }
}
