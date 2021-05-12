package com.example.security.security.tokens;

import com.example.security.security.AccountContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {
    private PostAuthorizationToken(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public static PostAuthorizationToken getTokenFromAccountContext(AccountContext context) {
        return new PostAuthorizationToken(context.getUsername(), context.getPassword(), context.getAuthorities());
    }

    public String getUsername() {
        return (String)super.getPrincipal();
    }

    public String getPassword() {
        return (String) super.getCredentials();
    }
}
