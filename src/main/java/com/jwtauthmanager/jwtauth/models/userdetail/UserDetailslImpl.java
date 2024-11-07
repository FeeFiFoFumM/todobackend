package com.jwtauthmanager.jwtauth.models.userdetail;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwtauthmanager.jwtauth.models.entity.User;

public class UserDetailslImpl extends User implements UserDetails {

    public UserDetailslImpl(User user){
        super(user.getId(),user.getUsername(),user.getEmail(),user.getPassword(),user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getAuthorities();
      }


    public User toUser() {
        return new User(getId(), getUsername(), getEmail(), getPassword(), getRole());
    }


}
