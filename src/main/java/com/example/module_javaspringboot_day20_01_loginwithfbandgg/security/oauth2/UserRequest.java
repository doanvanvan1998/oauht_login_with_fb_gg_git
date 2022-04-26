package com.example.module_javaspringboot_day20_01_loginwithfbandgg.security.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String clientName;
    private String accessToken;
    private String tokenType;
}
