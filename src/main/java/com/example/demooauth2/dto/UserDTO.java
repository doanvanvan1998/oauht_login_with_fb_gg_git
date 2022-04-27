package com.example.demooauth2.dto;

import com.example.demooauth2.entity.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Provider provider;
}
