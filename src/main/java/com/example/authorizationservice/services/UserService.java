package com.example.authorizationservice.services;

import com.example.authorizationservice.models.Role;
import com.example.authorizationservice.models.User;
import com.example.authorizationservice.repositories.RoleRepository;
import com.example.authorizationservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String createUser(String username, String password, String email) {
        // Create user
        if (username == null || password == null || email == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(username);
        user.setEmailVerified(true);
        user.setHashedPassword(passwordEncoder.encode(password));
        Role role = roleRepository.findById(2L).get();

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        return "created";
    }
}
