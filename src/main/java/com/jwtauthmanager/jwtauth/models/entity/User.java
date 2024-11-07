package com.jwtauthmanager.jwtauth.models.entity;



import com.jwtauthmanager.jwtauth.models.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Table(name = "users")
@Entity
@Getter
@Setter
public class User {
    
    public User(Long id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(name = "username" , unique = true,nullable = false)
    private String username;

    @Column(name = "password" , unique = false ,nullable = false)
    private String password;

    @Column(name = "email" , unique = true,nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

}
