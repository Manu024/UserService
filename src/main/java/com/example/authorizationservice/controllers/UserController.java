package com.example.authorizationservice.controllers;

import com.example.authorizationservice.dtos.SignupReqDto;
import com.example.authorizationservice.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String createUser(@RequestBody SignupReqDto signupReqDto) {
        return userService.createUser(signupReqDto.getUsername(), signupReqDto.getPassword(), signupReqDto.getEmail());
    }
}
