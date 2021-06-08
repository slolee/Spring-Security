package com.example.security;

import com.example.security.domain.Account;
import com.example.security.domain.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner bootstrapTestAccount(AccountRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            Account account = new Account();
            account.setUserId("slolee@naver.com");
            account.setUsername("ch4njun");
            account.setPassword(passwordEncoder.encode("cks14579"));
            repository.save(account);
        };
    }
}
