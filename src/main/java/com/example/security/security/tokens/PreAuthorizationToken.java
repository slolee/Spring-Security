package com.example.security.security.tokens;

import com.example.security.dtos.FormLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public PreAuthorizationToken(FormLoginDto formLoginDto) {
        super(formLoginDto.getId(), formLoginDto.getPassword());
    }
    public PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    public String getUsername() {
        return (String)super.getPrincipal();
    }

    public String getPassword() {
        return (String)super.getCredentials();
    }
}
