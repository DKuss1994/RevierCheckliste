package com.example.securitydispatch;


import com.example.securitydispatch.infrastructure.persistence.ShiftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecuritydispatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuritydispatchApplication.class, args);
    }

}

