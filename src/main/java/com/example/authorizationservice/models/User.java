package com.example.authorizationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel {
    private String name;
    private String email;
    private String hashedPassword;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean isEmailVerified;

    public User(String name, String email, String hashedPassword, List<Role> roles, boolean isEmailVerified) {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.roles = roles;
        this.isEmailVerified = isEmailVerified;
    }

    public User() {

    }
}
