package com.example.authorizationservice.security.models;

import com.example.authorizationservice.dtos.UserSignInEmailDto;
import com.example.authorizationservice.services.KafkaProducerService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final CustomJwtEncoder customJwtEncoder;
    private KafkaProducerService kafkaProducerService;

    public CustomOAuth2TokenCustomizer(CustomJwtEncoder customJwtEncoder, KafkaProducerService kafkaProducerService) {
        this.customJwtEncoder = customJwtEncoder;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void customize(JwtEncodingContext context) {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            context.getClaims().claims((claims) -> {
                Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                        .stream()
                        .map(c -> c.replaceFirst("^ROLE_", ""))
                        .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                claims.put("roles", roles);
            });
            JwtClaimsSet claims = context.getClaims().build();
            // Encode and store the token using the custom encoder
            String tokenValue = customJwtEncoder.encodeAndStore(claims);
            sendUserSignInEmail("ecommapp@gmail.com", context.getPrincipal().getName(), "You have signed in successfully");
//            System.out.println("jwt token2: " + tokenValue);

            // Optionally, you can update the context with the stored token
            // (not necessary in this case since it would be the same)
        }
    }

    private void sendUserSignInEmail(String from, String to, String message) {
        // Send an email to the user to notify them of the sign in
        UserSignInEmailDto userSignInEmailDto = new UserSignInEmailDto();
        userSignInEmailDto.setFrom(from);
        userSignInEmailDto.setTo(to);
        userSignInEmailDto.setMessage(message);

        kafkaProducerService.sendUserSignInMail(userSignInEmailDto);
    }
}
