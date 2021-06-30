package com.presence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WebApplication {
    //clasa principala unde pornim aplicatia
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(WebApplication.class, args);
    }
}