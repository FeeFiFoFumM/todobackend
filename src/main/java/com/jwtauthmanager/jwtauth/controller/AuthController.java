package com.jwtauthmanager.jwtauth.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtauthmanager.jwtauth.models.entity.User;
import com.jwtauthmanager.jwtauth.models.request.LoginRequest;
import com.jwtauthmanager.jwtauth.models.response.LoginResponse;
import com.jwtauthmanager.jwtauth.models.userdetail.UserDetailslImpl;
import com.jwtauthmanager.jwtauth.repository.UserRepository;
import com.jwtauthmanager.jwtauth.services.UserDetailsServiceImpl;
import com.jwtauthmanager.jwtauth.services.ControllerServices.AuthControllerService;
import com.jwtauthmanager.jwtauth.utils.JwtTokenManager;

@RestController
@RequestMapping("/auth")
public class AuthController {

    JwtTokenManager jwtTokenManager;

    UserRepository userRepository;

    UserDetailsServiceImpl userDetailsServiceImpl;
    AuthControllerService authControllerService;

    AuthController(UserRepository userRepository, UserDetailsServiceImpl userDetailsServiceImpl,
            JwtTokenManager jwtTokenManager,
            AuthControllerService authControllerService
            ) {
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtTokenManager = jwtTokenManager;
        this.authControllerService = authControllerService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // LoginController'dan yanıtı al
        ResponseEntity<LoginResponse> response = authControllerService.loginController(request.getUsername(), request.getPassword());
    
        // HTTP başlıklarını oluştur
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = response.getBody().getToken(); // Token'ı yanıt gövdesinden al
        httpHeaders.set("Authorization", "Bearer " + token); // Authorization başlığı ekle
    
        // Yeni ResponseEntity oluştur ve yanıtı döndür
        return new ResponseEntity<>(response.getBody(), httpHeaders, response.getStatusCode());
    }
    

    @PostMapping("/home")
    public ResponseEntity<String> home(@RequestBody String token) {
        // Token'ı burada doğrulayın (JWT veya başka bir yöntemle)
        if (isValidToken(token)) {
            return ResponseEntity.ok("Token geçerli. Hoş geldiniz!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Geçersiz token.");
        }
    }

    private boolean isValidToken(String token) {
        // Token doğrulama işlemi burada yapılmalı
        // Örneğin, JWT token'ı kontrol edebilirsiniz
        return true; // Bu, sadece örnek için. Gerçek doğrulama yapılmalı.
    }

}
