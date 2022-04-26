package com.example.demologinfacebookgoogle.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private  String token;
    private  String clienName;
    private OAuth2User oAuth2User;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClienName() {
        return clienName;
    }

    public void setClienName(String clienName) {
        this.clienName = clienName;
    }

    public OAuth2User getoAuth2User() {
        return oAuth2User;
    }

    public void setoAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public CustomOAuth2User(OAuth2User oAuth2User, String token, String clienName){

        this.oAuth2User=oAuth2User;
        this.token=token;
        this.clienName=clienName;
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
    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }

}
