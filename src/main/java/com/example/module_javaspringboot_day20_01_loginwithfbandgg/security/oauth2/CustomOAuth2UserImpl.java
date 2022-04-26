package com.example.module_javaspringboot_day20_01_loginwithfbandgg.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2UserImpl implements OAuth2User {
    private OAuth2User oAuth2User;
    private UserRequest userRequest;

    public CustomOAuth2UserImpl(OAuth2User oAuth2User, UserRequest userRequest) {
        this.oAuth2User = oAuth2User;
        this.userRequest = userRequest;
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
    public String getID() {
        return oAuth2User.getAttribute("id");
    }
    public String getClientName() {
        return this.userRequest.getClientName();
    }
    public String getAccessToken() {
        return this.userRequest.getAccessToken();
    }
    public String getTokenType() {
        return this.userRequest.getTokenType();
    }
    public String getImage() {
        return oAuth2User.getAttribute("picture");
    }
    public String getSubGG(){
        return oAuth2User.getAttribute("sub");
    }


}
