package com.example.authorizationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupReqDto{
    private String username;
    private String password;
    private String email;

    public SignupReqDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public SignupReqDto() {

    }
}
