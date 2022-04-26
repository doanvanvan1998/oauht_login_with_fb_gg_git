package com.example.demooauth2.security.oauth;

import com.example.demooauth2.entity.UserEntity;
import com.example.demooauth2.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = iUserRepository.findByUsername(username);
        if (userEntity == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetail(userEntity);
    }
}
