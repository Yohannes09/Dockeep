package com.dockeep.config;


import com.authmat.token.filter.SimpleJwtAuthenticationFilter;
import com.authmat.token.validation.contracts.PublicKeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;
import java.util.Set;

@Configuration
public class JwtFilterConfig {
    @Bean
    public OncePerRequestFilter jwtAuthenticationFilter(PublicKeyResolver publicKeyResolver){
        return new SimpleJwtAuthenticationFilter(publicKeyResolver, Set.of(""));
    }

    @Bean
    public PublicKeyResolver publicKeyResolver(){
        return () -> Optional.of("");
    }

}
