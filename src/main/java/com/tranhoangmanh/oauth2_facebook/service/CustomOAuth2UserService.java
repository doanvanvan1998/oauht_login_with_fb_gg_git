package com.tranhoangmanh.oauth2_facebook.service;

import com.tranhoangmanh.oauth2_facebook.config.oauth2.CustomOAuth2User;
import com.tranhoangmanh.oauth2_facebook.config.oauth2.CustomOAuth2UserGoogle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class  CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        if(clientName.equalsIgnoreCase("facebook")){
            return new CustomOAuth2User(user, userRequest.getAccessToken().getTokenValue().toString());
        }else if(clientName.equalsIgnoreCase("google")){
            String token = userRequest.getAccessToken().getTokenValue().toString();
            String clientId = userRequest.getClientRegistration().getClientId();
            String clientSecret = userRequest.getClientRegistration().getClientId();
            return new CustomOAuth2UserGoogle(user, token, clientId, clientSecret);
        }
        throw new OAuth2AuthenticationException("Không tìm thấy dịch vụ phù hợp");
    }
}
