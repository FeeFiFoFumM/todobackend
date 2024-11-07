package com.jwtauthmanager.jwtauth.shell;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.jwtauthmanager.jwtauth.models.userdetail.UserDetailslImpl;
import com.jwtauthmanager.jwtauth.services.UserDetailsServiceImpl;
import com.jwtauthmanager.jwtauth.utils.JwtTokenManager;

@ShellComponent
public class JwtTokenGen {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtTokenManager jwtTokenManager;

    public JwtTokenGen(UserDetailsServiceImpl userDetailsServiceImpl, JwtTokenManager jwtTokenManager) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtTokenManager = jwtTokenManager;
    }
    @ShellMethod("Generate JWT for a user by username")
    public String generateJwt() {
        try {
            UserDetailslImpl userDetails = (UserDetailslImpl) userDetailsServiceImpl.loadUserByUsername("admin");
            String token = jwtTokenManager.generateJwtToken(userDetails);
            return "Generated token: " + token;
        } catch (UsernameNotFoundException e) {
            return "User not found: ";
        }
    }
}

