package com.example.demooauth2.security;

import com.example.demooauth2.entity.Provider;
import com.example.demooauth2.security.oauth.CustomOAuth2User;
import com.example.demooauth2.security.oauth.CustomOAuth2UserService;
import com.example.demooauth2.security.oauth.UserDetailServiceImpl;
import com.example.demooauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Set;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    CustomOAuth2UserService oAuth2UserService;

    @Bean
    public UserDetailServiceImpl userDetailService(){
        return new UserDetailServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("pass")
                .defaultSuccessUrl("/user")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                        String clientName = oAuth2User.getClientName();
                        Provider provider = Provider.LOCAL;
                        String picture = "";

                        if (clientName.equals("Facebook")){
                            provider = Provider.FACEBOOK;
                            LinkedHashMap<String, Object> layer1 = oAuth2User.getAttribute("picture");
                            LinkedHashMap<String, Object> layer2 = (LinkedHashMap<String, Object>) layer1.get("data");
                            picture = (String) layer2.get("url");
                        } else if (clientName.equals("Google")){
                            provider = Provider.GOOGLE;
                            picture = oAuth2User.getAttribute("picture");
                        }
                        request.getSession().setAttribute("picture", picture);
                        userService.checkExistUserOauth(oAuth2User.getEmail(), provider);
                        response.sendRedirect("/user");
                    }
                })
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }
}
