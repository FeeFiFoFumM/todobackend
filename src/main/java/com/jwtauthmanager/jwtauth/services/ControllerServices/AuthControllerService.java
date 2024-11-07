package com.jwtauthmanager.jwtauth.services.ControllerServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwtauthmanager.jwtauth.models.response.LoginResponse;
import com.jwtauthmanager.jwtauth.models.userdetail.UserDetailslImpl;
import com.jwtauthmanager.jwtauth.repository.UserRepository;
import com.jwtauthmanager.jwtauth.services.UserDetailsServiceImpl;
import com.jwtauthmanager.jwtauth.utils.JwtTokenManager;

@Service
public class AuthControllerService {
    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserRepository userRepository;

    AuthControllerService(JwtTokenManager jwtTokenManager, UserRepository userRepository,
            UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtTokenManager = jwtTokenManager;
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;

    }

    public ResponseEntity<LoginResponse> loginController(String username, String password) {
        LoginResponse response = new LoginResponse();
    
        // Kullanıcı adı veya parola boş ise BAD_REQUEST döndür
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    
        // Kullanıcıyı yükle
        UserDetailslImpl userDetails;
        try {
            userDetails = (UserDetailslImpl) userDetailsServiceImpl.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            // Kullanıcı bulunamadığında 404 NOT_FOUND döndür
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
        // Şifre doğrulama
        if (password.equals(userDetails.getPassword())) {
            String token = jwtTokenManager.generateJwtToken(userDetails);
            String refToken = jwtTokenManager.generateJwtToken(userDetails);
            response.setUsername(username);
            response.setToken(token);
            response.setRole(userDetails.getRole());
            response.setRefToken(refToken);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    
        // Şifre geçersiz ise 401 UNAUTHORIZED döndür
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
