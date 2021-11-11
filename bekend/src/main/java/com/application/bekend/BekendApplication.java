package com.application.bekend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BekendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BekendApplication.class, args);
    }

}
