package com.tranhoangmanh.oauth2_facebook.config;

import com.tranhoangmanh.oauth2_facebook.config.oauth2.CustomOAuth2User;
import com.tranhoangmanh.oauth2_facebook.config.oauth2.CustomOAuth2UserGoogle;
import com.tranhoangmanh.oauth2_facebook.config.oauth2.UserDetailsServiceImpl;
import com.tranhoangmanh.oauth2_facebook.model.UserResponse;
import com.tranhoangmanh.oauth2_facebook.service.CustomOAuth2UserService;
import com.tranhoangmanh.oauth2_facebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomOAuth2UserService oAuth2UserService;

    @Autowired
    UserService userService;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/login")
                .permitAll()
                .anyRequest().authenticated().and()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("pass")
                .defaultSuccessUrl("/list")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        HttpSession session = request.getSession();
                        UserResponse userResponse = new UserResponse();
                        try{
                            CustomOAuth2User oAuth2UserService = (CustomOAuth2User) authentication.getPrincipal();
                            userService.checkExistUserOAuth(oAuth2UserService.getEmail(), "Facebook");
                            userResponse.setId(oAuth2UserService.getId() + "");
                            userResponse.setName(oAuth2UserService.getName());
                            userResponse.setEmail(oAuth2UserService.getEmail());
                            String imageUrl = "https://graph.facebook.com/" + oAuth2UserService.getId() + "/picture?type=large&access_token=" + oAuth2UserService.getAccessToken();
                            userResponse.setImageUrl(imageUrl);
                            session.setAttribute("clientName", "Facebook");
                        }catch (Exception e) {
                            CustomOAuth2UserGoogle customOAuth2UserGoogle = (CustomOAuth2UserGoogle) authentication.getPrincipal();
                            userService.checkExistUserOAuth(customOAuth2UserGoogle.getEmail(), "Google");
                            userResponse.setId(customOAuth2UserGoogle.getId());
                            userResponse.setName(customOAuth2UserGoogle.getName());
                            userResponse.setEmail(customOAuth2UserGoogle.getEmail());
                            userResponse.setImageUrl(customOAuth2UserGoogle.getUrlAvatarURL());
                            userResponse.setLocale(customOAuth2UserGoogle.getLocale());
                            userResponse.setFamilyName(customOAuth2UserGoogle.getFamilyName());
                            userResponse.setLastName(customOAuth2UserGoogle.getLastName());
                            userResponse.setEmailVerified(customOAuth2UserGoogle.emailVerified());
                            session.setAttribute("clientName", "Google");
                        }
                        session.setAttribute("userResponse", userResponse);
                        response.sendRedirect("/list");
                    }
                })
                .and()
                .logout().logoutSuccessUrl("/login").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }
}
