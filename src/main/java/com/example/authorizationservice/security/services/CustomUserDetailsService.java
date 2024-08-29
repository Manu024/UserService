package com.example.authorizationservice.security.services;

import com.example.authorizationservice.security.models.CustomUserDetails;
import com.example.authorizationservice.models.User;
import com.example.authorizationservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found for email: " + username);
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(optionalUser.get());
        return customUserDetails;
    }
}
