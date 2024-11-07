package com.jwtauthmanager.jwtauth.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtauthmanager.jwtauth.models.entity.User;
import com.jwtauthmanager.jwtauth.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  UserController(UserService userService){
    this.userService = userService;
  }

  @PostMapping(value = "/data", consumes = "application/json", produces = "application/json")
  public User createUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

  @GetMapping("/hello")
  //@Secured({ "ROLE_VIEWER", "ROLE_EDITOR" })
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
  public String hello() {
    return "hello";
  }

   @GetMapping("/{passwd}")
    public String changePassword(@PathVariable String passwd,Principal connectedUser) {
        System.out.println("Principal Type: " + connectedUser.getName());
        System.out.println(passwd);        
        userService.changePassword(passwd, connectedUser);
        return "Password changed successfully"; // Kullanıcıya geri dönüş
    }



}
