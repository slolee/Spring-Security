package com.example.security.security.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        // TODO 사용자에게 적절한 메시지를 보여줘야 한다.
        // TODO Front-End에 넘어갈 적절한 Dto를 만들어서 보내주면 된다. 파라미터로 받는 Exception을 적절히 이용하면 된다.

        Logger log = LoggerFactory.getLogger("Authentication_Failure");
        log.error(e.getMessage());
    }
}
