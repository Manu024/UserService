package com.example.authorizationservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserSignInEmailDto {
    private String from;
    private String to;
    private String message;

    public UserSignInEmailDto(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public UserSignInEmailDto() {
    }
}
