package com.example.demo.sercurity.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

public class Custom0Auth2User implements OAuth2User {
    private OAuth2User oAuth2User;

    public Custom0Auth2User(OAuth2User oAuth2User,String methodLogin){
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
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public String getUsername() {
        return oAuth2User.getAttribute("username");
    }

    public String getImageUrl(){
        if(oAuth2User.getAttributes().containsKey("picture")) {
            Map<String, Object> pictureObj = (Map<String, Object>) oAuth2User.getAttributes().get("picture");
            if(pictureObj.containsKey("data")) {
                Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")) {
                    return (String) dataObj.get("url");
                }
            }
        }
        return null;
    }

}
