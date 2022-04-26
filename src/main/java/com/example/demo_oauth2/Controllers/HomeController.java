package com.example.demo_oauth2.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/list")
    public String homePage(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = this.authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        OAuth2AccessToken token = authorizedClient.getAccessToken();

        return "index";
    }

    @GetMapping("/login/oauth2/code/google")
            public String redrgoogle()
    {
        return "/list";
    }
}
