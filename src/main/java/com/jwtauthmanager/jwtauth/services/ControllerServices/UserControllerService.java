package com.jwtauthmanager.jwtauth.services.ControllerServices;

import org.springframework.stereotype.Service;
import org.stringtemplate.v4.compiler.CodeGenerator.primary_return;

import com.jwtauthmanager.jwtauth.models.response.LoginResponse;
import com.jwtauthmanager.jwtauth.models.userdetail.UserDetailslImpl;
import com.jwtauthmanager.jwtauth.repository.UserRepository;
import com.jwtauthmanager.jwtauth.services.UserDetailsServiceImpl;
import com.jwtauthmanager.jwtauth.utils.JwtTokenManager;


@Service
public class UserControllerService {
    
    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserRepository userRepository;

    UserControllerService(JwtTokenManager jwtTokenManager, UserRepository userRepository,UserDetailsServiceImpl userDetailsServiceImpl){
        this.jwtTokenManager = jwtTokenManager;
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public LoginResponse loginController(String username,String password){
        UserDetailslImpl userDetails = (UserDetailslImpl) userDetailsServiceImpl.loadUserByUsername(username);
        String token = jwtTokenManager.generateJwtToken(userDetails);
        LoginResponse response = new LoginResponse();
        response.setUsername(username);
        response.setToken(token);
        return response;
    }
}
