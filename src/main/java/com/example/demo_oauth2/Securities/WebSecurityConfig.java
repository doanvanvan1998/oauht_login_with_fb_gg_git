package com.example.demo_oauth2.Securities;


import com.example.demo_oauth2.Entities.Provider;
import com.example.demo_oauth2.Securities.Oauth.CustomOAuth2User;
import com.example.demo_oauth2.Securities.Oauth.CustomOAuth2UserService;
import com.example.demo_oauth2.Services.UserDetailsServiceImp;
import com.example.demo_oauth2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImp();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Autowired
    UserService userService;
    @Autowired
    CustomOAuth2UserService oAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/list")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
//                .userService(oAuth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        try{
                            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                            if (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().toLowerCase().equals("facebook")){
                                userService.checkexistUser(oauthUser.getEmail(), Provider.FACEBOOK);
                            }else if(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().toLowerCase().equals("github")){
                                userService.checkexistUser(oauthUser.getName(), Provider.GITHUB);
                            }
                            request.getSession().setAttribute("username", oauthUser.getName());
                            request.getSession().setAttribute("userimage", oauthUser.getPicture());
                        }catch(Exception e){
                            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                            userService.checkexistUser(oauthUser.getEmail(), Provider.GOOGLE);
                            request.getSession().setAttribute("username", oauthUser.getName());
                            request.getSession().setAttribute("userimage", oauthUser.getPicture());
                        }
                        response.sendRedirect("/list");
                    }
                })
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }
}
