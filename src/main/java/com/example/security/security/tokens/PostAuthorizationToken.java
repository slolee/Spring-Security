package com.example.security.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public PostAuthorizationToken(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getUsername() {
        return (String)super.getPrincipal();
    }

    public String getPassword() {
        return (String) super.getCredentials();
    }
}
