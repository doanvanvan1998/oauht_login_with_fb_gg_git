package com.example.demo_oauth2.Securities;

import com.example.demo_oauth2.Entities.RoleEntity;
import com.example.demo_oauth2.Entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyUserDetail implements UserDetails {
    UserEntity userEntity;

    public MyUserDetail(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleEntity> roles = userEntity.getRoleEntities();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole_name()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
