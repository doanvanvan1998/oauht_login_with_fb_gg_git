package com.example.module_javaspringboot_day20_01_loginwithfbandgg.security.oauth2;

import com.example.module_javaspringboot_day20_01_loginwithfbandgg.entities.UserEntity;
import com.example.module_javaspringboot_day20_01_loginwithfbandgg.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserRepository iUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = iUserRepository.findByEmail(username);
        if (userEntity == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(userEntity);
    }
}
