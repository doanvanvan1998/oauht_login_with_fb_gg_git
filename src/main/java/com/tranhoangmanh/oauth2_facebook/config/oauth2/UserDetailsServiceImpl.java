package com.tranhoangmanh.oauth2_facebook.config.oauth2;

import com.tranhoangmanh.oauth2_facebook.entity.Provider;
import com.tranhoangmanh.oauth2_facebook.entity.UserEntity;
import com.tranhoangmanh.oauth2_facebook.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

//Ko có vẫn chạy được
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsernameAndProvider(username, Provider.OTHER);
        if(user.isPresent()){
            throw new UsernameNotFoundException("Không thể tìm thấy");
        }
        return new MyUserDetail(user.get());
    }
}
