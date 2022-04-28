package com.example.quanlysanpham.Configuration.Security;

import com.example.quanlysanpham.Configuration.Oauth2.CustomOauth2User;
import com.example.quanlysanpham.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
//    @Autowired
//    CustomOauth2UserService customOauth2UserService;

    @Autowired
    UserService userService;

    @Bean
    public UserDetailsServiceCustom userDetailsServiceImpl(){
        return new UserDetailsServiceCustom() ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/login", "/add").permitAll()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    UserDetailsCustom ud =(UserDetailsCustom) authentication.getPrincipal();
                    ud.getAuthorities().forEach(x->{
                        try{
                            if(x.getAuthority().equals("ROLE_ADMIN")){
                                response.sendRedirect("/admin/index");
                            }
                            else if(x.getAuthority().equals("ROLE_USER")){
                                response.sendRedirect("/listCart");
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                })
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        CustomOauth2User oAuth2User = (CustomOauth2User) authentication.getPrincipal();
                        String clientName = oAuth2User.getClientName();
                        String email = oAuth2User.getAttribute("email");

                        userService.checkExistUserOauth(email, clientName);
                        response.sendRedirect("/listCart");
                    }
                })
                .and()
                .logout()
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }
}
