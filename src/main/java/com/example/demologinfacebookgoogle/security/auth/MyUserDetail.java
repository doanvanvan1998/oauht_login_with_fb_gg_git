package com.example.demologinfacebookgoogle.security.auth;

import com.example.demologinfacebookgoogle.entiy.RoleEntity;
import com.example.demologinfacebookgoogle.entiy.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyUserDetail implements UserDetails {
    UserEntity user;

    public MyUserDetail(UserEntity user) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Set<RoleEntity> roles=user.getRoles();
       List<SimpleGrantedAuthority> authorityList=new ArrayList<>();
        for (RoleEntity ro:roles) {
            authorityList.add(new SimpleGrantedAuthority(ro.getRolename()));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
