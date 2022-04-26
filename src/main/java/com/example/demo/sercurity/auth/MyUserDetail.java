package com.example.demo.sercurity.auth;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class MyUserDetail implements UserDetails {
    private UserEntity userEntity;

    public MyUserDetail(UserEntity user){
        this.userEntity = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleEntity> roles = userEntity.getRoles();
        return null;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
