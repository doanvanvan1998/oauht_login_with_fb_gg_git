package com.tranhoangmanh.oauth2_facebook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String imageUrl;
    private String locale;
    private String lastName;
    private String familyName;
    private boolean emailVerified;
}
