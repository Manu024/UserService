package com.example.authorizationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Token extends BaseModel {
    private String value;
    @ManyToOne
    private User user;
    private Date expiryAt;
    private boolean isDeleted;

    public Token(String value, User user, Date expiryAt) {
        this.value = value;
        this.user = user;
        this.expiryAt = expiryAt;
        this.isDeleted = false;
    }

    public Token() {

    }
}
