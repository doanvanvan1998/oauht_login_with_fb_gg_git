package com.tranhoangmanh.oauth2_facebook.config.oauth2;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class CustomOAuth2UserGoogle implements OAuth2User {
    private final OAuth2User oAuth2User;
    private String token;
    private String clientId;
    private String clientSecret;

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

    public String getId(){
        return oAuth2User.getAttribute("sub");
    }

    public String getUrlAvatarURL(){
        return oAuth2User.getAttribute("picture");
    }

    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }

    public String getLocale(){
        return oAuth2User.getAttribute("locale");
    }

    public String getLastName(){
        return oAuth2User.getAttribute("given_name");
    }

    public String getFamilyName(){
        return oAuth2User.getAttribute("family_name");
    }

    public Boolean emailVerified(){
        return oAuth2User.getAttribute("email_verified");
    }
}
