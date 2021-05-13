package com.example.security.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.security.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class JwtFactory {
    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);
    private static final String SIGNING_KEY = "jwttest";

    public String generateToken(AccountContext context) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("ch4njun")
                    .withClaim("USER_ROLE", context.getAccount().getUserRole().getRoleName())
                    .sign(generateAlgorithm());
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return token;
    }

    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        return Algorithm.HMAC256(SIGNING_KEY);
    }
}
