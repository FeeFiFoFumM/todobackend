package com.jwtauthmanager.jwtauth.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jwtauthmanager.jwtauth.services.UserDetailsServiceImpl;
import com.jwtauthmanager.jwtauth.utils.JwtTokenManager;
import com.jwtauthmanager.jwtauth.models.entity.User;
import com.jwtauthmanager.jwtauth.models.userdetail.UserDetailslImpl;
import com.jwtauthmanager.jwtauth.repository.UserRepository;

@Component
public class StartupCommandLineRunner implements CommandLineRunner {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) {
        JwtTokenGen jwtTokenGen = new JwtTokenGen(userDetailsServiceImpl, jwtTokenManager);
        System.out.println("Uygulama başlatıldı, gerekli işlemler yapılıyor...");
        System.out.println(jwtTokenGen.generateJwt());
        var user = userDetailsServiceImpl.loadUserByUsername("admin");
        System.out.println("Kullanıcı Tipi: " + user.getClass().getName());
        System.out.println("Kullanıcı Bilgileri: " + user);
        // UserDetailslImpl'i User nesnesine çevir
        User baseUser = ((UserDetailslImpl) user).toUser();
        System.out.println("Kullanıcı Tipi: " + baseUser.getClass().getName());
        System.out.println("Kullanıcı Bilgileri: " + baseUser);
        System.out.println("Kullanıcı Bilgileri: " + baseUser.getUsername());
        System.out.println("Kullanıcı Bilgileri: " + baseUser.getPassword());
        System.out.println("Kullanıcı Bilgileri: " + baseUser.getRole());

    }
}
