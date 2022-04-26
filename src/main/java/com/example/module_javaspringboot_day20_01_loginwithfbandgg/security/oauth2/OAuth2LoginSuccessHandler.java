package com.example.module_javaspringboot_day20_01_loginwithfbandgg.security.oauth2;

import com.example.module_javaspringboot_day20_01_loginwithfbandgg.entities.RoleEntity;
import com.example.module_javaspringboot_day20_01_loginwithfbandgg.entities.UserEntity;
import com.example.module_javaspringboot_day20_01_loginwithfbandgg.repository.IRoleRepository;
import com.example.module_javaspringboot_day20_01_loginwithfbandgg.repository.IUserRepository;
import com.example.module_javaspringboot_day20_01_loginwithfbandgg.security.config.AuthProvider;
import com.example.module_javaspringboot_day20_01_loginwithfbandgg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    IRoleRepository iRoleRepository;
    @Autowired
    UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2UserImpl customOAuth2User = (CustomOAuth2UserImpl) authentication.getPrincipal();

        String email = customOAuth2User.getEmail();
        String username = customOAuth2User.getName();
        String clientName = customOAuth2User.getClientName();
        String idClient = customOAuth2User.getID();
        String accessToken = customOAuth2User.getAccessToken();
//        String tokenType = customOAuth2User.getTokenType();

        String urlImageFacebookOrGG ="";
        String alphaAndDigits ="";
        String userUpLoadPath = "/Users/admin/Desktop/Module_JavaSpingBoot/Module_JavaSpringBoot_Day20_01_LoginWithFBAndGG/src/main/resources/images";

        UserEntity userEntity = iUserRepository.findByEmail(email);

        AuthProvider authProvider = null;
        if (clientName.equals("Facebook")){
            urlImageFacebookOrGG = "https://graph.facebook.com/"+idClient+"/picture?type=large&access_token="+accessToken;
            alphaAndDigits = username.replaceAll("\\s+", "")+idClient;
            authProvider = AuthProvider.FACEBOOK;
        }else if (clientName.equals("Google")){
            urlImageFacebookOrGG = customOAuth2User.getImage();
            alphaAndDigits = username.replaceAll("\\s+", "")+customOAuth2User.getSubGG();
            authProvider = AuthProvider.GOOGLE;
        }else {
            authProvider = AuthProvider.LOCAL;
        }

        String imageName = alphaAndDigits.replaceAll("[^a-zA-Z0-9]+","")+".jpg";
        String pathImages = userUpLoadPath +"/"+imageName;

        RoleEntity roleEntity = iRoleRepository.getById(2L);
        List<RoleEntity> listRole = new ArrayList<>();
        listRole.add(roleEntity);

        if (userEntity == null){
            UserEntity newUser = new UserEntity();
            newUser.setId(null);
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setEnabled(true);
            newUser.setAuthProvider(authProvider);
            newUser.setRoleEntityList(listRole);
            newUser.setImage(imageName);
            userService.save(newUser);
        }else {
            userEntity.setAuthProvider(authProvider);
            userEntity.setUsername(username);
            userEntity.setEmail(email);
            userEntity.setImage(imageName);
            userService.save(userEntity);
        }
        userService.downloadImage(urlImageFacebookOrGG,pathImages);

//        HttpSession session = request.getSession();
//        if (session == null){
//            session.setAttribute("User",iUserRepository.findByEmail(email));
//        }
//        request.setAttribute("ImageUser",imageName);
        request.getRequestDispatcher("/index").forward(request,response);
    }
}
