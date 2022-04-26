package com.example.demo.sercurity.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Data
public class Custom0Auth2User implements OAuth2User {
    private OAuth2User oAuth2User;
    private String token;

    public Custom0Auth2User(OAuth2User oAuth2User,String token){
        this.oAuth2User = oAuth2User;
        this.token =token;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public String getUsername() {
        return oAuth2User.getAttribute("username");
    }

    public String getImageUrl(){

        return null;
    }
    public String getId(){
        return oAuth2User.getAttribute("id");
    }

}
