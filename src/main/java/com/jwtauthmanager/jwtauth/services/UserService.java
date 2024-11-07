package com.jwtauthmanager.jwtauth.services;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.jwtauthmanager.jwtauth.models.entity.User;
import com.jwtauthmanager.jwtauth.models.userdetail.UserDetailslImpl;
import com.jwtauthmanager.jwtauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserRepository userRepository;
      public void changePassword(String newPassword, Principal connectedUser) {
        //Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getDetails();
        // check if the current password is correct
        // update the password
        user.setPassword(newPassword);

        User baseUser = ((UserDetailslImpl) user).toUser();
        // save the new password
        userRepository.save(baseUser);
    }
    public User save(User user) {
        return userRepository.save(user);
    }
}