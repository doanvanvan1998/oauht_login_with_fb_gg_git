package com.example.demo.sercurity;

import com.example.demo.entity.ProviderEntity;
import com.example.demo.sercurity.auth.Custom0Auth2User;
import com.example.demo.sercurity.auth.CustomOAuth2UserService;
import com.example.demo.sercurity.auth.UserDetailServiceImpl;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.naming.Context;
import javax.persistence.Enumerated;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailServiceImpl();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/login").permitAll()
                        .anyRequest().authenticated()
                        .and()
                            .formLogin()
                            .permitAll()
                            .loginPage("/login").usernameParameter("email")
                            .passwordParameter("pass").defaultSuccessUrl("/list")
                        .and()
                            .oauth2Login().loginPage("/login")
                            .userInfoEndpoint().userService(customOAuth2UserService)
                        .and()
                            .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                        Custom0Auth2User auth2User = null;
                        DefaultOAuth2User oAuth2User = null;
                        String email = "";
                        ProviderEntity provider;
                        try{
                            auth2User = (Custom0Auth2User) authentication.getPrincipal();
                            email = auth2User.getAttribute("email");
                            provider = ProviderEntity.FACEBOOK;
                            String token = auth2User.getToken();
                            HttpSession session = request.getSession();
                            session.setAttribute("token",token);
                            session.setAttribute("id",auth2User.getId());
                        }
                        catch (Exception e){
                            oAuth2User= (DefaultOAuth2User) authentication.getPrincipal();
                            email = oAuth2User.getAttribute("email");
                            provider = ProviderEntity.GOOGLE;
                        }
                        userService.checkexitsUserAuth(email,provider);

                        response.sendRedirect("/list");
                    }
                })
                        .and()
                            .logout().logoutSuccessUrl("/").permitAll()

                        .and()
                            .exceptionHandling().accessDeniedPage("/403");

    }
    @Autowired
    CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    UserService userService;
}
