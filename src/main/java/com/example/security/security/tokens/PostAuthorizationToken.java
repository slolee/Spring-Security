package com.example.security.security.tokens;

import com.example.security.security.AccountContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {
    private PostAuthorizationToken(Object principal, Object password, Collection<? extends GrantedAuthority> authorities) {
        super(principal, password, authorities);
    }

    public static PostAuthorizationToken getTokenFromAccountContext(AccountContext context) {
        return new PostAuthorizationToken(context, context.getPassword(), context.getAuthorities());
    }

    public String getPassword() {
        return (String) super.getCredentials();
    }
}
