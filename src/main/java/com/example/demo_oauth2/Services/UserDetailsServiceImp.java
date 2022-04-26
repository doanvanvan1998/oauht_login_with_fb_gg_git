package com.example.demo_oauth2.Services;

import com.example.demo_oauth2.Entities.UserEntity;
import com.example.demo_oauth2.Repositories.UserRepository;
import com.example.demo_oauth2.Securities.MyUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("Could not found user");
        }
        return new MyUserDetail(user);
    }
}
