package com.example.demo_oauth2.Securities.Oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private OAuth2User oAuth2User;

    public CustomOAuth2User(OAuth2User oAuth2User){
        this.oAuth2User = oAuth2User;
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
        if(oAuth2User.getAttribute("name")!= null){
            return oAuth2User.getAttribute("name");
        }else {
            return oAuth2User.getAttribute("login");
        }

    }

    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }

    public String getPicture(){
        if(oAuth2User.getAttribute("picture")!= null)
        return oAuth2User.getAttribute("picture").toString();
        else {
            return oAuth2User.getAttribute("avatar_url").toString();
        }
    }

}
