package com.dockeep.demo.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OAuth2Debugger {
    @Value("${GOOGLE_OAUTH2_CLIENT_ID: ID NOT_FOUND}")
    private String cliendId;
    @Value("${GOOGLE_OAUTH2_CLIENT_SECRET}")//: SECRET NOT_FOUND}")
    private String clientSecret;

    @PostConstruct
    public void printOAuthEnvVariables(){
        log.info("*** OAUTH2 VARIABLES ***");
        log.info("ID: {}", cliendId);
        log.info("SECRET: {}", clientSecret);
    }

//    @PostConstruct
//    public void printOAuthEnvVariables(){
//        log.info("*** OAUTH2 VARIABLES ***");
//        log.info("ID: {}", cliendId);
//        log.info("SECRET: {}", clientSecret);
//
//        // Check if system properties are being set at all
//        log.info("System property google.oauth2.client.id: {}", System.getProperty("google.oauth2.client.id"));
//        log.info("System property google.oauth2.client.secret: {}", System.getProperty("google.oauth2.client.secret"));
//
//        // List ALL system properties to see what's actually there
//        log.info("All system properties containing 'google':");
//        System.getProperties().stringPropertyNames().stream()
//                .filter(name -> name.toLowerCase().contains("google"))
//                .forEach(name -> log.info("  {} = {}", name, System.getProperty(name)));
//    }

}
