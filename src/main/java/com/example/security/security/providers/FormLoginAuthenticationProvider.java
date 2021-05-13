package com.example.security.security.providers;

import com.example.security.domain.Account;
import com.example.security.domain.AccountRepository;
import com.example.security.security.AccountContext;
import com.example.security.security.AccountContextService;
import com.example.security.security.tokens.PostAuthorizationToken;
import com.example.security.security.tokens.PreAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 여기들어온 Authentication 객체는 무조건 PreAuthorizationToken 이라는 확신을 가져도 된다!
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;
        String username = token.getUsername();
        String password = token.getPassword();

        Account account = accountRepository.findByUserId(username).orElseThrow(() -> new NoSuchElementException("정보에 맞는 계정이 없습니다."));

        if (checkPassword(password, account)) {
            return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(account));
        }
        // Exception Handler 다 구현해야한다!
        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreAuthorizationToken.class.isAssignableFrom(aClass);
    }

    private boolean checkPassword(String password, Account account) {
        // 원본이 앞에와야함!! 주의주의!!
        return passwordEncoder.matches(password, account.getPassword());
    }
}
