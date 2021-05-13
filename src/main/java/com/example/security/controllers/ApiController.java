package com.example.security.controllers;

<<<<<<< HEAD
=======
import com.example.security.security.tokens.PostAuthorizationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
>>>>>>> step3-jwt-request
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
<<<<<<< HEAD
    @GetMapping
    public String hello() {
        return "hello";
=======
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUsername(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        return token.getAccountContext().getUsername();
>>>>>>> step3-jwt-request
    }
}
