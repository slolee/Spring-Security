package com.example.security.security.filters;

import com.example.security.dtos.FormLoginDto;
import com.example.security.security.handlers.FormLoginAuthenticationSuccessHandler;
import com.example.security.security.tokens.PreAuthorizationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public FormLoginFilter(String defaultUrl, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(defaultUrl);
        this.authenticationSuccessHandler = successHandler;
        this.authenticationFailureHandler = failureHandler;
    }

    protected FormLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    // 처음에 인증 시도 (토큰을 만들어서 Authentication Provider 로 보내서 인증을 시도) - 그 결과가 아래 두 개의 오버라이딩 메서드로 오는듯.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        FormLoginDto dto = new ObjectMapper().readValue(req.getReader(), FormLoginDto.class);
        PreAuthorizationToken token = new PreAuthorizationToken(dto);
        return super.getAuthenticationManager().authenticate(token);
    }

    // 인증 성공했으면 JWT 토큰 생성. Http Response 로 JWT 토큰을 내려주는 역할까지.
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(req, res, authResult);
    }

    // 인증 실패했으면 유저에게 어떠한 정보를 알려줄지를 여기서 구현.
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException {
        this.authenticationFailureHandler.onAuthenticationFailure(req, res, failed);
    }
}
