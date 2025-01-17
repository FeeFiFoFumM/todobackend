package com.jwtauthmanager.jwtauth.models.response;
import com.jwtauthmanager.jwtauth.models.role.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    
    private String username;
    private String token;
    private Role Role;
    private String refToken;
}
