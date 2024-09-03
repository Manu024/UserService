package com.example.authorizationservice.security.models;

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

    public CustomOAuth2TokenCustomizer(CustomJwtEncoder customJwtEncoder) {
        this.customJwtEncoder = customJwtEncoder;
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
//
//            System.out.println("jwt token2: " + tokenValue);

            // Optionally, you can update the context with the stored token
            // (not necessary in this case since it would be the same)
        }
    }
}
