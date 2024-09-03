package com.example.authorizationservice.security.models;

import com.example.authorizationservice.models.Token;
import com.example.authorizationservice.models.User;
import com.example.authorizationservice.repositories.TokenRepository;
import com.example.authorizationservice.repositories.UserRepository;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

@Component
public class CustomJwtEncoder {

    private final JwtEncoder jwtEncoder;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public CustomJwtEncoder(JwtEncoder jwtEncoder, TokenRepository tokenRepository, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public String encodeAndStore(JwtClaimsSet claims) {
        Instant expiryTime = claims.getExpiresAt();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);
        String tokenValue = jwtEncoder.encode(parameters).getTokenValue();

        String userEmail = claims.getSubject();
        // Save the token in the repository
        Token token = new Token();
        token.setValue(tokenValue);
        token.setExpiryAt(Date.from(expiryTime));
        Optional<User> user = userRepository.findByEmail(userEmail);
//        System.out.println("user" + userEmail + " token: " + tokenValue);
        if (user.isPresent()) {
            token.setUser(user.get());
        }
        tokenRepository.save(token);

        return tokenValue;
    }
}
