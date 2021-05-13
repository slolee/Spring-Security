package com.example.security.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.security.exceptions.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtDecoder {
    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);
    private static final String SIGNING_KEY = "jwttest";

    // 유효한 JWT인지 검증하는 부분.
    public AccountContext decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new InvalidJwtException("유효한 토큰이 아닙니다."));

        String username = decodedJWT.getClaim("USERNAME").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();

        return new AccountContext(username, role);
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SIGNING_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();

            // 여기까지오면 이 token이 유효하다고 판단할 수 있다.
            jwt = verifier.verify(token);
        }catch(Exception e) {
            log.error(e.getMessage());
        }
        // 이 문법 기억하자..
        return Optional.ofNullable(jwt);
    }
}
